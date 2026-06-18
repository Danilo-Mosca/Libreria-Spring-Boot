package com.moscadanilo.libreria.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(nullable = false, length = 100)
    private String author;

    @Column(nullable = false)
    private Integer yearOfPublication;

    @Column(nullable = false)
    private boolean available = true;

    @Column( length = 255)
    private String description;

    @Column(nullable = false)
    private Integer pages;

    @Enumerated(EnumType.STRING)    // salva il nome dell'enum come stringa ("INDOOR"/"OUTDOOR")
    @Column(nullable = false)
    private Genre genre;

    // ── Relazione: molte Book → un User (relazione molti a uno) ──────────────────────────────────────
    @ManyToOne
    @JoinColumn(name = "user_id")  // nome della FK nella tabella Books
    //Oppure anche  @JoinColumn(name = "user_id", nullable = true)
    private User user;

    // Costruttore no-arg
    protected Book() {
    }

    // Costruttore completo per uso applicativo
    public Book(String title, String author, Integer yearOfPublication, Integer pages, Genre genre, boolean available, String description) {
        this.title = title;
        this.author = author;
        this.yearOfPublication = yearOfPublication;
        this.pages = pages;
        this.genre = genre;
        this.available = available;
        this.description = description;
    }

    // Getter e Setter
    public Integer getId() {
        return id;
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

    public String getDescription(){
        return this.description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    // Getter e setter di User per la relazione many to 1. Detti anche "metodi helper"
    // Utente associato a quel libro prenotato. Sempre se il libro è stato prenotato da qualche utente
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
    }
}
