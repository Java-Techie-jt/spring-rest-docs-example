package com.javatechie.docs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
@RestController
public class SpringDocsApplication {
    @Autowired
    private OrderRepository repository;

    @PostMapping("/PlaceOrder")
    public List<Order> saveOrder(@RequestBody List<Order> orders) {
        repository.saveAll(orders);
        return orders;
    }

    @GetMapping("/getOrders")
    public List<Order> getOrders() {
        return repository.findAll();
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringDocsApplication.class, args);
    }

}
