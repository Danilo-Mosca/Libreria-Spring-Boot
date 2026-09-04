package com.moscadanilo.libreria.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// Creo una nuova identità (tabella) categories (categorie)
@Entity
@Table(name ="categories")
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @NotBlank(message = "La categoria non può esistere senza un titolo adeguato")
    @Size(min = 3, message = "Il nome della categoria deve avere almeno un minimo di tre caratteri")
    private String name;

    // Descrizione della categoria
    @Lob
    private String description;


    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // Procedo con l'associazione tra le due tabelle: categories e books
    @ManyToMany(mappedBy = "categories")    //stabilisco quale elemento viene mappato dall'altra parte (avrei potuto inserirlo anche nella entità Book al posto di questa). 
    // Con (mappedBy = "categories") specifico il nome della variabile d'istanza a cui fa riferimento nell'altro modello, ovvero la variabile "categories" (perchè nell'entità "Book" ho questa variabile d'istanza: private
    // List<Category> categories;)
    /* Aggiungo una relazione "many to many" tra il Book e la Category */
    private List<Book> books;   // Lista dei libri

}
