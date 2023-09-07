package com.example.ProductsCatalog.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@SequenceGenerator(
        name="order_id_sequence",
        sequenceName = "order_id_sequence",
        initialValue = 23453,
        allocationSize = 1
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "order_id_sequence")
    private int orderId;
    private String orderDescription;
    private String orderType;
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;


}
