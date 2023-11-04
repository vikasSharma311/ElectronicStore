package com.lcwd.electronic.store.dtos;

import com.lcwd.electronic.store.entities.OrderItem;
import com.lcwd.electronic.store.entities.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateOrderRequest {
    @NotBlank(message = "cart Id is required !!")
    private String CartId;
    @NotBlank(message = "user Id is required !!")
    private String userId;
    private String orderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    @NotBlank(message = "Address is required !!")
    private String billingAddress;
    @NotBlank(message = "Phone number is required !!")
    private String billingPhone;
    @NotBlank(message = "Billing name  is required !!")
    private String billingName;
}
