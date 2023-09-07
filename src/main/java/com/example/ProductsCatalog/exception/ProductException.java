package com.example.ProductsCatalog.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


public class ProductException extends RuntimeException{
    private static final long serialVersionUID = 1L;

    public ProductException(String s) {
        super(s);
    }
}
