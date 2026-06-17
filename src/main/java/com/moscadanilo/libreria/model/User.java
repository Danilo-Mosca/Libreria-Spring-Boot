package com.moscadanilo.libreria.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String lastName;

    // ── Relazione inversa: un User → molti Book (relazione uno a molti) ──────────────────────────────
    @OneToMany(mappedBy = "user", // nome del campo @ManyToOne in Book
            cascade = { CascadeType.PERSIST, CascadeType.MERGE } // operazioni propagate ai figli
    )
    private List<Book> books = new ArrayList<>();

    // Costruttore no-arg
    protected User() {
    }

    // Costruttore completo per uso applicativo
    public User(String name, String lastname) {
        this.name = name;
        this.lastName = lastname;
    }

    // Getter e Setter
    public Integer getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return this.lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    // Getter e setter di Book per la relazione 1 to many

    // Lo userò per leggere i libri associati con quell'utente user.getBooks()
    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }

    // Override del metodo toString() aggiornato
    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", lastName='" + lastName + '\'' +
                '}';
    }

    // Metodi helper che mantengono la coerenza tra le due tabelle
    public void addBook(Book book) {
        books.add(book);
        book.setUser(this); // mantiene sincronizzato anche il lato Book
    }

    public void removeBook(Book book) {
        books.remove(book);
        book.setUser(null);
    }
}
