-- =========================
-- USERS
-- =========================
INSERT INTO users (name, last_name) VALUES
('Mario', 'Rossi'),
('Luigi', 'Bianchi'),
('Anna', 'Verdi'),
('Giulia', 'Neri'),
('Marco', 'Gialli');

-- =========================
-- BOOKS
-- =========================

INSERT INTO books (title, author, year_of_publication, pages, genre, available, number_of_copies, description) VALUES
('Clean Code', 'Robert C. Martin', 2008, 464, 'TECH', true, 10, 'Una guida pratica per scrivere codice pulito, leggibile e manutenibile.'),
('The Pragmatic Programmer', 'Andrew Hunt', 1999, 352, 'TECH', true, 5, 'Consigli e best practice per diventare sviluppatori software migliori.'),
('Spring in Action', 'Craig Walls', 2022, 520, 'TECH', true, 1, 'Manuale completo sul framework Spring per applicazioni Java moderne.'),
('Effective Java', 'Joshua Bloch', 2018, 416, 'TECH', true, 8, 'Raccolta di best practice fondamentali per programmare in Java.'),
('Design Patterns', 'Erich Gamma', 1994, 395, 'TECH', true, 0, 'Libro storico sui principali design pattern orientati agli oggetti.'),

('1984', 'George Orwell', 1949, 328, 'FICTION', true, 40, 'Romanzo distopico su un regime totalitario e il controllo assoluto.'),
('To Kill a Mockingbird', 'Harper Lee', 1960, 281, 'FICTION', true, 3, 'Una storia intensa su giustizia, razzismo e crescita personale.'),
('The Great Gatsby', 'F. Scott Fitzgerald', 1925, 180, 'FICTION', true, 15, 'Ritratto dell’alta società americana durante gli anni ruggenti.'),
('The Catcher in the Rye', 'J.D. Salinger', 1951, 277, 'FICTION', true, 0, 'Il viaggio interiore di un adolescente ribelle e disilluso.'),
('The Alchemist', 'Paulo Coelho', 1988, 208, 'FICTION', true, 7, 'Favola filosofica sulla ricerca dei propri sogni e del destino.'),

('Sapiens', 'Yuval Noah Harari', 2011, 443, 'HISTORY', true, 7, 'Analisi dell’evoluzione e della storia dell’umanità.'),
('Guns, Germs, and Steel', 'Jared Diamond', 1997, 480, 'HISTORY', true, 8, 'Studio sui fattori geografici e sociali che hanno plasmato le civiltà.'),
('The Silk Roads', 'Peter Frankopan', 2015, 636, 'HISTORY', true, 18, 'Una nuova prospettiva sulla storia mondiale attraverso le vie della seta.'),
('SPQR', 'Mary Beard', 2015, 608, 'HISTORY', true, 101, 'Racconto dettagliato della storia dell’antica Roma.'),
('The Wright Brothers', 'David McCullough', 2015, 336, 'HISTORY', true, 50, 'Biografia dei pionieri che rivoluzionarono il volo umano.'),

('Pride and Prejudice', 'Jane Austen', 1813, 279, 'ROMANCE', true, 10, 'Classico romantico tra amore, orgoglio e differenze sociali.'),
('Me Before You', 'Jojo Moyes', 2012, 369, 'ROMANCE', true, 20, 'Storia emozionante di amore e cambiamento personale.'),
('The Notebook', 'Nicholas Sparks', 1996, 214, 'ROMANCE', true, 0, 'Romanzo romantico su un amore che resiste al tempo.'),
('Outlander', 'Diana Gabaldon', 1991, 850, 'ROMANCE', true, 5, 'Avventura romantica tra viaggi nel tempo e Scozia storica.'),
('Twilight', 'Stephenie Meyer', 2005, 498, 'ROMANCE', true, 51, 'Storia d’amore adolescenziale tra umani e vampiri.'),

('The Da Vinci Code', 'Dan Brown', 2003, 489, 'THRILLER', true, 12, 'Thriller investigativo tra misteri religiosi e società segrete.'),
('Gone Girl', 'Gillian Flynn', 2012, 432, 'THRILLER', true, 5, 'Thriller psicologico su un matrimonio pieno di segreti.'),
('The Girl with the Dragon Tattoo', 'Stieg Larsson', 2005, 465, 'THRILLER', true, 43, 'Indagine oscura che intreccia misteri familiari e criminalità.'),
('Angels and Demons', 'Dan Brown', 2000, 616, 'THRILLER', true, 0, 'Avventura ricca di enigmi tra scienza, religione e complotti.'),
('Shutter Island', 'Dennis Lehane', 2003, 369, 'THRILLER', true, 1, 'Thriller psicologico ambientato in un inquietante ospedale psichiatrico.'),

('The Hobbit', 'J.R.R. Tolkien', 1937, 310, 'FANTASY', true, 2, 'Viaggio epico di Bilbo Baggins nella Terra di Mezzo.'),
('Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', 1997, 309, 'FANTASY', true, 5, 'L’inizio delle avventure del giovane mago Harry Potter.'),
('A Game of Thrones', 'George R.R. Martin', 1996, 694, 'FANTASY', true, 7, 'Intrighi politici e battaglie per il trono in un mondo fantasy.'),
('The Name of the Wind', 'Patrick Rothfuss', 2007, 662, 'FANTASY', true, 8, 'La storia leggendaria del mago e musicista Kvothe.'),
('Steve Jobs', 'Walter Isaacson', 2011, 656, 'BIOGRAPHY', true, 9, 'Biografia autorizzata del fondatore di Apple Steve Jobs.');