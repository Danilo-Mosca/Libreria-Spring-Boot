package com.moscadanilo.libreria.exceptions;

import com.moscadanilo.libreria.model.Genre;

//Eccezione non controllata
public class BookGenreNotFoundException extends RuntimeException {
    public BookGenreNotFoundException(Genre genre) {

        super("Nessun libro trovato per il genere: " + genre);
    }
}
