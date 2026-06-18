package com.moscadanilo.libreria.exceptions;

public class UserNameNotFoundException extends RuntimeException {

    public UserNameNotFoundException(String name) {
        super("Nessun utente trovato con nome: " + name);
    }
}