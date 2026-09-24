# Libreria-Spring-Boot

Progetto di gestione di una libreria completo di view in **Thymeleaf** e **API REST**.

L'applicazione permette di gestire libri, categorie, utenti e prestiti, garantendo la disponibilità dei volumi tramite un sistema di prestito/restituzione basato su relazioni JPA.

## Caratteristiche

- **CRUD completo** su libri, categorie e prestiti tramite interfaccia web Thymeleaf
- **API REST** per la gestione di libri (`/api/books`) e utenti (`/api/users`)
- **Sistema di prestito e restituzione** dei libri con controllo automatico della disponibilità
- **Ricerca** dei libri per titolo, autore e genere
- **Validazione** dei dati in ingresso (Bean Validation) sia per le viste che per le API
- **Gestione centralizzata degli errori** con eccezioni custom e `@ControllerAdvice`
- **Approccio multi-livello**: Controller → Service → Repository (DTO Record per input/output)
- UI responsive con **Bootstrap 5**

## Tecnologie

| Tecnologia | Versione |
|------------|----------|
| Java | 25 |
| Spring Boot | 4.0.6 |
| Spring MVC | starter `spring-boot-starter-webmvc` |
| Spring Data JPA / Hibernate | starter `spring-boot-starter-data-jpa` |
| Thymeleaf | starter `spring-boot-starter-thymeleaf` |
| Bean Validation | starter `spring-boot-starter-validation` |
| MySQL | `mysql-connector-j` |
| Bootstrap (WebJar) | 5.3.8 |
| dotenv (WebJar) | 16.5.0 |
| Maven | `mvnw` (wrapper) |

## Struttura del progetto

```
src/main/java/com/moscadanilo/libreria
├── controller/          # Controller REST e controller per le viste Thymeleaf
│   ├── BookRestController      # API REST libri (/api/books)
│   ├── UserRestController      # API REST utenti (/api/users)
│   ├── BookController          # Viste libri (/books)
│   ├── CategoryController      # Viste categorie (/categories)
│   ├── BorrowingController     # Viste prestiti (/borrowings)
│   └── HomeController          # Homepage (/)
├── service/             # Logica di business
│   ├── BookService
│   └── UserService
├── model/               # Entità JPA e enum
│   ├── Book, User, Category, Borrowing, Genre
├── record/              # DTO immutabili (Request/Response)
│   ├── BookRequest, BookResponse, UserRequest, UserResponse, ...
├── repository/          # Repository Spring Data JPA
├── exceptions/          # Eccezioni custom
├── GlobalExceptionHandler.java   # Gestione centralizzata errori
└── LibreriaApplication.java      # Punto di ingresso
```

## Requisiti

- **JDK 25**
- **MySQL** (server attivo e raggiungibile)
- Maven (opzionale: è incluso il wrapper `mvnw`)

## Configurazione

1. Copia il file `.env.example` in `.env` e compila le variabili:

```env
DB_URL=jdbc:mysql://localhost:3306/nome_database
DB_USERNAME=root
DB_PASSWORD=password
```

> Le credenziali vengono lette tramite dotenv e non sono hardcoded nel codice.

2. Il database viene creato/aggiornato automaticamente all'avvio (`spring.jpa.hibernate.ddl-auto=update`).

3. Per popolare il database con i dati di esempio contenuti in `src/main/resources/data.sql`, abilita (solo al **primo avvio**) le righe commentate in `application.properties`:

```properties
spring.jpa.defer-datasource-initialization=true
spring.sql.init.mode=always
spring.sql.init.data-locations=classpath:data.sql
```

## Esecuzione

```bash
./mvnw spring-boot:run
```

Il comando `./mvnw spring-boot:run` avvia l'applicazione Spring Boot tramite il Maven Wrapper, senza bisogno di generare un `jar` o installare Maven sul sistema. In dettaglio:

1. **Compila il codice** sorgente (fase `compile` del ciclo di vita di Maven).
2. **Avvia il server Tomcat** incorporato in Spring Boot, leggendo la configurazione da `application.properties` e `.env` (credenziali MySQL).
3. **Crea/aggiorna lo schema** del database (`spring.jpa.hibernate.ddl-auto=update`) e, se abilitato in configurazione, esegue `data.sql` per i dati di esempio.

L'applicazione sarà disponibile su: <http://localhost:8080>

Note:

