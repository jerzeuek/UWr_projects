CREATE TABLE miejsce_pracy(
	id SERIAL PRIMARY KEY,
	nazwa varchar(50) NOT NULL,
	adres varchar(100)
);


CREATE TABLE osoba(
	id SERIAL PRIMARY KEY,
	imie varchar(50),
	nazwisko varchar(50) NOT NULL,
	id_miejsce_pracy int NOT NULL,
	FOREIGN KEY(id_miejsce_pracy)
	REFERENCES miejsce_pracy(id)
);
	