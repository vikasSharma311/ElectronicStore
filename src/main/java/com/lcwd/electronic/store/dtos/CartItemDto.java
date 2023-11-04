package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.Cart;
import com.lcwd.electronic.store.entities.Product;
import lombok.*;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {

    private int cartItemId;
    private ProductDto product;
    private int quantity;
    private int totalPrice;

}
