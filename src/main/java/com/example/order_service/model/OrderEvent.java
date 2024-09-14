package com.example.order_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OrderEvent {

    private String product;

    private Integer quantity;
}
