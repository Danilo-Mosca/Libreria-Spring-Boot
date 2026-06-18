package com.example.libreria.exceptions;

// Estende RuntimeException: eccezione non verificata (unchecked)
public class BookNotFoundException extends RuntimeException{
    
    public BookNotFoundException(Integer id) {
        super("Libro con id " + id + " non trovato");
    }
}
