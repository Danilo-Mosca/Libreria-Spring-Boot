package com.moscadanilo.libreria;

import java.time.LocalDateTime;

import com.moscadanilo.libreria.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.moscadanilo.libreria.record.ErrorResponse;
import org.springframework.http.converter.HttpMessageNotReadableException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Gestisce BookNotFoundException restituendo un Not Found
    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFound(BookNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    // Gestisce BookTitleNotFoundException restituendo un Not Found
    @ExceptionHandler(BookTitleNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleTitleNotFound(BookTitleNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    // Gestisce BookAuthorNotFoundException restituendo un Not Found
    @ExceptionHandler(BookAuthorNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleAuthorNotFound(BookAuthorNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    // Gestisce BookGenreNotFoundException restituendo un Not Found
    @ExceptionHandler(BookGenreNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleGenreNotFound(BookGenreNotFoundException exception) {
        ErrorResponse error = new ErrorResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);

    }

    // Gestisce l'eccezione della data di pubblicazione verificando che il valore
    // inserito sia un intero, altrimenti lancio l'eccezione
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleJsonParseError(HttpMessageNotReadableException exception) {

        ErrorResponse error = new ErrorResponse(
                "Il formato della data di pubblicazione non è valido. Deve essere un numero intero",
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());

        return ResponseEntity.badRequest().body(error);
    }

    // Genere non valido perchè input sbagliato, ovvero quando si inserisce un
    // genere che non esiste nell'enum
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> handleEnumError() {

        ErrorResponse error = new ErrorResponse(
                "Genere non valido. Valori accettati: TECH, FICTION, HISTORY, ROMANCE, THRILLER, FANTASY, BIOGRAPHY",
                400,
                java.time.LocalDateTime.now());

        return ResponseEntity.badRequest().body(error);
    }

    // Eccezione che si verifica con l'inserimento di una data di pubblicazione
    // (yearOfPublication) come stringa e non numerica
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException exception) {
        ErrorResponse error = new ErrorResponse(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Eccezione che gestisce UserNotFoundException quando l'id per quell'utente non
    // viene trovato nella tabella User
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleUserNotFound(UserNotFoundException exception) {

        ErrorResponse error = new ErrorResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Eccezione che gestisce UserNameNotFoundException quando non viene trovato il
    // nome per quell'utente nella tabella User
    @ExceptionHandler(UserNameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNameNotFound(UserNameNotFoundException exception) {

        ErrorResponse error = new ErrorResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Eccezione che gestisce UserNotFoundException quando non viene trovato il
    // cognome per quell'utente nella tabella User
    @ExceptionHandler(UserLastNameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleLastNameNotFound(UserLastNameNotFoundException exception) {

        ErrorResponse error = new ErrorResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Eccezione che gestisce UserNotFoundException quando non viene trovato il nome
    // + cognome per quell'utente nella tabella User
    @ExceptionHandler(UserFullNameNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleFullNameNotFound(UserFullNameNotFoundException exception) {

        ErrorResponse error = new ErrorResponse(
                exception.getMessage(),
                HttpStatus.NOT_FOUND.value(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // Eccezione che gestisce BookUnavailableException ovvero un utente ricerca un
    // libro e questo non è disponibile per il prestito (cioè quanto available =
    // false)
    @ExceptionHandler(BookUnavailableException.class)
    public ResponseEntity<ErrorResponse> handleBookUnavailable(BookUnavailableException exception) {

        ErrorResponse error = new ErrorResponse(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Eccezione che gestisce BookNotPrenotedException ovvero quando un utente
    // ricerca un libro che è disponibile e non risulta prenotato (cioè quanto
    // available = true)
    @ExceptionHandler(BookNotPrenotedException.class)
    public ResponseEntity<ErrorResponse> handleBookNotPrenoted(BookNotPrenotedException exception) {

        ErrorResponse error = new ErrorResponse(
                exception.getMessage(),
                HttpStatus.BAD_REQUEST.value(),
                LocalDateTime.now());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // Server Error (errore generico non gestito)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception exception) {
        ErrorResponse error = new ErrorResponse(
                "Errore interno al server: " + exception.getMessage(),
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}