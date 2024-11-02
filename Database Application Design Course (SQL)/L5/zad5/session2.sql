SET TRANSACTION ISOLATION LEVEL SERIALIZABLE;

-- Start another transaction and update the data
BEGIN TRANSACTION
    UPDATE ExampleTable SET Value = 150 WHERE ID = 1;

-- Now, in the third session, execute the same SELECT with NOLOCK