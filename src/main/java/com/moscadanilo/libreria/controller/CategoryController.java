package com.moscadanilo.libreria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moscadanilo.libreria.model.Category;
import com.moscadanilo.libreria.model.Genre;
import com.moscadanilo.libreria.repository.CategoryRepository;

import jakarta.validation.Valid;

@Controller
@RequestMapping ("/categories")
public class CategoryController {

    private final CategoryRepository categoryRepository;    // dichiaro una variabile di tipo private final di CategoryRepository

    // Iniezione via costruttore (raccomandata)
    // Iniezione dipendenze tramite costruttore ed essendo un solo costruttore si può omettere @Autowired in quanto lo farà automaticamente
    @Autowired
    public CategoryController(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    // Ritorna tutta la lista di tutte le categorie
    @GetMapping
    public String index(Model model){
        model.addAttribute("categories", categoryRepository.findAll());
        return "categories/index";
    }

    /* CREAZIONE DI NUOVE CATEGORIE */
    @GetMapping("/create")
    public String create(Model model){
        // Passo un oggetto nuovo di tipo Category
        model.addAttribute("category", new Category());
        return "categories/create-or-edit";
    }

    @PostMapping("/create")
    public String store(@Valid @ModelAttribute("category") Category formCategory, BindingResult bindingResult, Model model){
        // Se ho degli errori ritorno la pagina create con gli errori
        if (bindingResult.hasErrors()) {
            return "/categories/create-or-edit";
        }
        // Altrimenti salvo la categoria sul database e successivamente faccio un redirect alla pagina contenente tutte le categorie
        categoryRepository.save(formCategory);
        return "redirect:/categories";
    }
}
