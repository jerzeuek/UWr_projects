CREATE FUNCTION GetReadersWithSpecimens(@minDays int)
RETURNS @ResultTable TABLE (PESEL varchar(11), SpecimensCount int)
AS
BEGIN
    INSERT INTO @ResultTable (PESEL, SpecimensCount)
    SELECT
        czyt.PESEL,
        COUNT(wypoz.Egzemplarz_ID) AS SpecimensCount
    FROM
        Czytelnik AS czyt
    JOIN Wypozyczenie AS wypoz ON czyt.Czytelnik_ID = wypoz.Czytelnik_ID
    WHERE
        wypoz.Liczba_Dni >= @minDays
    GROUP BY
        czyt.PESEL;
    
    RETURN;
END;