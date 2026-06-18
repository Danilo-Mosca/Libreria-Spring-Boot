package com.moscadanilo.libreria.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moscadanilo.libreria.model.Genre;
import com.moscadanilo.libreria.record.BookRequest;
import com.moscadanilo.libreria.record.BookResponse;
import com.moscadanilo.libreria.service.BookService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/books")   // prefisso comune per tutti gli endpoint di questa classe
public class BookRestController {

    private final BookService bookService;

    //Iniezione dipendenze tramite costruttore ed essendo un solo costruttore si può omettere @Autowired
    //in quanto lo farà automaticamente
    @Autowired
    public BookRestController(BookService bookservice){
        this.bookService = bookservice;
    }

    //Get per ritornare tutta la lista dei libri
    @GetMapping
    public ResponseEntity<List<BookResponse>> findAll(){
        List<BookResponse> books = bookService.findAll();
        return ResponseEntity.ok(books);
    }

    // Get per ritornare il libri per id
    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> getBookById(@PathVariable Integer id){
        BookResponse book = bookService.findById(id);
        return ResponseEntity.ok(book);
    }

    // Get per recuperare il libro per titolo
    @GetMapping("/titolo/{title}")
    public ResponseEntity<BookResponse> getBookByTitle(@PathVariable String title){
        return ResponseEntity.ok(bookService.getBookByTitle(title));
    }

    // Metodo che recupera i libri per genere
    @GetMapping("/genere/{genre}")
    public ResponseEntity<List<BookResponse>> getBooksByGenre(@PathVariable String genre) {
        Genre normalizedGenre = Genre.valueOf(genre.toUpperCase());

        return ResponseEntity.ok(bookService.getBooksByGenre(normalizedGenre));
    }

    // Metodo che recupera i libri per autore
    @GetMapping("/autore/{author}")
    public ResponseEntity<List<BookResponse>> getBooksByAuthor(@PathVariable String author) {
        List<BookResponse> book = bookService.getBooksByAuthor(author);
        return ResponseEntity.ok(book);


    }

    // POST crea un nuovo libro
    // BindingResult permette di catturare gli errori di validazione nel metodo del

    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody BookRequest newBook, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {

            Map<String, String> errors = new HashMap<>();

            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage())
            );

            // Restituisce status 400 BAD REQUEST
            return ResponseEntity.badRequest().body(errors);
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(bookService.save(newBook));
    }

    // PUT aggiorna un libro esistente
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody BookRequest bookDetails, BindingResult bindingResult) {

        // Controlla eventuali errori di validazione
        if (bindingResult.hasErrors()) {
            // Mappa key-value errori
            Map<String, String> errors = new HashMap<>();
            // Cicla gli errori trovati
            bindingResult.getFieldErrors().forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

            // Restituisce status 400 BAD REQUEST
            return ResponseEntity.badRequest().body(errors);
        }
        // Aggiorna utente e restituisce status 200 OK + utente aggiornato
        return ResponseEntity.status(HttpStatus.OK).body(bookService.update(id, bookDetails));
    }

    // DELETE elimina un libro
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        bookService.delete(id);
        //return ResponseEntity.status(HttpStatus.OK).body("Libro eliminato con successo!");
        
        //Una volta eliminato il libro con quel determinato id utilizzare questa return:
        //return ResponseEntity.ok(Map.of("message", "Libro eliminato", "id", id));
        
        // Oppure ancora meglio questa riga di seguito perché generalmente una DELETE riuscita non deve restituire un body:
        return ResponseEntity.noContent().build();
    }

    // GET cerca per query

    @GetMapping("/search")
    public ResponseEntity<BookResponse> search(@RequestParam String author, @RequestParam String title) {
        return ResponseEntity.ok(bookService.search(author, title));
    }
}
