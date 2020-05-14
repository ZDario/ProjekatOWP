DROP INDEX IF EXISTS userName;

DROP TABLE IF EXISTS films;
DROP TABLE IF EXISTS users;

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


CREATE TABLE tipProjekcije (
    idTipProjekcije INTEGER NOT NULL,
    naziv  VARCHAR(20) NOT NULL,
    PRIMARY KEY(idTipProjekcije)
);
INSERT INTO tipProjekcije(naziv)
VALUES('');


CREATE TABLE sala (
    idSala INTEGER NOT NULL,
    naziv  VARCHAR(20) NOT NULL,
    PRIMARY KEY(idSala)
);
INSERT INTO sala(naziv)
VALUES('1a');
INSERT INTO sala(naziv)
VALUES('2a');
INSERT INTO sala(naziv)
VALUES('3a');


CREATE TABLE sediste (
    idSediste INTEGER NOT NULL,
    idSala INTEGER NOT NULL,
    PRIMARY KEY(idSediste),
    FOREIGN KEY (idSala) REFERENCES sala(idSala)
);
INSERT INTO sediste(idSala)
VALUES(1);
INSERT INTO sediste(idSala)
VALUES(1);
INSERT INTO sediste(idSala)
VALUES(1);
INSERT INTO sediste(idSala)
VALUES(2);
INSERT INTO sediste(idSala)
VALUES(2);
INSERT INTO sediste(idSala)
VALUES(2);
INSERT INTO sediste(idSala)
VALUES(3);
INSERT INTO sediste(idSala)
VALUES(3);


CREATE TABLE projekcija (
    idProjekcija INTEGER NOT NULL,
    idFilm INTEGER NOT NULL,
    idTipProjekcije INTEGER NOT NULL,
    idSala INTEGER NOT NULL,
    datumPrikazivanja DATE NOT NULL,
    cena DOUBLE NOT NULL,
    administratorKorIme VARCHAR(10) NOT NULL DEFAULT 'a',
    PRIMARY KEY(idProjekcija)
    
    FOREIGN KEY (idFilm) REFERENCES films(idFilm),
    FOREIGN KEY (idTipProjekcije) REFERENCES tipProjekcije(idTipProjekcije),
    FOREIGN KEY (idSala) REFERENCES sala(idSala),
    FOREIGN KEY (administratorKorIme) REFERENCES users(userName)
);
INSERT INTO projekcija(idFilm,idTipProjekcije,idSala,datumPrikazivanja,cena,administratorKorIme)
VALUES(1,1,1,'2020-06-22',300,'a');
INSERT INTO projekcija(idFilm,idTipProjekcije,idSala,datumPrikazivanja,cena,administratorKorIme)
VALUES(2,1,2,'2020-07-25',400,'a');
INSERT INTO projekcija(idFilm,idTipProjekcije,idSala,datumPrikazivanja,cena,administratorKorIme)
VALUES(3,1,3,'2020-07-04',400,'a');
INSERT INTO projekcija(idFilm,idTipProjekcije,idSala,datumPrikazivanja,cena,administratorKorIme)
VALUES(1,2,2,'2020-06-27',500,'a');
INSERT INTO projekcija(idFilm,idTipProjekcije,idSala,datumPrikazivanja,cena,administratorKorIme)
VALUES(2,2,2,'2020-08-15',550,'a');


CREATE TABLE karta (
    idKarta INTEGER NOT NULL,
    idProjekcija INTEGER NOT NULL,
    idSediste INTEGER NOT NULL,
    vremeProdaje DATE NOT NULL,
    korisnikKorIme VARCHAR(10) NOT NULL,
    PRIMARY KEY(idKarta),
    
    FOREIGN KEY (idProjekcija) REFERENCES projekcija(idProjekcija),
    FOREIGN KEY (idSediste) REFERENCES sediste(idSediste),
    FOREIGN KEY (korisnikKorIme) REFERENCES users(userName)
);
INSERT INTO karta(idProjekcija,idSediste,vremeProdaje,korisnikKorIme)
VALUES(1,1,'2020-06-14','b');
INSERT INTO projekcija(idProjekcija,idSediste,vremeProdaje,korisnikKorIme)
VALUES(2,2,'2020-07-22','b');
INSERT INTO projekcija(idProjekcija,idSediste,vremeProdaje,korisnikKorIme)
VALUES(3,3,'2020-06-28','ab');