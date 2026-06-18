package com.moscadanilo.libreria.record;

//DTO della response (risposta da inviare all'utente) per le entità User e Book
// questo servirà a mostrare i libri prenotati dall'utente
public record UserBookResponse(
        Integer id,
        String title,
        String author,
        Integer yearOfPublication,
        Integer pages,
        String genre,
        boolean available,
        String description
) {
}
