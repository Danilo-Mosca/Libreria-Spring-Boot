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

INSERT INTO books (title, author, year_of_publication, pages, genre, available, description) VALUES
('Clean Code', 'Robert C. Martin', 2008, 464, 'TECH', true, 'Una guida pratica per scrivere codice pulito, leggibile e manutenibile.'),
('The Pragmatic Programmer', 'Andrew Hunt', 1999, 352, 'TECH', true, 'Consigli e best practice per diventare sviluppatori software migliori.'),
('Spring in Action', 'Craig Walls', 2022, 520, 'TECH', true, 'Manuale completo sul framework Spring per applicazioni Java moderne.'),
('Effective Java', 'Joshua Bloch', 2018, 416, 'TECH', true, 'Raccolta di best practice fondamentali per programmare in Java.'),
('Design Patterns', 'Erich Gamma', 1994, 395, 'TECH', true, 'Libro storico sui principali design pattern orientati agli oggetti.'),

('1984', 'George Orwell', 1949, 328, 'FICTION', true, 'Romanzo distopico su un regime totalitario e il controllo assoluto.'),
('To Kill a Mockingbird', 'Harper Lee', 1960, 281, 'FICTION', true, 'Una storia intensa su giustizia, razzismo e crescita personale.'),
('The Great Gatsby', 'F. Scott Fitzgerald', 1925, 180, 'FICTION', true, 'Ritratto dell’alta società americana durante gli anni ruggenti.'),
('The Catcher in the Rye', 'J.D. Salinger', 1951, 277, 'FICTION', true, 'Il viaggio interiore di un adolescente ribelle e disilluso.'),
('The Alchemist', 'Paulo Coelho', 1988, 208, 'FICTION', true, 'Favola filosofica sulla ricerca dei propri sogni e del destino.'),

('Sapiens', 'Yuval Noah Harari', 2011, 443, 'HISTORY', true, 'Analisi dell’evoluzione e della storia dell’umanità.'),
('Guns, Germs, and Steel', 'Jared Diamond', 1997, 480, 'HISTORY', true, 'Studio sui fattori geografici e sociali che hanno plasmato le civiltà.'),
('The Silk Roads', 'Peter Frankopan', 2015, 636, 'HISTORY', true, 'Una nuova prospettiva sulla storia mondiale attraverso le vie della seta.'),
('SPQR', 'Mary Beard', 2015, 608, 'HISTORY', true, 'Racconto dettagliato della storia dell’antica Roma.'),
('The Wright Brothers', 'David McCullough', 2015, 336, 'HISTORY', true, 'Biografia dei pionieri che rivoluzionarono il volo umano.'),

('Pride and Prejudice', 'Jane Austen', 1813, 279, 'ROMANCE', true, 'Classico romantico tra amore, orgoglio e differenze sociali.'),
('Me Before You', 'Jojo Moyes', 2012, 369, 'ROMANCE', true, 'Storia emozionante di amore e cambiamento personale.'),
('The Notebook', 'Nicholas Sparks', 1996, 214, 'ROMANCE', true, 'Romanzo romantico su un amore che resiste al tempo.'),
('Outlander', 'Diana Gabaldon', 1991, 850, 'ROMANCE', true, 'Avventura romantica tra viaggi nel tempo e Scozia storica.'),
('Twilight', 'Stephenie Meyer', 2005, 498, 'ROMANCE', true, 'Storia d’amore adolescenziale tra umani e vampiri.'),

('The Da Vinci Code', 'Dan Brown', 2003, 489, 'THRILLER', true, 'Thriller investigativo tra misteri religiosi e società segrete.'),
('Gone Girl', 'Gillian Flynn', 2012, 432, 'THRILLER', true, 'Thriller psicologico su un matrimonio pieno di segreti.'),
('The Girl with the Dragon Tattoo', 'Stieg Larsson', 2005, 465, 'THRILLER', true, 'Indagine oscura che intreccia misteri familiari e criminalità.'),
('Angels and Demons', 'Dan Brown', 2000, 616, 'THRILLER', true, 'Avventura ricca di enigmi tra scienza, religione e complotti.'),
('Shutter Island', 'Dennis Lehane', 2003, 369, 'THRILLER', true, 'Thriller psicologico ambientato in un inquietante ospedale psichiatrico.'),

('The Hobbit', 'J.R.R. Tolkien', 1937, 310, 'FANTASY', true, 'Viaggio epico di Bilbo Baggins nella Terra di Mezzo.'),
('Harry Potter and the Sorcerer''s Stone', 'J.K. Rowling', 1997, 309, 'FANTASY', true, 'L’inizio delle avventure del giovane mago Harry Potter.'),
('A Game of Thrones', 'George R.R. Martin', 1996, 694, 'FANTASY', true, 'Intrighi politici e battaglie per il trono in un mondo fantasy.'),
('The Name of the Wind', 'Patrick Rothfuss', 2007, 662, 'FANTASY', true, 'La storia leggendaria del mago e musicista Kvothe.'),
('Steve Jobs', 'Walter Isaacson', 2011, 656, 'BIOGRAPHY', true, 'Biografia autorizzata del fondatore di Apple Steve Jobs.');