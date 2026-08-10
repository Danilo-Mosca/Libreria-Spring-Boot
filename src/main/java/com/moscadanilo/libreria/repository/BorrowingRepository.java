package com.moscadanilo.libreria.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.moscadanilo.libreria.model.Borrowing;

public interface BorrowingRepository extends JpaRepository<Borrowing, Integer> {
    
}
