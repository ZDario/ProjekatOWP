DROP INDEX IF EXISTS userName;

DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS films;

CREATE TABLE users (
    userName VARCHAR(10) NOT NULL, 
    password VARCHAR(15) NOT NULL,
    dateOfRegistration DATE NOT NULL, 
    role VARCHAR(5) NOT NULL DEFAULT 'USER', -- SQLite ne poznaje enum-e
    PRIMARY KEY(userName)
);
INSERT INTO users (userName, password, dateOfRegistration, role) VALUES ('a', 'a','2020-01-12', 'ADMIN');
INSERT INTO users (userName, password, dateOfRegistration, role) VALUES ('b', 'b','2020-01-15', 'USER');
INSERT INTO users (userName, password, dateOfRegistration, role) VALUES ('ab', 'ab','2020-01-18', 'USER');


CREATE TABLE films (
    idFilm    INTEGER PRIMARY KEY,
    naziv    VARCHAR(20) NOT NULL,
    reziser    VARCHAR(20) ,
    glumci    VARCHAR(80) ,
    zanrovi    VARCHAR(30) NOT NULL,
    trajanje INTEGER NOT NULL DEFAULT 9999,
    distributer    VARCHAR(20) NOT NULL,
    zemljaPorekla    VARCHAR(20) NOT NULL,
    godinaProizvodnje  INTEGER NOT NULL DEFAULT 9999,  
    opis    VARCHAR(150)
);
INSERT INTO films(naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godinaProizvodnje,opis) 
VALUES ('Aquaman','James Wan','Jason Momoa, Amber Heard, Willem Dafoe','Akcija/Fantazija',120,'CBS Films','United States',2018,'Legitimni kralj se vraca na svoj presto u Atlantidi ali tu ga cekaju mnoge opasnosti.');
INSERT INTO films(naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godinaProizvodnje,opis) 
VALUES ('Jumanji','Jake Kasdan','Dwayne Johnson, Jack Black, Kevin Hart, Karen Gillan, Nick Jonas','Akcija/Komedija',119,'Columbia Pictures','United States',2017,'Avanture tinejdzera koji su postali zarobljeni u igri Jumanji');
INSERT INTO films(naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godinaProizvodnje,opis) 
VALUES ('The Revenant','Alejandro G. Injarritu','Leonardo DiCaprio, Tom Hardy, Will Poulter ','Istorija/Drama',156,'20th Century Fox','United States',2015,'Polaznik ekspedicije za trgovinu krznom 1820-tih, bori se za opstanak nakon borbe sa medvedom i ostavljen da umre od svojih clanova vlastite lovcke ekipe.');