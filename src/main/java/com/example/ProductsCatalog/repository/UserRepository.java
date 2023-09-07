package com.example.ProductsCatalog.repository;


import com.example.ProductsCatalog.model.UserProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserProduct,Integer> {
    public UserProduct findByemail(String Email);
}
