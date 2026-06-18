package com.moscadanilo.libreria.record;

public record BookResponse(
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