#Ksawery Plis
#Lista 11 Zadanie 2

import argparse
from sqlalchemy.orm import declarative_base, relationship, validates, sessionmaker
from sqlalchemy import *

Base = declarative_base()
engine = create_engine("sqlite:///table.db", echo=False)
Session = sessionmaker(bind=engine)
sesja = Session()

#############################################

# deklaracje tabel:


class Film(Base):
    __tablename__ = "Filmy"
    id = Column(Integer, primary_key=True)
    tytul = Column(String(50), nullable=False)
    rok_powstania = Column(Integer)
    tworcy = relationship("Twórca")
    obsada = relationship("Aktor")

    @validates('rok_powstania')
    def validate_name(self, key, value):
        assert value >= 1895 and value <= 2023
        return value


class Twórca(Base):
    __tablename__ = "Twórcy"
    id = Column(Integer, primary_key=True)
    rezyser = Column(String(50), nullable=False)
    operator = Column(String(50))
    producent = Column(String(50))
    film = Column(Integer, ForeignKey("Filmy.id"))

    @validates('reżyser')
    def validate_director(self, key, value):
        assert value != ''
        return value


class Aktor(Base):
    __tablename__ = "Aktorzy"
    id = Column(Integer, primary_key=True)
    imie = Column(String(30))
    nazwisko = Column(String(30), nullable=False)
    email = Column(String(25))
    film = Column(Integer, ForeignKey("Filmy.id"))

    @validates("email")
    def validate_email(self, key, value):
        assert '@' in value
        return value


Base.metadata.create_all(engine)

#############################################

# deklaracje parserów:


parser = argparse.ArgumentParser(description='Zarządzanie bazą danych')
parser.add_argument('tabela', help="Wybierz tabelę tabeli", choices=['Film', 'Twórca', 'Aktor'])
parser.add_argument('--wypisz', help='Wypisz nazwy kolumn tabeli', action="store_true")
parser.add_argument('--usun', help="Usuń wiersz o danym id", type=int)
parser.add_argument('--dodaj', help="Dodaj wiersz z dowolną ilością argumentów", action='store_true')

parser.add_argument('--tytul', type=str, help="Podaj tytuł filmu")
parser.add_argument('--rok_powstania', type=int, help="Podaj rok powstania filmu")
parser.add_argument('--rezyser', type=str, help="Podaj reżysera filmu")
parser.add_argument('--operator', type=str, help="Podaj operatora filmu")
parser.add_argument('--producent', type=str, help="Podaj producenta filmu")
parser.add_argument('--film', type=int, help="Podaj id filmu, z którym związani są twórcy (tabela Twórca) lub aktor (tabela Aktor)")
parser.add_argument('--imie', type=str, help="Podaj imię aktora")
parser.add_argument('--nazwisko', type=str, help="Podaj nazwisko aktora")
parser.add_argument('--email', type=str, help="Podaj adres e-mail aktora")

args = parser.parse_args()

if (args.wypisz):
    columns = eval(args.tabela).metadata.tables[eval(args.tabela).__tablename__].c.keys()
    rows = sesja.query(eval(args.tabela)).all()

    print(f"Tabela {args.tabela}: \n")

    for column in columns:
        print("{:<50}".format(column), end=" ")
    print()

    for row in rows:
        if (args.tabela == 'Film'):
            print("{:<50} {:<50} {:<50}".format(str(row.id), str(row.tytul), str(row.rok_powstania)))
        elif (args.tabela == 'Twórca'):
            print("{:<50} {:<50} {:<50} {:<50} {:<50}".format(str(row.id), str(row.rezyser), str(row.operator), str(row.producent), str(row.film)))
        else:
            print("{:<50} {:<50} {:<50} {:<50} {:<50}".format(str(row.id), str(row.imie), str(row.nazwisko), str(row.email), str(row.film)))

if (args.usun):
    sesja.query(eval(args.tabela)).filter_by(id=args.usun).delete()
    sesja.commit()
    print(f"Usunięto wiersz o id = {args.usun} z tabeli {args.tabela}!")

if (args.dodaj):
    if (args.tabela == "Film"):
        new = Film()
        if (args.tytul): new.tytul = args.tytul
        if (args.rok_powstania): new.rok_powstania = args.rok_powstania

    elif (args.tabela == "Twórca"):
        new = Twórca()
        if (args.rezyser): new.rezyser = args.rezyser
        if (args.operator): new.operator = args.operator
        if (args.producent): new.producent = args.producent
        if (args.film): new.film = args.film

    else:
        new = Aktor()
        if (args.imie): new.imie = args.imie
        if (args.nazwisko): new.nazwisko = args.nazwisko
        if (args.email): new.email = args.email
        if (args.film): new.film = args.film

    sesja.add(new)
    sesja.commit()
    print(f"Dodano rząd do tabeli {args.tabela}!")

sesja.close()