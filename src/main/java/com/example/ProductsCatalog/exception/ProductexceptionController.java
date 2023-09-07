package com.example.ProductsCatalog.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ProductexceptionController {
    @ExceptionHandler(value=ProductException.class)
    public ResponseEntity<Object> exception(ProductException productException)
    {
        return new ResponseEntity<>(productException.getMessage(), HttpStatus.NOT_FOUND);
    }
}
