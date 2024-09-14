package com.example.order_service.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class OrderProcessEvent {

    private String status;

    private Instant date;
}
