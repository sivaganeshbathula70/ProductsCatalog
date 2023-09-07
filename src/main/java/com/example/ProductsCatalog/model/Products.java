package com.example.ProductsCatalog.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;
@Entity
@SequenceGenerator(
        name = "product_id_sequence",
        sequenceName = "product_id_sequence",
        initialValue = 13232,
        allocationSize = 1
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "products_data")
public class Products {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "product_id_sequence")
    private int productId;
    private String productName;
    private String productDescription;
    private String category;
    private int sku;
    private int price;
    private int quantity;
    private Long contactNumber;
   //private int bookingId;
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate localDate;
    private int rating;
    private int Discount;
    @ManyToOne(cascade=CascadeType.PERSIST)
    @JoinColumn(name = "order_id")
    private Order order;
}
