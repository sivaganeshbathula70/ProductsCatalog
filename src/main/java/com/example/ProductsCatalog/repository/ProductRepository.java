package com.example.ProductsCatalog.repository;
import com.example.ProductsCatalog.model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ProductRepository extends JpaRepository<Products,Integer> {
    public Products findByproductName(String field);
}
