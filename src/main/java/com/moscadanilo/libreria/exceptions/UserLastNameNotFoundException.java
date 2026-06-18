package com.example.libreria.exceptions;

public class UserLastNameNotFoundException extends RuntimeException {

    public UserLastNameNotFoundException(String lastName) {
        super("Nessun utente trovato con cognome: " + lastName);
    }
}