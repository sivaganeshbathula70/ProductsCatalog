package com.example.ProductsCatalog.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Entity
@SequenceGenerator(
        name = "booking_id_sequence",
        sequenceName = "booking_id_sequence",
        initialValue = 32453,  // Set the initial value
        allocationSize = 1
)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name="bookingInfo")
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "booking_id_sequence")
    private int bookingId;
    private String productName;
    private LocalDate bookingdate;
    private double totalCost;
    private Long contactNumber;
    private String bookingStatus;
    private int orderId;
    private String orderDescription;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "productId")
    private Products products;
}
