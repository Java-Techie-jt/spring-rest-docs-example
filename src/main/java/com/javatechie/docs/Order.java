package com.javatechie.docs;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@AllArgsConstructor
@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "ORDER_TB")
public class Order {
    @Id
    private int orderId;
    private String name;
    private int quantity;
    private double price;
}
