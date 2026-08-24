package com.moscadanilo.libreria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moscadanilo.libreria.model.Borrowing;
import com.moscadanilo.libreria.repository.BorrowingRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/borrowings")
public class BorrowingController {

    private final BorrowingRepository borrowingRepository;  // dichiaro una variabile di tipo private final di BookRepositor

    // Iniezione via costruttore (raccomandata)
    // Iniezione dipendenze tramite costruttore ed essendo un solo costruttore si può omettere @Autowired in quanto lo farà automaticamente
    @Autowired
    public BorrowingController(BorrowingRepository borrowingRepository){
        this.borrowingRepository = borrowingRepository;
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("borrowing") Borrowing formBorrowing, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            // Inoltre passo l'enum dei generi dei libri altrimenti questi non saranno più visibili nelle "option" della "select" al reindirizzamento nella pagina "create" con gli errori (la select del template fallisce perché ${genres} è null)
            return "/borrowings/create-or-edit";
        }
        // Altrimenti salvo il prestito sul database e successivamente faccio un redirect alla show del libro che ho appena preso in prestito e che conterrà nella lista dei prestiti anche l'ultimo prestito da me creato
        borrowingRepository.save(formBorrowing);
        return "redirect:/books/" + formBorrowing.getBook().getId();    //Una volta aggiunto il prestito reindirizzo alla show del libro che ho appena preso in prestito
    }

    /* METODO CHE RESTITUISCA UNA EDIT DA COMPILARE (CON DATI GIA' INSERITI) */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model){
        // Passo un oggetto che conterrà il prestito da modificare
        model.addAttribute("borrowing", borrowingRepository.findById(id).get());
        // Restituisco un valore che verrà controllato dal form per capire se sto eseguendo una "create" o una "edit", e in tal caso il form verrà reindirizzato nella giusta pagina
        model.addAttribute("edit", true);
        return "borrowings/create-or-edit";
    }
    
    /* METODO CHE EFFETTUI UNA UPDATE VERA E PROPRIA (CON VALIDAZIONE) */
    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("borrowing") Borrowing formBorrowing, BindingResult bindingResult, Model model){
        // Se ho degli errori ritorno la pagina edit in GET con gli errori
        if (bindingResult.hasErrors()) {
            return "/borrowings/create-or-edit";
        }
        // Altrimenti salvo il prestito sul database e successivamente faccio un redirect alla show del libro che ho appena preso in prestito e che conterrà nella lista dei prestiti anche il prestito da me modificato
        borrowingRepository.save(formBorrowing);
        return "redirect:/books/" + formBorrowing.getBook().getId();    //Una volta modificato il prestito reindirizzo alla show del libro che ho appena preso in prestito
    }
}
