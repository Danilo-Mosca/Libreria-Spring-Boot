package com.moscadanilo.libreria.exceptions;

//Eccenzione non controllata
public class BookNumberOfCopiesNotFoundException extends RuntimeException {
    public BookNumberOfCopiesNotFoundException(Integer numberOfCopies){
        super("Numero di copie " + numberOfCopies + " non inserito correttamente");
    }
}
