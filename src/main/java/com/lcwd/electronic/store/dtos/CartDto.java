package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.CartItem;
import com.lcwd.electronic.store.entities.User;
import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartDto {

    private String cartId;
    private Date createdAt;
    private UserDto user;
    private List<CartItemDto> items = new ArrayList<>();
}
