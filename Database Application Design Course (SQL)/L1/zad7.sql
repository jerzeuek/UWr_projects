/*
Creating the table:

CREATE TABLE Test
(
    ID INT IDENTITY(1000, 10),
    Data VARCHAR(255)
);
*/

--Inserting sample data:

INSERT INTO Test (Data) VALUES ('Sample Data');

--@@IDENTITY - Last identity value that was generated in any table within the current session.
SELECT @@IDENTITY AS [@@IDENTITY];

--IDENT_CURRENT - Last identity value generated for the table.
SELECT IDENT_CURRENT('Test') AS [IDENT_CURRENT];