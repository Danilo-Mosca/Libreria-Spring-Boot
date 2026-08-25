package com.moscadanilo.libreria.model;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    @NotNull(message = "Ci dev'essere un titolo")
    @NotBlank(message = "Il titolo non può essere vuoto")
    @Size(min = 3, max = 100, message = "Il titolo deve avere tra i 3 e i 100 caratteri")
    private String title;

    @Column(nullable = false, length = 100)
    @NotNull(message = "Ci dev'essere un autore")
    @NotBlank(message = "L''autore non può essere vuoto")
    @Size(min = 3, max = 100, message = "L''autore deve avere un nome compreso tra i 3 e i 100 caratteri")
    private String author;

    @Column(nullable = false)
    @NotNull(message = "L''anno di pubblicazione è obbligatorio")
    @Min(value = 1450, message = "L''anno di pubblicazione non può essere antecedente all'invenzione della stampa")
    @Max(value = 2026, message = "L''anno di pubblicazione non può essere nel futuro")
    private Integer yearOfPublication;

    @Column(nullable = false)
    @NotNull(message = "La disponibilità è obbligatoria")
    private boolean available = true;

    @Column(nullable = false)
    @NotNull(message = "Devi inserire obbligatoriamente un numero di copie")
    @Min(value = 0, message = "Il numero di copie non può essere negativo")
    private Integer numberOfCopies;

    @Column(length = 255)
    @Size(max = 255, message = "La descrizione non può avere più di 255 caratteri")
    private String description;

    @Column(nullable = false)
    @NotNull(message = "Il numero delle pagine è obbligatorio")
    @Positive(message = "Il numero delle pagine deve essere un valore maggiore di zero")
    private Integer pages;

    @Enumerated(EnumType.STRING) // salva il nome dell'enum come stringa
                                 // ("TECH"/"FICTION"/"HISTORY"/"ROMANCE"/"THRILLER"/"FANTASY"/"BIOGRAPHY")
    @Column(nullable = false)
    @NotNull(message = "Ci dev''essere un genere")
    private Genre genre;

    // ── Relazione: molte Book → un User (relazione molti a uno)
    // ──────────────────────────────────────
    @ManyToOne
    @JoinColumn(name = "user_id") // nome della FK nella tabella Books
    // Oppure anche @JoinColumn(name = "user_id", nullable = true)
    private User user;

    /* AGGIUNTA DI UNA RELAZIONE TRA UN LIBRO E 0,1,2,3,4,5 O PIU' PRESTITI */
    @OneToMany(mappedBy = "book") // Specifico che si tratta di una relazione One to Many che si basa sull'entità
                                  // book (no books, perchè si tratta del singolo libro non dell'insieme di libri)
    private List<Borrowing> borrowings; // Lista dei prestiti

    /* Se avessi aggiunto i seguenti parametri:
    @OneToMany(mappedBy = "book", cascade = { CascadeType.REMOVE })
    avrei cancellato qualsiasi libro con i relativi prestiti ad esso associati senza modificare il metodo "delete" iniziale presente in BookController
    */
   
    // Getter e setter della variabile d'istanza borrowings della tabella dipendende
    // "borrowings"
    public List<Borrowing> getBorrowings() {
        return this.borrowings;
    }

    public void setBorrowings(List<Borrowing> borrowings) {
        this.borrowings = borrowings;
    }

    // Costruttore no-arg
    /*
     * LO COMMENTO PROVVISORIAMENTE USANDO UN COSTRUTTORE SENZA ARGOMENTI PUBBLICO
     * protected Book() {
     * }
     */
    // Costruttore no-arg pubblico (provvisorio)
    public Book() {
    }

    // Costruttore completo per uso applicativo
    public Book(String title, String author, Integer yearOfPublication, Integer pages, Genre genre, boolean available, Integer numberOfCopies,
            String description) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.pages = pages;
        this.genre = genre;
        this.available = available;
        this.numberOfCopies = numberOfCopies;
        this.description = description;
    }

    // Getter e Setter
    public Integer getId() {
        return id;
    }
    // Setter di id per permettere il salvataggio dei borrowing (prenotazioni) associate all'id di quel libro selezionato
    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getYearOfPublication() {
        return yearOfPublication;
    }

    public void setYearOfPublication(Integer yearOfPublication) {
        this.yearOfPublication = yearOfPublication;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Integer getNumberOfCopies() {
        return numberOfCopies;
    }

    public void setNumberOfCopies(Integer numberOfCopies) {
        this.numberOfCopies = numberOfCopies;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Getter e setter di User per la relazione many to 1. Detti anche "metodi
    // helper"
    // Utente associato a quel libro prenotato. Sempre se il libro è stato prenotato
    // da qualche utente
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
    // ----------------------

    // Override del metodo toString() aggiornato
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", yearOfPublication=" + yearOfPublication +
                ", pages=" + pages +
                ", genre=" + genre +
                ", available=" + available +
                ", description=" + description +
                '}';
        /*
         * Oppure avrei potuto scrivere così:
         * return String.format(
         * "%d: Title: %s, Author: %s, Anno di pubblicazionne: %d, Pagine: %d, Genere: %s, Disponibilità: %b, Descrizione: %s"
         * ,
         * this.id, this.title, this.author, this.yearOfPublication, this.pages,
         * this.genre, this.available,
         * this.description);
         */
    }
}