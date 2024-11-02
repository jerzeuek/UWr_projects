SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;

-- Create a sample table
CREATE TABLE ExampleTable (
    ID INT PRIMARY KEY,
    Value INT
);

-- Insert some data
INSERT INTO ExampleTable (ID, Value) VALUES (1, 100), (2, 200);

-- Start a transaction with serializable isolation level
BEGIN TRANSACTION
    SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;

-- Execute a SELECT without NOLOCK
SELECT * FROM ExampleTable WHERE ID = 1;