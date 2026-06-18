package com.moscadanilo.libreria.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.moscadanilo.libreria.exceptions.BookNotFoundException;
import com.moscadanilo.libreria.exceptions.BookNotPrenotedException;
import com.moscadanilo.libreria.exceptions.BookUnavailableException;
import com.moscadanilo.libreria.exceptions.UserFullNameNotFoundException;
import com.moscadanilo.libreria.exceptions.UserLastNameNotFoundException;
import com.moscadanilo.libreria.exceptions.UserNameNotFoundException;
import com.moscadanilo.libreria.exceptions.UserNotFoundException;
import com.moscadanilo.libreria.model.Book;
import com.moscadanilo.libreria.model.User;
import com.moscadanilo.libreria.record.UserBookResponse;
import com.moscadanilo.libreria.record.UserRequest;
import com.moscadanilo.libreria.record.UserResponse;
import com.moscadanilo.libreria.repository.BookRepository;
import com.moscadanilo.libreria.repository.UserRepository;

@Service
public class UserService {
    
    private final UserRepository userRepository; // dichiaro una variabile di tipo private final di UserRepository
    private final BookRepository bookRepository; // dichiaro una variabile di tipo private final di BookRepository

    // Dependency Injection tramite costruttore
    public UserService(UserRepository userRepository, BookRepository bookRepository) {
        this.userRepository = userRepository;
        this.bookRepository = bookRepository;
    }

    // GET ALL USERS
    public List<UserResponse> findAll() {

        return userRepository.findAll()
                .stream()
                .map(this::toResponse)  // converte ogni User in UserResponse
                .toList();
    }

    // GET USER BY ID
    public UserResponse findById(Integer id) {
        // orElseThrow lancia l'eccezione se non trova corrispondenze con l'id
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
        // Ritorno lo user trasformato da User entità JPA a UserResponse
        return toResponse(user);
    }

    // GET USER BY NAME
    public List<UserResponse> findByName(String name) {

        List<User> users = userRepository.findByNameIgnoreCase(name);

        if (users.isEmpty()) {
            throw new UserNameNotFoundException(name);
        }
        //Ritorno gli users trasformati da entità User JPA a UserResponse così da non esporre l’entità database
        return users.stream()
                .map(this::toResponse)
                .toList();
    }

    // GET USER BY LASTNAME
    public List<UserResponse> findByLastName(String lastName) {

        List<User> users = userRepository.findByLastNameIgnoreCase(lastName);

        if (users.isEmpty()) {
            throw new UserLastNameNotFoundException(lastName);
        }
        // Ritorno gli users trasformati da entità User JPA a UserResponse così da non esporre l’entità database
        return users.stream()
                .map(this::toResponse)
                .toList();
    }

    // SEARCH USER BY NAME + LASTNAME
    //ricerca per query
    public List<UserResponse> searchUsers(String name, String lastName) {

        List<User> users;
        //Recupero lo user specifico per quel nome con
        // findByNameIgnoreCaseAndLastNameIgnoreCase(...) oppure orElseThrow lancia l'eccezione se non trova corrispondenze con il titolo ricercato
        if (name != null && lastName != null) {
            User user = userRepository
                    .findByNameIgnoreCaseAndLastNameIgnoreCase(name, lastName)
                    .orElseThrow(() -> new UserFullNameNotFoundException(name, lastName));

            return List.of(toResponse(user));

        } else if (name != null) {
            users = userRepository.findByNameIgnoreCase(name);

        } else if (lastName != null) {
            users = userRepository.findByLastNameIgnoreCase(lastName);

        } else {
            users = userRepository.findAll();
        }

        return users.stream()
                .map(this::toResponse)
                .toList();
    }

    // Metodo POST. Crea un utente
    public UserResponse save(UserRequest request) {
        //Prima trasformo la request ricevuta in entità JPA User
        User user = toEntity(request);
        //Salvo nel db
        User saved = userRepository.save(user);
        // Ritorno lo user trasformato da entità JPA User a UserResponse così da non esporre l’entità database
        return toResponse(saved);
    }

    // Metodo PUT. UPDATE USER
    public UserResponse update(Integer id, UserRequest requestUserUpdate) {
        // Controllo l'esistenza di quella riga nel database
        // se non trova l'utente da aggiornare, restituisce errore richiamando la classe UserNotFoundException dal @ControllerAdvice di GlobalExceptionHandler
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        //Se l'utente con quell'id esiste allora prendo i campi ricevuti dalla @RequestBody requestUserUpdate e li setto per quell'utente trovato così da modificarlo
        user.setName(requestUserUpdate.name());
        user.setLastName(requestUserUpdate.lastName());
        //Salvo nel database i nuovi dati
        User updated = userRepository.save(user);
        //Prima converto i dati aggiornati da entità JPA User a UserResponse e poi li ritorno al controller
        return toResponse(updated);
    }

    // DELETE USER
    public void delete(Integer id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
    }

    // PRESTITO LIBRO
    public UserResponse prestitoBook(Integer userId, Integer bookId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        //Se esistono entrambi
        // controllo disponibilità
        if (!book.isAvailable()) {
            throw new BookUnavailableException("Il libro non è disponibile");
        }

        // assegna libro all'utente
        book.setUser(user);

        // aggiorno stato libro
        book.setAvailable(false);

        // salvo book (user viene aggiornato via cascade o merge)
        bookRepository.save(book);

        return toResponse(user);
    }

    // RESTITUZIONE LIBRO
    public UserResponse restituzioneBook(Integer userId, Integer bookId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        Book book = bookRepository.findById(bookId)
                .orElseThrow(() -> new BookNotFoundException(bookId));

        // controllo che il libro sia dell'utente
        if (book.getUser() == null || !book.getUser().getId().equals(userId)) {
            throw new BookNotPrenotedException("Il libro non è associato a questo utente");
        }

        // Se esistono entrambi e il libro è associato a quell'utente
        // rimuovo relazione
        book.setUser(null);

        // reset disponibilità
        book.setAvailable(true);

        bookRepository.save(book);

        return toResponse(user);
    }


    // MAPPING: da UserRequest -> a entità JPA User
    private User toEntity(UserRequest request) {

        return new User(
                request.name(),
                request.lastName());
    }

    // MAPPING: da User entità JPA -> UserResponse
    private UserResponse toResponse(User user) {

        return new UserResponse(

                user.getId(),
                user.getName(),
                user.getLastName(),

                // Mapping lista libri associati all'utente
                user.getBooks()
                        .stream()
                        .map(book -> new UserBookResponse(
                                book.getId(),
                                book.getTitle(),
                                book.getAuthor(),
                                book.getYearOfPublication(),
                                book.getPages(),
                                book.getGenre().name(),
                                book.isAvailable(),
                                book.getDescription()
                        ))
                        .toList()
        );
    }
}
