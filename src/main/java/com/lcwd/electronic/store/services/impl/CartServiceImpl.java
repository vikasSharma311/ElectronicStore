package com.lcwd.electronic.store.services.impl;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;
import com.lcwd.electronic.store.entities.Cart;
import com.lcwd.electronic.store.entities.CartItem;
import com.lcwd.electronic.store.entities.Product;
import com.lcwd.electronic.store.entities.User;
import com.lcwd.electronic.store.exceptions.BadApiRequestException;
import com.lcwd.electronic.store.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.repositories.CartItemRepository;
import com.lcwd.electronic.store.repositories.CartRepository;
import com.lcwd.electronic.store.repositories.ProductRepository;
import com.lcwd.electronic.store.repositories.UserRepository;
import com.lcwd.electronic.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
@Service
public class CartServiceImpl implements CartService {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;
    @Autowired
    private ModelMapper mapper;
    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        String productId = request.getProductId();
        int quantity = request.getQuantity();
        if (quantity <= 0) {
            throw new BadApiRequestException("Requested quantity is not valid !!");
        }
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with given id not found !!"));
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("Product with given id not found !!"));
        Cart cart=null;
        try {
             cart = cartRepository.findByUser(user).get();
        }catch (NoSuchElementException e){
            cart=new Cart();
            cart.setCreatedAt(new Date());
            cart.setCartId(UUID.randomUUID().toString());
        }
        //perform cart operations
        //if cart item already present
        AtomicReference<Boolean>update=new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        items = items.stream().map(item -> {
            if (item.getProduct().getProductId().equals(productId)) {
                //item is already present in cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity * product.getDiscountedPrice());
                update.set(true);
            }
                    return item;
        }
        ).collect(Collectors.toList());
        if (update.get()==false){
            CartItem cartItem = CartItem.builder()
                    .quantity(quantity)
                    .totalPrice(quantity * product.getDiscountedPrice())
                    .product(product)
                    .cart(cart)
                    .build();
            cart.getItems().add(cartItem);
        }
        cart.setUser(user);
        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart, CartDto.class);
    }
    @Override
    public void removeItemFromCart(String userId, int cartItemId) {
        CartItem cartItem = cartItemRepository.findById(cartItemId).orElseThrow(() -> new ResourceNotFoundException("CartItem with given id not found !!"));
        cartItemRepository.delete(cartItem);
    }
    @Override
    public void clearCart(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with given id not found !!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user Not found !!"));
        cart.getItems().clear();
        cartRepository.save(cart);
    }
    @Override
    public CartDto getCartByUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user with given id not found !!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("Cart of given user Not found !!"));

        return mapper.map(cart, CartDto.class);
    }
}
