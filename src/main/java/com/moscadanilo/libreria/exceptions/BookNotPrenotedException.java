package com.moscadanilo.libreria.exceptions;

//Eccezioni business logic
public class BookNotPrenotedException extends RuntimeException {

    public BookNotPrenotedException(String title) {
        super("Il libro '" + title + "' non risulta prenotato");
    }
}