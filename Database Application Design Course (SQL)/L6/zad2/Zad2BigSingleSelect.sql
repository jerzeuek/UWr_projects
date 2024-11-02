SET STATISTICS TIME ON;


SELECT c.PESEL, c.Nazwisko
FROM Czytelnik_big c WHERE c.Czytelnik_ID IN
(SELECT w.Czytelnik_ID FROM Wypozyczenie_big w
JOIN Egzemplarz_big e ON e.Egzemplarz_ID=w.Egzemplarz_ID
JOIN Ksiazka_big k ON e.Ksiazka_ID=k.Ksiazka_ID)