package com.moscadanilo.libreria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.moscadanilo.libreria.model.Book;
import com.moscadanilo.libreria.model.Genre;

@Repository
public interface BookRepository extends JpaRepository<Book, Integer> {
    // recupera tutti i libri
    List<Book> findAll();

    // metodo per recuperare un libro dal titolo
    Optional<Book> findByTitleIgnoreCase(String title);

    // metodo per recuperare una lista di libri dal genere
    List<Book> findByGenre(Genre genre);

    // metodo per recuperare una lista dall'autore
    List<Book> findByAuthorIgnoreCase(String author);

    // metodo per controllare se un libro esiste per id (usato nel delete di
    // BookController)
    boolean existsById(int id);

    // metodo per controllare se un libro esiste per titolo
    boolean existsByTitleIgnoreCase(String title);

    // recupera libri per numero pagine
    List<Book> findByPages(Integer pages);

    @Query("SELECT r FROM Book r WHERE LOWER(r.author) = LOWER(:author) AND LOWER(r.title) = LOWER(:title) ORDER BY r.author ASC")
    Optional<Book> findBookByAuthorAndTitle(@Param("author") String author, @Param("title") String title);

    // Metodo che recupera un libro che contiene nel suo titolo la stringa passata
    // dall'utente
    public List<Book> findByTitleContaining(String title);
}