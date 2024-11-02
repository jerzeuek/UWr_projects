CREATE TABLE osoba (
	id SERIAL PRIMARY KEY,
	imie varchar(50) NOT NULL,
	nazwisko varchar(50)
);

CREATE TABLE miejsce_pracy (
	id SERIAL PRIMARY KEY,
	nazwa varchar(100) NOT NULL,
	adres varchar(100)
);

CREATE TABLE osoba_pracodawca (
	id SERIAL PRIMARY KEY,
	osoba_id int NOT NULL,
	miejsce_pracy_id int NOT NULL,
  	CONSTRAINT fk_osoba FOREIGN KEY(osoba_id) REFERENCES osoba(id),
  	CONSTRAINT fk_pracodawca FOREIGN KEY(miejsce_pracy_id) REFERENCES miejsce_pracy(id)
);
	