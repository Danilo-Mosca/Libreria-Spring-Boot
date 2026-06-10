package com.moscadanilo.libreria;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication		// ← annotazione "tutto in uno"
public class LibreriaApplication {

	public static void main(String[] args) {
		// Avvia il contesto Spring e il server embedded
		SpringApplication.run(LibreriaApplication.class, args);
	}
}