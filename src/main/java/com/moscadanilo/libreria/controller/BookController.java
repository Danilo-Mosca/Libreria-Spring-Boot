package com.moscadanilo.libreria.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.moscadanilo.libreria.model.Book;
import com.moscadanilo.libreria.model.Borrowing;
import com.moscadanilo.libreria.model.Genre;
import com.moscadanilo.libreria.repository.BookRepository;

import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


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

    // GET cerca per query il libro o i libri contentente come titolo la stringa passata come parametro
    @GetMapping("/searchbytitle") // Esempio http://localhost:8080/books/searchbytitle?title=design%20patt -----> il sistema troverà il o i libri con titolo "Design Patt", che nel nostro caso sarà Design Pattern
    public String searchByTitle(@RequestParam(name = "title") String title, Model model) {
        List<Book> books = bookRepository.findByTitleContaining(title);
        model.addAttribute("books", books);
        return "/books/index";
    }

    // GET cerca per query il libro o i libri contentente come titolo "o" come nome autore la stringa passata come parametro
    @GetMapping("/searchbytitleorauthor")   
    // Esempio con titolo passato come query string:
    // http://localhost:8080/books/searchbytitleorauthor?query=design%20patt -----> il sistema troverà il o i libri con titolo "Design Patt", che nel nostro caso sarà Design Pattern
    // Esempio con autore passato come query string:
    // http://localhost:8080/books/searchbytitleorauthor?query=Dan Brown -----> il sistema troverà il o i libri che hanno come autore "Dan Brown"
    public String searchByTitleOrAuthor(@RequestParam(name = "query") String query, Model model) {
        List<Book> books = bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(query, query);
        model.addAttribute("books", books);
        return "/books/index";
    }

    /* CREAZIONE DI NUOVI LIBRI */
    @GetMapping("/create")
    public String create(Model model){
        // Passo un oggetto nuovo di tipo Book
        model.addAttribute("book", new Book());
        // Passo l'enum dei generi
        model.addAttribute("genres", Genre.values());
        return "/books/create";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("book") Book formBook, BindingResult bindingResult, Model model) {
        // Se ho degli errori ritorno la pagina create con gli errori
        if (bindingResult.hasErrors()) {
            // Inoltre passo l'enum dei generi dei libri altrimenti questi non saranno più visibili nelle "option" della "select" al reindirizzamento nella pagina "create" con gli errori (la select del template fallisce perché ${genres} è null)
            model.addAttribute("genres", Genre.values());
            return "/books/create";
        }
        // Altrimenti salvo il libro sul database e successivamente faccio un redirect alla pagina contenente tutti i libri
        bookRepository.save(formBook);
        return "redirect:/books";
    }

    /* MODIFICA (UPDATE) DI LIBRI ESISTENTI */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        // Passo un oggetto che conterrà il libro da modificare
        model.addAttribute("book", bookRepository.findById(id).get());
        // Passo l'enum dei generi
        model.addAttribute("genres", Genre.values());
        return "/books/edit";
    }

    @PostMapping("/edit/{id}")
    public String update(@PathVariable("id") Integer id,
                         @Valid @ModelAttribute("book") Book formBook, 
                         BindingResult bindingResult, 
                         Model model) {
        // Se ho degli errori ritorno la pagina edit in GET con gli errori
        if (bindingResult.hasErrors()) {
            // Inoltre passo l'enum dei generi dei libri altrimenti questi non saranno più
            // visibili nelle "option" della "select" al reindirizzamento nella pagina
            // "edit" con gli errori (la select del template fallisce perché ${genres} è
            // null)
            model.addAttribute("genres", Genre.values());
            return "/books/edit";
        }
        // Altrimenti faccio l'aggiornamento dei dati creando un oggetto di tipo Book
        Book book = bookRepository.findById(id).get();

        // Inserisco i valori ricevuti dal form nell'oggetto di tipo Book
        book.setTitle(formBook.getTitle());
        book.setAuthor(formBook.getAuthor());
        book.setYearOfPublication(formBook.getYearOfPublication());
        book.setPages(formBook.getPages());
        book.setGenre(formBook.getGenre());
        book.setAvailable(formBook.isAvailable());
        book.setNumberOfCopies(formBook.getNumberOfCopies());
        book.setDescription(formBook.getDescription());
        // Infine salvo il libro modificato sul database e successivamente faccio un redirect alla pagina contenente tutti i libri
        bookRepository.save(book);
        return "redirect:/books";
    }

    /* CANCELLAZIONE DI LIBRI ESISTENTI */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        // Cancello il libro con quello specifico id presente nel database grazie al metodo fornito dall'ORM Spring Data JPA
        bookRepository.deleteById(id);
        // Faccio un redirect alla pagina contenente tutti i libri
        return "redirect:/books";
    }

    /* METODO PRENDI IN PRESTITO UN LIBRO */
    @GetMapping("/{id}/borrow")         // localhost:8080/books/id/borrow
    public String borrow(@PathVariable("id") Integer id, Model model) {
        Borrowing borrowing = new Borrowing();
        borrowing.setBook(bookRepository.findById(id).get());
        model.addAttribute("borrowing", borrowing);        
        return "borrowings/create";
    }
    
}
