CREATE TABLE osoba (
	id SERIAL PRIMARY KEY,
	imie varchar(50) NOT NULL,
	nazwisko varchar(50),
	id_miejsce_pracy int NOT NULL
);

CREATE TABLE miejsce_pracy (
	id SERIAL PRIMARY KEY,
	nazwa varchar(100) NOT NULL,
	adres varchar(100)
);

ALTER TABLE osoba
ADD CONSTRAINT fk_osoba_pracodawca
FOREIGN KEY(id_miejsce_pracy)
REFERENCES miejsce_pracy (id)