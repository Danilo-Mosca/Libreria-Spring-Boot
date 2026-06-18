package com.moscadanilo.libreria.record;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

//DTO della request (richiesta ricevuta dall'utente) per l'entità User
public record UserRequest(

        @NotNull(message = "Il nome è obbligatorio")
        @NotBlank(message = "Il nome non può essere vuoto")
        @Size(min = 2, max = 50, message = "Il nome deve avere tra 2 e 50 caratteri")
        String name,

        @NotNull(message = "Il cognome è obbligatorio")
        @NotBlank(message = "Il cognome non può essere vuoto")
        @Size(min = 2, max = 50, message = "Il cognome deve avere tra 2 e 50 caratteri")
        String lastName
) {
}