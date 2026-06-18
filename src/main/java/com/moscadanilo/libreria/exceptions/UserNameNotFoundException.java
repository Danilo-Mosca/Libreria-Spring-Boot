package com.example.libreria.exceptions;

public class UserNameNotFoundException extends RuntimeException {

    public UserNameNotFoundException(String name) {
        super("Nessun utente trovato con nome: " + name);
    }
}