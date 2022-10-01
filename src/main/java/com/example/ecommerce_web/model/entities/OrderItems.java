package com.example.ecommerce_web.model.entities;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "order_item")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private int orderItemsID ;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    @NotEmpty(message = "orders is required")
    @NotNull(message = "orders must not be null")
    private Orders orders;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id")
    @NotEmpty(message = "book is required")
    @NotNull(message = "book must not be null")
    private Books books;

    @Column(name = "previous_price")
    @NotEmpty(message = "previous price is required")
    @NotNull(message = "previous price must not be null")
    private float price;

    @Column(name = "quantity")
    @NotEmpty(message = "quantity is required")
    @NotNull(message = "quantity must not be null")
    private int quantity;

}
