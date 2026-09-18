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

import com.moscadanilo.libreria.model.Book;
import com.moscadanilo.libreria.model.Category;
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

    // Ritorna le categorie per id
    @GetMapping("/{id}") // localhost:8080/categories/id
    public String show(@PathVariable("id") Integer id, Model model){
        model.addAttribute("category", categoryRepository.findById(id).get());
        return "/categories/show";
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

    /* MODIFICA (UPDATE) DI CATEGORIE ESISTENTI */
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Integer id, Model model) {
        // Passo un oggetto che conterrà il libro da modificare
        model.addAttribute("category", categoryRepository.findById(id).get());
        // Restituisco un valore che verrà controllato dal form per capire se sto eseguendo una "create" o una "edit", e in tal caso il form verrà reindirizzato nella giusta pagina
        model.addAttribute("edit", true);
        return "/categories/create-or-edit";
    }
    
    @PostMapping("/edit/{id}")
    public String update(@Valid @ModelAttribute("category") Category formCategory, BindingResult bindingResult, Model model) {
        // Se ho degli errori ritorno la pagina edit in GET con gli errori
        if (bindingResult.hasErrors()) {
            return "/categories/edit";
        }

        /* VECCHIA SOLUZIONE */
        // Altrimenti faccio l'aggiornamento dei dati creando un oggetto di tipo Category
        // Category category = categoryRepository.findById(id).get();

        // Inserisco i valori ricevuti dal form nell'oggetto di tipo Book
        // category.setName(formCategory.getName());
        // category.setDescription(formCategory.getDescription());
        // Infine salvo la categoria modificata sul database e successivamente faccio un redirect alla pagina contenente tutte le categorie
        /* FINE VECCHIA SOLUZIONE */
        
        /* NUOVA SOLUZIONE MOLTO PIU' SEMPLICE */
        categoryRepository.save(formCategory);
        return "redirect:/categories";
    }

    /* CANCELLAZIONE DI CATEGORIE ESISTENTI */
    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        Category categoryToDelete = categoryRepository.findById(id).get();  // prima cerco la categoria corrispondente a quell'id che voglio cancellare
        
        // Successivamente devo cancellare questa categoria su ogni libro che ce l'ha associata, ovvero per ogni libro che ha quella categoria associata, cancello quel campo del libro
        for (Book linkedBook : categoryToDelete.getBooks()) {
            // Se presente, cancello la categoria in quel libro
            linkedBook.getCategories().remove(categoryToDelete);
        }
        /* ORA CHE NON HO PIU' LIBRI CORRISPONDENTI A QUELLA CATEGORIA, CANCELLO LA CATEGORIA STESSA */
        categoryRepository.delete(categoryToDelete);    // Oppure va bene anche: categoryRepository.deleteById(id);
        // Oppure potrei cancellare il libro con quello specifico id presente nel database grazie al metodo fornito dall'ORM Spring Data JPA
        // categoryRepository.deleteById(id);

        // Infine faccio un redirect alla pagina contenente tutte le categorie
        return "redirect:/categories";
    }
}
