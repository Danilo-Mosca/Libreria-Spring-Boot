package com.moscadanilo.libreria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moscadanilo.libreria.model.Book;
import com.moscadanilo.libreria.repository.BookRepository;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookRepository bookRepository; // dichiaro una variabile di tipo private final di BookRepository

    // Iniezione via costruttore (raccomandata)
    //Iniezione dipendenze tramite costruttore ed essendo un solo costruttore si può omettere @Autowired in quanto lo farà automaticamente
    @Autowired
    public BookController(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    // Ritorna tutta la lista dei libri
    @GetMapping
    public String index(Model model){
        List<Book> books = bookRepository.findAll();    // sarebbe l'equivalente di SELECT * FROM books => i cui risultati sono trasformati in una lista di oggetti di tipo Book

        // Aggiungo l'attributo che chiameerò "books" (il primo parametro), al quale assegnerò il valore della lista di oggetti contenuti nella variabile book (il secondo attributo)
        model.addAttribute("books", books);
        return "books/index";
    }

    // Ritorna i libri per id
    @GetMapping("/{id}")    // localhost:8080/books/id
    public String show(@PathVariable("id") Integer id, Model model){
        model.addAttribute("book", bookRepository.findById(id).get());
        return "/books/show";
    }

    // GET cerca per query il libro contentente come titolo la stringa passata come parametro
    @GetMapping("/searchbytitle")
    public String searchByTitle(@RequestParam(name = "title") String title, Model model) {
        List<Book> books = bookRepository.findByTitleContaining(title);
        model.addAttribute("books", books);
        return "/books/index";
    }
}
