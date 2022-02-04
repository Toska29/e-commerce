package com.phoenix.web.exceptions;

public class ProductDoesNotExistException extends Throwable {
    public ProductDoesNotExistException(String message) {
        super(message);
    }
}
