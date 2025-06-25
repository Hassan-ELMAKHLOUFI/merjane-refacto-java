package com.nimbleways.springboilerplate.services.order;

import com.nimbleways.springboilerplate.dto.product.ProcessOrderResponse;
import com.nimbleways.springboilerplate.entities.Order;
import com.nimbleways.springboilerplate.entities.Product;
import com.nimbleways.springboilerplate.exceptions.OrderNotFoundException;
import com.nimbleways.springboilerplate.repositories.OrderRepository;
import com.nimbleways.springboilerplate.services.product.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;


@RequiredArgsConstructor
@Service
public class OrderService {

    private final ProductService productService;
    private final OrderRepository orderRepository;

    public ProcessOrderResponse processOrder(Long orderId) throws OrderNotFoundException {
        Optional<Order> optionalOrder = orderRepository.findById(orderId);

        if (optionalOrder.isEmpty()) {
            throw new OrderNotFoundException("Ordr not found ");
        }

        Order order = optionalOrder.get();
        Set<Product> products = order.getItems();

        if (products == null || products.isEmpty()) {
            return new ProcessOrderResponse(order.getId());
        }

        for (Product product : products) {
            try {
                productService.processProduct(product);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return new ProcessOrderResponse(order.getId());
    }

}
