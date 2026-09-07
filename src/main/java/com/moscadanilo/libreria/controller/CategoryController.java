package com.moscadanilo.libreria.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.moscadanilo.libreria.repository.CategoryRepository;

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
}
