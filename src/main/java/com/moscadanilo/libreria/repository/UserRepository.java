package com.moscadanilo.libreria.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.moscadanilo.libreria.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // Recupara tutti gli utenti
    List<User> findAll();

    // Trova utenti per nome (case insensitive)
    List<User> findByNameIgnoreCase(String name);
    /*
     * Questa è l'equivalente di
     * SELECT * FROM users
     * WHERE LOWER(name) = LOWER('danilo')
     */

    // Trova utenti per cognome (case insensitive)
    List<User> findByLastNameIgnoreCase(String lastName);

    // Trova utente per nome + cognome (case insensitive)
    Optional<User> findByNameIgnoreCaseAndLastNameIgnoreCase(String name, String lastName);
}