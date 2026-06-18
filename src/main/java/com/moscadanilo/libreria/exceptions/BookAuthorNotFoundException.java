package com.example.libreria.exceptions;

//Eccenzione non controllata
public class BookAuthorNotFoundException extends RuntimeException {
    public BookAuthorNotFoundException(String author) {
        super("Il libro con il seguente Autore "+ author +" non esiste");
    }
}
