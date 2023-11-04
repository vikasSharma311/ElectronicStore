package com.lcwd.electronic.store.services;

import com.lcwd.electronic.store.dtos.AddItemToCartRequest;
import com.lcwd.electronic.store.dtos.CartDto;

public interface CartService {
    //add item to cart
    //case 1:cart for user is not available:we will create the cart and then add items
    //case 2:cart is available :we will add  items to the cart\
    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //remove item from cart
    void removeItemFromCart(String userId,int cartItemId);

    //remove all items from cart
    void clearCart(String userId);

    //to get cart of a user
    CartDto getCartByUser(String userId);
}
