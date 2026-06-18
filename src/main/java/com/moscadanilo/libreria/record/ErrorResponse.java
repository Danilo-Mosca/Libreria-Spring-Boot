package com.moscadanilo.libreria.record;

import java.time.LocalDateTime;

// Un Record è perfetto per oggetti immutabili come la risposta di errore
public record ErrorResponse(String message, int status, LocalDateTime timestamp) {
}