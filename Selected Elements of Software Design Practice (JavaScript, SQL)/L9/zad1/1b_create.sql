CREATE TABLE osoba(
	id SERIAL PRIMARY KEY,
	imie varchar(50),
	nazwisko varchar(50) NOT NULL,
	miasto varchar(50),
	plec char(1),
	wiek int
);