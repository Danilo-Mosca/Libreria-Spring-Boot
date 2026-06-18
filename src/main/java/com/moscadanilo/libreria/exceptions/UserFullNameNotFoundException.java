package com.moscadanilo.libreria.exceptions;

public class UserFullNameNotFoundException extends RuntimeException {

    public UserFullNameNotFoundException(String name, String lastName) {

        super("Nessun utente trovato con nome '" +
                name +
                "' e cognome '" +
                lastName +
                "'");
    }
}