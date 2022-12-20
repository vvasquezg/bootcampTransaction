package com.bootcamp.java.transaction.service.exception;

public class InvalidProductException extends Exception {
    private static final long serialVersionUID = 1L;
    public InvalidProductException() {
        super("Product not configured for client type");
    }
}
