/*CREATING TABLES AND ADDING TEST DATA

CREATE TABLE firstnames (
    id INT PRIMARY KEY,
    firstname NVARCHAR(255) NOT NULL
);

CREATE TABLE lastnames (
    id INT PRIMARY KEY,
    lastname NVARCHAR(255) NOT NULL
);

CREATE TABLE fldata (
    firstname NVARCHAR(255),
    lastname NVARCHAR(255),
    PRIMARY KEY (firstname, lastname)
);

INSERT INTO firstnames (id, firstname)
VALUES
    (1, N'John'),
    (2, N'Jane'),
    (3, N'Michael'),
    (4, N'Emily'),
    (5, N'William');

INSERT INTO lastnames (id, lastname)
VALUES
    (1, N'Smith'),
    (2, N'Johnson'),
    (3, N'Brown'),
    (4, N'Davis'),
    (5, N'Wilson');
*/

CREATE PROCEDURE InsertRandomPairs
    @n INT
AS
BEGIN
    DECLARE @i INT = 0;
    DECLARE @fname NVARCHAR(255);
    DECLARE @lname NVARCHAR(255);
    DECLARE @total_possible_pairs INT;
    
    SELECT @total_possible_pairs = COUNT(*) FROM firstnames, lastnames;
    
    IF @n > @total_possible_pairs
    BEGIN;
        THROW 50000, 'Error: n is larger than the number of all possible pairs', 1;
    END
    ELSE
    BEGIN
        DELETE FROM fldata;
        
        CREATE TABLE #used_pairs (
            firstname NVARCHAR(255),
            lastname NVARCHAR(255)
        );
        
        WHILE @i < @n
        BEGIN

            SET @fname = (SELECT TOP 1 firstname FROM firstnames ORDER BY NEWID());
            SET @lname = (SELECT TOP 1 lastname FROM lastnames ORDER BY NEWID());
            
            IF NOT EXISTS (SELECT 1 FROM #used_pairs WHERE firstname = @fname AND lastname = @lname)
            BEGIN

                INSERT INTO fldata (firstname, lastname) VALUES (@fname, @lname);
                INSERT INTO #used_pairs (firstname, lastname) VALUES (@fname, @lname);
                SET @i = @i + 1;
            END
        END;
        
        DROP TABLE #used_pairs;
    END
END;