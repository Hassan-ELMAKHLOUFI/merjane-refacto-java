package com.nimbleways.springboilerplate.contollers;

import com.nimbleways.springboilerplate.dto.product.ProcessOrderResponse;
import com.nimbleways.springboilerplate.exceptions.OrderNotFoundException;
import com.nimbleways.springboilerplate.services.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class MyController {

    private final OrderService orderService;

    @PostMapping("{orderId}/processOrder")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<ProcessOrderResponse> processOrder(@PathVariable Long orderId) {
        try {
            ProcessOrderResponse response = orderService.processOrder(orderId);
            return ResponseEntity.ok(response);
        } catch (OrderNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
