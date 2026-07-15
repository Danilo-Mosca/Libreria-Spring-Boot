package com.moscadanilo.libreria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping
    public String index(Model model){
        List<Book> books = bookRepository.findAll();    // sarebbe l'equivalente di SELECT * FROM books => i cui risultati sono trasformati in una lista di oggetti di tipo Book

        // Aggiungo l'attributo che chiameerò "books" (il primo parametro), al quale assegnerò il valore della lista di oggetti contenuti nella variabile book (il secondo attributo)
        model.addAttribute("books", books);
        return "books/index";
    }
}