- La prima esecuzione può richiedere qualche istante perché scarica le dipendenze e compila il progetto.
- Il processo resta attivo finché non viene interrotto (Ctrl+C nel terminale; con le Spring Boot Dashboard corrisponde all'arresto dal pannello).
- Il comando non esegue i test: per quello si veda la sezione [Test](#test).

## Modello dati

| Entità | Tabella | Note |
|--------|---------|------|
| `User` | `users` | Utente registrato |
| `Book` | `books` | Libro, con genere (`Genre`), disponibilità e numero di copie |
| `Category` | `categories` | Categoria assegnabile ai libri |
| `Borrowing` | `borrowings` | Prestito di un libro con date di inizio/ritorno |

Relazioni principali:

- **User 1→N Book**: un utente può prendere in prestito più libri
- **Book N→M Category**: un libro può appartenere a più categorie (tabelle pivot `book_category`)
- **Book 1→N Borrowing**: un libro può avere più prestiti nel tempo

Genere (`Genre`): `TECH`, `FICTION`, `HISTORY`, `ROMANCE`, `THRILLER`, `FANTASY`, `BIOGRAPHY`.

## API REST

Base path: `http://localhost:8080`

### Libri — `/api/books`

| Metodo | Endpoint | Descrizione |
|--------|----------|-------------|
| GET | `/api/books` | Elenco di tutti i libri |
| GET | `/api/books/{id}` | Libro per id |
| GET | `/api/books/titolo/{title}` | Libro per titolo |
| GET | `/api/books/genere/{genre}` | Libri per genere |
| GET | `/api/books/autore/{author}` | Libri per autore |
| GET | `/api/books/search?author=&title=` | Ricerca per autore e titolo |
| POST | `/api/books` | Crea un nuovo libro (body `BookRequest`) |
| PUT | `/api/books/{id}` | Aggiorna un libro esistente |
| DELETE | `/api/books/{id}` | Elimina un libro |

### Utenti — `/api/users`

| Metodo | Endpoint | Descrizione |
|--------|----------|-------------|
| GET | `/api/users` | Elenco di tutti gli utenti (con libri associati) |
| GET | `/api/users/{id}` | Utente per id |
| GET | `/api/users/nome/{name}` | Utenti per nome |
| GET | `/api/users/cognome/{lastName}` | Utenti per cognome |
| GET | `/api/users/search?name=&lastName=` | Ricerca per nome e/o cognome |
| POST | `/api/users` | Crea un nuovo utente (body `UserRequest`) |
| PUT | `/api/users/{id}` | Aggiorna un utente |
| PUT | `/api/users/{userId}/prestito/{bookId}` | Prende in prestito un libro |
| PUT | `/api/users/{userId}/restituzione/{bookId}` | Restituisce un libro |
| DELETE | `/api/users/{id}` | Elimina un utente |

### Gestione errori

Gli errori vengono restituiti in formato JSON con la struttura `ErrorResponse`:

```json
{
  "message": "Libro non trovato con id: 99",
  "status": 404,
  "timestamp": "2026-09-24T10:00:00"
}
```

Le eccezioni custom sono gestite centralmente in `GlobalExceptionHandler` (HTTP 400 BAD REQUEST, 404 NOT FOUND, 500 INTERNAL SERVER ERROR).

## Interfaccia Web (Thymeleaf)

| Risorsa | Rotta | Descrizione |
|---------|-------|-------------|
| Home | `/` | Pagina iniziale |
| Libri | `/books` | Elenco, dettaglio, creazione (`/books/create`), modifica (`/books/edit/{id}`), eliminazione |
| Ricerca | `/books/searchbytitle?title=` | Per titolo |
| Ricerca | `/books/searchbytitleorauthor?query=` | Per titolo o autore |
| Prestito | `/books/{id}/borrow` | Nuovo prestito di un libro |
| Categorie | `/categories` | Elenco, dettaglio, creazione, modifica, eliminazione |
| Prestiti | `/borrowings` | Creazione e modifica prestiti |

## Test

```bash
./mvnw test
```

Il comando `./mvnw test` esegue la **fase `test` del ciclo di vita di Maven** utilizzando il Maven Wrapper (`mvnw`), uno script incluso nel progetto che scarica e avvia automaticamente una versione specifica di Maven, senza bisogno di averlo installato sul sistema. In dettaglio:

1. **Compila il codice**: esegue le fasi precedenti del ciclo di vita, ovvero `validate`, `compile` (compilazione del codice sorgente) e `test-compile` (compilazione delle classi di test).
2. **Esegue i test**: lancia tutti i test del progetto (unit test e test di integrazione, tipicamente JUnit) presenti in `src/test/java` (classi con suffisso `Test`, `Tests` o `TestCase`).
3. **Riporta il risultato**: genera un report dell'esito; se tutti i test passano il comando termina con successo (exit code 0), altrimenti si interrompe con un errore (exit code ≠ 0) indicando quale test è fallito.

Note:

- Il comando **non avvia l'applicazione** né genera il pacchetto finale (`jar`): si ferma dopo l'esecuzione dei test.
- Le dipendenze non ancora presenti in cache vengono scaricate automaticamente dai repository remoti.
- Per scavalcare l'esecuzione dei test durante una build si può usare `./mvnw package -DskipTests`.

Per eseguire i test e avviare subito l'applicazione su http://localhost:8080 (utile per la verifica manuale), si possono concatenare i due comandi:

```bash
./mvnw test && ./mvnw spring-boot:run
```

L'applicazione viene avviata solo se tutti i test passano. In alternativa, se si usa il plugin "Spring Boot Dashboard" per VS Code, si può lanciare solo `./mvnw test` da terminale e poi avviare l'applicazione dal pannello, oppure usare il comando combinato sopra. Per i dettagli su come avviare l'applicazione, vedi la sezione [Esecuzione](#esecuzione).