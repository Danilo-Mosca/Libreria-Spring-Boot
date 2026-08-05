package com.moscadanilo.libreria.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

// Creo una nuova identità (tabella) borrowings (prestiti)
@Entity
@Table(name = "borrowings")
public class Borrowing {

    @Id
    @GeneratedValue( strategy = GenerationType.IDENTITY)
    private Integer id;

    // Libro da cui dipendo (non libri, perché è una one to many: un prestito corrisponde ad uno e soltanto un libro)
    @ManyToOne
    @JoinColumn(name = "book_id", nullable = false)   // Specifico a quale chiave esterna devo far riferimento per recuperare quello specifico libro presente nella tabella "books" dato in prestito o da restituire (di solito gli si da il nome della tabella da cui si dipende al singolare più "_id" quindi sarà book_id. Infine specifico che non può essere null).

    private Book book;
    // Data di inizio prestito
    @NotNull(message = "La data di inizio prestito non può essere nulla")
    @PastOrPresent(message = "La data inserita per l'inizio del prestito non può essere una data futura")   // Regola che dice che la data in cui io vado a prendere in prestito il libro non può essere una data diversa da oggi o una data precedente a quella di oggi. Non permette che l'utente inserisca una data successiva a quella di oggi
    private LocalDate borrowinDate;

    // Data di ritorno del libro dato in prestito
    @PastOrPresent(message = "La data inserita per il ritorno del libro preso in prestito non può essere una data futura")  // Come per borrowingDate
    private LocalDate returnDate;

    // Stringa di tipo Lob ovvero long object (oggetto di grandi dimensioni)
    @Lob
    private String notes;
}
