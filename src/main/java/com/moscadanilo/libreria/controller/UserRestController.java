package com.moscadanilo.libreria.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.moscadanilo.libreria.record.UserRequest;
import com.moscadanilo.libreria.record.UserResponse;
import com.moscadanilo.libreria.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")   // prefisso comune per tutti gli endpoint di questa classe
public class UserRestController {
    // Dependency Injection del service
    private final UserService userService;

    // Costruttore. Iniezione dipendenze tramite costruttore
    //Iniezione dipendenze tramite costruttore ed essendo un solo costruttore si può omettere @Autowired
    //in quanto lo farà automaticamente
    @Autowired
    public UserRestController(UserService userService) {
        this.userService = userService;
    }

    // GET /api/users
    // Recupera la lista di tutti gli utenti con i relativi libri associati
    @GetMapping
    public ResponseEntity<List<UserResponse>> findAll() {

        // Richiama il service
        List<UserResponse> users = userService.findAll();

        // Restituisce status 200 OK + lista utenti
        return ResponseEntity.ok(users);
    }

    // GET /api/users/{id}
    // Recupera un singolo utente tramite id
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> findById(@PathVariable Integer id) {

        // Richiama il service
        UserResponse user = userService.findById(id);

        // Restituisce status 200 OK + utente
        return ResponseEntity.ok(user);
    }

    // GET /api/users/nome/{name}
    // Recupera lista utenti tramite nome
    @GetMapping("/nome/{name}")
    public ResponseEntity<List<UserResponse>> findByName(
            @PathVariable String name) {

        // Richiama il service
        List<UserResponse> users = userService.findByName(name);

        // Restituisce status 200 OK + lista utenti
        return ResponseEntity.ok(users);
    }

    // GET /api/users/cognome/{lastName}
    // Recupera lista utenti tramite cognome
    @GetMapping("/cognome/{lastName}")
    public ResponseEntity<List<UserResponse>> findByLastName(
            @PathVariable String lastName) {

        // Richiama il service
        List<UserResponse> users = userService.findByLastName(lastName);

        // Restituisce status 200 OK + lista utenti
        return ResponseEntity.ok(users);
    }

    // GET /api/users/search?name=Mario&lastName=Rossi
    // Ricerca utente tramite nome + cognome
    // Esempi:
    // /api/users/search?name=Mario
    // /api/users/search?lastName=Rossi
    // /api/users/search?name=Mario&lastName=Rossi
    // =========================================================
    @GetMapping("/search")
    public ResponseEntity<List<UserResponse>> searchUsers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String lastName) {

        // Richiama il service
        List<UserResponse> users = userService.searchUsers(name, lastName);
        // Restituisce status 200 OK + utente
        return ResponseEntity.ok(users);
    }

    @PostMapping
    public ResponseEntity<?> create(
            @Valid @RequestBody UserRequest request, BindingResult bindingResult) {

        // Controlla eventuali errori di validazione, se presenti li ritorno subito
        if (bindingResult.hasErrors()) {

            // Mappa key-value degli errori
            Map<String, String> errors = new HashMap<>();

            // Cicla tutti gli errori trovati
            bindingResult.getFieldErrors().forEach(error ->
            // Inserisce:
            // chiave -> nome campo
            // valore -> messaggio errore
            errors.put(
                    error.getField(),
                    error.getDefaultMessage()));

            // Restituisce status 400 BAD REQUEST
            return ResponseEntity.badRequest().body(errors);
        }

        // Salva nuovo utente
        UserResponse savedUser = userService.save(request);

        // Restituisce status 201 CREATED + utente creato
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(savedUser);
    }

    // PUT /api/users/{id}
    // Aggiorna un utente esistente
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Integer id, @Valid @RequestBody UserRequest request, BindingResult bindingResult) {

        // Controlla eventuali errori di validazione, se presenti li ritorno subito
        if (bindingResult.hasErrors()) {

            // Mappa key-value errori
            Map<String, String> errors = new HashMap<>();

            // Cicla gli errori trovati
            bindingResult.getFieldErrors().forEach(error ->

            errors.put(
                    error.getField(),
                    error.getDefaultMessage()));

            // Restituisce status 400 BAD REQUEST
            return ResponseEntity.badRequest().body(errors);
        }

        // Aggiorna utente
        UserResponse updatedUser = userService.update(id, request);

        // Restituisce status 200 OK + utente aggiornato
        return ResponseEntity.ok(updatedUser);
    }

    // DELETE /api/users/{id}
    // Elimina un utente tramite id
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Integer id) {
        // Richiama il service
        userService.delete(id);

        //Una volta eliminato l'utente con quello specifico id ritornare questo:
        //return ResponseEntity.ok(Map.of("message", "Utente eliminato", "id", id));
        
        // Oppure ancora meglio, siccome una delete restituisce status 204 NO CONTENT, perché generalmente una DELETE riuscita non deve restituire un body. Allora ritornare quanto di seguito:
        return ResponseEntity.noContent().build();
    }


    
    // PUT /api/users/{userId}/prestito/{bookId}
    // Permette a un utente di prendere in prestito un libro
    //
    // LOGICA:
    // - controlla esistenza user
    // - controlla esistenza book
    // - verifica che book.available = true
    // - assegna libro all’utente
    // - imposta available = false
    @PutMapping("/{userId}/prestito/{bookId}")
    public ResponseEntity<UserResponse> prestitoBook(
            @PathVariable Integer userId,
            @PathVariable Integer bookId) {

        UserResponse response = userService.prestitoBook(userId, bookId);

        return ResponseEntity.ok(response);
    }

    // PUT /api/users/{userId}/restituzione/{bookId}
    // Permette a un utente di restituire un libro
    //
    // LOGICA:
    // - controlla esistenza user
    // - controlla esistenza book
    // - verifica che il libro appartenga all’utente
    // - rimuove associazione user-book
    // - imposta available = true
    @PutMapping("/{userId}/restituzione/{bookId}")
    public ResponseEntity<UserResponse> restituzioneBook(
            @PathVariable Integer userId,
            @PathVariable Integer bookId) {

        UserResponse response = userService.restituzioneBook(userId, bookId);

        return ResponseEntity.ok(response);
    }
}
