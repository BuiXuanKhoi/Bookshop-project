package com.example.ecommerce_web.model.entities;

import lombok.Data;
import java.util.List;
import javax.persistence.*;

@Data
@Entity
@Table(name = "Order_item")
public class OrderItems {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private int orderItemsID ;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_id")
    private Order order;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "book_id")
    private List<Books> books;

    @Column(name = "previous_price")
    private float previousPrice;

    @Column(name = "quantity")
    private int quantity;

}
