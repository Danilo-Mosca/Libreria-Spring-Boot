package com.moscadanilo.libreria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
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
        // Altrimenti salvo il libro sul database e successivamente faccio un redirect alla show del libro che ho appena preso in prestito
        borrowingRepository.save(formBorrowing);
        return "redirect:/books/" + formBorrowing.getBook().getId();    //Una volta aggiunto il prestito reindirizzo alla show del libro che ho appena preso in prestito
    }

    /* METODO CHE RESTITUISCA UNA EDIT DA COMPILARE (CON DATI GIA' INSERITI) */

    /* METODO CHE EFFETTUI UNA UPDATE VERA E PROPRIA */
}
