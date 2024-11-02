SET STATISTICS TIME ON;

SELECT c.PESEL, c.Nazwisko
FROM Czytelnik c WHERE c.Czytelnik_ID IN
(SELECT w.Czytelnik_ID FROM Wypozyczenie w 
where w.Egzemplarz_ID in (select e.Egzemplarz_ID from Egzemplarz e
JOIN Ksiazka k ON e.Ksiazka_ID=k.Ksiazka_ID))