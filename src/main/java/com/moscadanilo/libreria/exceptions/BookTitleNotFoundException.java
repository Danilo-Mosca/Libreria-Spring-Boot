package com.moscadanilo.libreria.exceptions;

// Estende RuntimeException: eccezione non verificata (unchecked)
public class BookTitleNotFoundException extends RuntimeException {
    
    public BookTitleNotFoundException(String title) {
        super("Libro con titolo " + title + " non trovato");
    }
}
