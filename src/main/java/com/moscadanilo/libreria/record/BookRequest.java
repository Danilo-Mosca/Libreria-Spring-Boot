package com.moscadanilo.libreria.record;

import com.moscadanilo.libreria.model.Genre;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

// Record immutabile: usato come DTO di input
public record BookRequest(
    @NotNull(message = "Ci dev'essere un titolo")
    @NotBlank(message = "Il titolo non può essere vuoto")
    @Size(min = 3, max = 100, message = "Il titolo deve avere tra i 3 e i 100 caratteri")
    String title,

    @NotNull(message = "Ci dev'essere un autore")
    @NotBlank(message = "L'autore non può essere vuoto")
    @Size(min = 3, max = 100, message = "L'autore deve avere un nome compreso tra i 3 e i 100 caratteri")
    String author,

    @NotNull(message = "L'anno di pubblicazione è obbligatorio")
    @Min(value = 1450, message = "L'anno di pubblicazione non può essere antecedente all'invenzione della stampa")
    @Max(value = 2026, message = "L'anno di pubblicazione non può essere nel futuro")
    Integer yearOfPublication,

    @NotNull(message = "Il numero delle pagine è obbligatorio")
    @Positive(message = "Il numero delle pagine deve essere un valore maggiore di zero")
    Integer pages,

    @NotNull(message = "Ci dev'essere un genere")
    Genre genre,

    @NotNull(message = "La disponibilità è obbligatoria")
    Boolean available,

    @Size(max = 255, message = "La descrizione non può avere più di 255 caratteri")
    String description
) {}