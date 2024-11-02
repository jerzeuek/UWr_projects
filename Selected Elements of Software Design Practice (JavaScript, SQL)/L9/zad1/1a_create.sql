CREATE TABLE osoba(
	id int NOT NULL,
	imie varchar(50),
	nazwisko varchar(50) NOT NULL,
	miasto varchar(50),
	plec char(1),
	wiek int
);

CREATE SEQUENCE klucze START 1;