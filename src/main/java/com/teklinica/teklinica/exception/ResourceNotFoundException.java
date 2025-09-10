package com.teklinica.teklinica.exception;

public class ResourceNotFoundException extends RuntimeException {

    // Constructor vacío opcional
    public ResourceNotFoundException() {
        super("Recurso no encontrado");
    }

    // Constructor que recibe un mensaje
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
