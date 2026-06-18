package com.moscadanilo.libreria.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.moscadanilo.libreria.exceptions.BookAuthorNotFoundException;
import com.moscadanilo.libreria.exceptions.BookGenreNotFoundException;
import com.moscadanilo.libreria.exceptions.BookNotFoundException;
import com.moscadanilo.libreria.exceptions.BookTitleNotFoundException;
import com.moscadanilo.libreria.model.Book;
import com.moscadanilo.libreria.model.Genre;
import com.moscadanilo.libreria.record.BookRequest;
import com.moscadanilo.libreria.record.BookResponse;
import com.moscadanilo.libreria.repository.BookRepository;

@Service
public class BookService {

    private final BookRepository bookRepository; // dichiaro una variabile di tipo private final di BookRepository

    // Iniezione via costruttore (raccomandata)
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // GET /api/books → lista di tutte i libri
    public List<BookResponse> findAll() {
        return bookRepository.findAll()
                .stream()
                .map(this::toResponse) // converte ogni Book in BookResponse
                .toList();
    }

    // Metodo che recupera il singolo libro per id
    // GET /api/books/{id} → singolo libro per ID
    public BookResponse findById(Integer id) {
        // orElseThrow lancia l'eccezione se non trova corrispondenze con l'id
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        //Ritorno il book trasformato da Book entità JPA a BookResponse
        return toResponse(book);
    }

    // Metodo che recupera i libri per titolo
    // GET /api/books/titolo/{title} → singolo libro per titolo
    public BookResponse getBookByTitle(String title) {
        // orElseThrow lancia l'eccezione se non trova corrispondenze con il titolo ricercato
        Book book = bookRepository.findByTitleIgnoreCase(title).orElseThrow(() -> new BookTitleNotFoundException(title));
        // Ritorno il book trasformato da Book entità JPA a BookResponse
        return toResponse(book);
    }

    // Metodo che recupera i libri per genere
    // GET /api/books/genere/{genre} → lista di libri per genere
    public List<BookResponse> getBooksByGenre(Genre genre) {

        List<Book> books = bookRepository.findByGenre(genre);

        if (books.isEmpty()) {
            throw new BookGenreNotFoundException(genre);
        }
        // Ritorno il book trasformato da Book entità JPA a BookResponse
        return books.stream().map(this::toResponse).toList();
    }

    // Metodo che recupera i libri per autore
    // GET /api/books/autore/{author} → lista di libri per genere
    public List<BookResponse> getBooksByAuthor(String author) {
        List<Book> books = bookRepository.findByAuthorIgnoreCase(author);

        if (books.isEmpty()) {
            throw new BookAuthorNotFoundException(author);
        }
        // Ritorno il book trasformato da Book entità JPA a BookResponse
        return books.stream().map(this::toResponse).toList();
    }

    // POST /api/books → crea un nuovo libro
    public BookResponse save(BookRequest newBook) {
        // Controllo se esiste già un libro con lo stesso titolo
        if (bookRepository.existsByTitleIgnoreCase(newBook.title())) {
            throw new IllegalArgumentException(
                    "Esiste già un libro con il titolo: " + newBook.title());
        }
        //Converto BookRequest in Book
        Book book = toEntity(newBook);
        //Salvo l'entità
        Book saved = bookRepository.save(book);
        //Riconverto da Book a BookRequest per ritornarlo al controller
        return toResponse(saved);
    }

    // PUT /api/books/{id} → aggiorna un libro esistente
    public BookResponse update(Integer id, BookRequest bookDetails) {
        // Controllo l'esistenza di quella riga nel database
        // se non trova il libro da aggiornare, restituisce errore richiamando la classe
        // BookNotFoundException dal @ControllerAdvice di GlobalExceptionHandler
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));

        // Visto che book esiste prendo setto i campi di Book con i nuovi valori ricevuti dalla @RequestBody Book bookDetails
        book.setTitle(bookDetails.title());
        book.setAuthor(bookDetails.author());
        book.setPages(bookDetails.pages());
        book.setGenre(bookDetails.genre());
        book.setYearOfPublication(bookDetails.yearOfPublication());
        book.setAvailable(bookDetails.available());
        book.setDescription(bookDetails.description());
        // Salvo nel database e nella variabile updated di tipo Book
        Book updated = bookRepository.save(book);
        //Converto Book in BookResponse e ritorno al controller
        return toResponse(updated);
    }

    // DELETE /api/books/{id} → elimina un libro
    public void delete(Integer id) {
        Book book = bookRepository.findById(id).orElseThrow(() -> new BookNotFoundException(id));
        bookRepository.delete(book);
    }

    // GET /api/runs/search?author=nomeAutore&title=nomeTitolo -> ricerca per query
    // string
    public BookResponse search(String author, String title) {
        Book book = bookRepository.findBookByAuthorAndTitle(author, title).orElseThrow(() -> new IllegalArgumentException(
                "Nessun libro trovato con autore '" + author +
                        "' e titolo '" + title + "'"));

        // Converto Book in BookResponse e ritorno al controller
        return toResponse(book);
    }
    
    // Mapping: BookRequest → Book (entità JPA)
    private Book toEntity(BookRequest request) {
        Book book = new Book(
            request.title(),
            request.author(),
            request.yearOfPublication(), 
            request.pages(),
            request.genre(),
            request.available(),
            request.description()
        );
        return book;
    }

    // Mapping: Book (entità JPA) → BookResponse
    private BookResponse toResponse(Book book) {
        return new BookResponse(
                book.getId(),
                book.getTitle(),
                book.getAuthor(),
                book.getYearOfPublication(),
                book.getPages(),
                book.getGenre().name(),  // converte enum in stringa
                book.isAvailable(),
                book.getDescription()
        );
    }
}
