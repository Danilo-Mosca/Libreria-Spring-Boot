package com.moscadanilo.libreria.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moscadanilo.libreria.model.Borrowing;

import jakarta.validation.Valid;

@Controller
@RequestMapping("/borrowings")
public class BorrowingController {

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("borrowing") Borrowing formBorrowing, BindingResult bindingResult,
            Model model) {

        if (bindingResult.hasErrors()) {
            // Inoltre passo l'enum dei generi dei libri altrimenti questi non saranno più visibili nelle "option" della "select" al reindirizzamento nella pagina "create" con gli errori (la select del template fallisce perché ${genres} è null)
            return "/borrowings/create";
        }
        // Altrimenti salvo il libro sul database e successivamente faccio un redirect alla show del libro che ho appena preso in prestito
        return "redirect:/borrowings";
    }
}
