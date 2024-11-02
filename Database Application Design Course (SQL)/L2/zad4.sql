/*CREATING TYPE
 
CREATE TYPE dbo.ReaderIDTableType AS TABLE (
    ReaderID INT
);
*/

CREATE PROCEDURE CalculateReaderTotalDaysBorrowed
    @ReaderIDs dbo.ReaderIDTableType READONLY
AS
BEGIN
    CREATE TABLE #ReaderTotalDaysBorrowed (
        Reader_ID INT,
        Sum_Days INT
    );

    INSERT INTO #ReaderTotalDaysBorrowed (Reader_ID, Sum_Days)
    SELECT
        wypoz.Czytelnik_ID AS Reader_ID,
        SUM(wypoz.Liczba_Dni) AS Sum_Days
    FROM
        Wypozyczenie AS wypoz
    WHERE
        wypoz.Czytelnik_ID IN (SELECT ReaderID FROM @ReaderIDs)
    GROUP BY
        wypoz.Czytelnik_ID;

    SELECT * FROM #ReaderTotalDaysBorrowed;

    DROP TABLE #ReaderTotalDaysBorrowed;
END;