SET STATISTICS TIME ON;

SELECT c.PESEL, c.Nazwisko
FROM Czytelnik_big c WHERE c.Czytelnik_ID IN
(SELECT w.Czytelnik_ID FROM Wypozyczenie_big w 
where w.Egzemplarz_ID in (select e.Egzemplarz_ID from Egzemplarz_big e
JOIN Ksiazka_big k ON e.Ksiazka_ID=k.Ksiazka_ID))