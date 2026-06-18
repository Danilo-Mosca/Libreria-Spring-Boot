package com.moscadanilo.libreria.record;

import java.util.List;

//DTO della response (invio dei dati all'utente) per l'entità User
public record UserResponse(
        Integer id,
        String name,
        String lastName,
        List<UserBookResponse> books
) {
}
