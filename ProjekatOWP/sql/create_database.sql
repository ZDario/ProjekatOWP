CREATE TABLE users (
    userName VARCHAR(10) NOT NULL, 
    password VARCHAR(15) NOT NULL, 
    role VARCHAR(5) NOT NULL DEFAULT 'USER', -- SQLite ne poznaje enum-e
    PRIMARY KEY(userName)
);
INSERT INTO users (userName, password, role) VALUES ('a', 'a', 'ADMIN');
INSERT INTO users (userName, password, role) VALUES ('b', 'b', 'USER');


CREATE TABLE films (
    idFilm    INTEGER PRIMARY KEY,
    naziv    VARCHAR(20) NOT NULL,
    reziser    VARCHAR(20) ,
    glumci    VARCHAR(80) ,
    zanrovi    VARCHAR(30) NOT NULL,
    trajanje INTEGER NOT NULL,
    distributer    VARCHAR(20) NOT NULL,
    zemljaPorekla    VARCHAR(20) NOT NULL,
    godinaProizvodnje  INTEGER NOT NULL,  
    opis    VARCHAR(150)
);
INSERT INTO films(idFilm,naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godinaProizvodnje,opis) 
VALUES (1,'Aquaman','James Wan','Jason Momoa, Amber Heard, Willem Dafoe','Akcija/Fantazija',120,'CBS Films','Amerika',2018,'Legitimni kralj se vraca na svoj presto u Atlantidi ali tu ga cekaju mnoge opasnosti.');
INSERT INTO films(idFilm,naziv,reziser,glumci,zanrovi,trajanje,distributer,zemljaPorekla,godinaProizvodnje,opis) 
VALUES (2,'Jumanji','Jake Kasdan','Dwayne Johnson, Jack Black, Kevin Hart, Karen Gillan, Nick Jonas','Akcija/Komedija',119,'Columbia Pictures','Amerika',2017,'Avanture tinejdzera koji su postali zarobljeni u igri Jumanji');