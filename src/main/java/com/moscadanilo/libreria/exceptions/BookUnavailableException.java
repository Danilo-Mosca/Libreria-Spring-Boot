package com.moscadanilo.libreria.exceptions;

//Eccezioni business logic
public class BookUnavailableException extends RuntimeException {

    public BookUnavailableException(String title) {
        super("Il libro '" + title + "' non è disponibile");
    }
}