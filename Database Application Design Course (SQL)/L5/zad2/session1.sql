SET TRANSACTION ISOLATION LEVEL READ UNCOMMITTED
-- SET TRANSACTION ISOLATION LEVEL READ COMMITTED;
-- SET TRANSACTION ISOLATION LEVEL REPEATABLE READ;
-- SET TRANSACTION ISOLATION LEVEL SNAPSHOT;
-- SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;


DROP TABLE IF EXISTS Test_zad1
GO
    
CREATE TABLE Test_zad1
(
    ID INT PRIMARY KEY,
    Book VARCHAR(100), 
    Quantity INT
);
GO

INSERT INTO Test_zad1(ID, Book, Quantity)
VALUES (1, 'Harry Potter and the Philosopher Stone', 3),
       (2, 'Da Vinci Code', 5),
       (3, 'Harry Potter and the Chamber of Secrets', 2),
       (4, 'The Hunger Games', 3),
       (5, 'Labyrinth', 9),
       (6, 'Lord of the Rings', 1),
       (7, 'The Witcher', 5)


SELECT * FROM Test_zad1

-- Dirty read:

BEGIN TRANSACTION
UPDATE Test_zad1
SET Quantity = 100 WHERE ID = 3
WAITFOR DELAY '00:00:25'
ROLLBACK TRANSACTION

-- Non-repeatable read:

BEGIN TRANSACTION
UPDATE 
    Test_zad1
SET 
    Quantity = 100
WHERE 
    ID = 1
COMMIT

-- Phantom read:

BEGIN TRANSACTION
INSERT INTO Test_zad1(ID, Book, Quantity)
VALUES (9, 'The Hobbit', 123)
COMMIT
     
