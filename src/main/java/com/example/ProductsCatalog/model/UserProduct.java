package com.example.ProductsCatalog.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="user_product")
public class UserProduct {

    @Id
    @GeneratedValue
    private int userId;

    private String username;
    private String password;
    @Column(unique = true,nullable = false)
    private String email;
}
