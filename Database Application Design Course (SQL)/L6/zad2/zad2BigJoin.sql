SET STATISTICS TIME ON;


SELECT DISTINCT c.PESEL, c.Nazwisko
FROM Egzemplarz_big e
JOIN Ksiazka_big k ON e.Ksiazka_ID=k.Ksiazka_ID
JOIN Wypozyczenie_big w ON e.Egzemplarz_ID=w.Egzemplarz_ID
JOIN Czytelnik_big c ON c.Czytelnik_ID = w.Czytelnik_ID