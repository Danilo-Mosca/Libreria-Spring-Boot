package com.example.libreria.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Integer id) {
        super("Utente con id " + id + " non trovato");
    }
}