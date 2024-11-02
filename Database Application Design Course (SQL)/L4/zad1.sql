CREATE TRIGGER modifyCustomers
ON SalesLT.Customer
AFTER UPDATE
AS
BEGIN
    SET NOCOUNT ON;

    -- Update ModifiedDate to current date and time
    UPDATE SalesLT.Customer
    SET ModifiedDate = GETDATE()
    FROM inserted
    WHERE SalesLT.Customer.CustomerID = inserted.CustomerID;
END;

ENABLE TRIGGER modifyCustomers ON SalesLT.Customer;

UPDATE SalesLT.Customer
SET FirstName = 'Rosmariess'
WHERE CustomerID = 30
GO

SELECT * FROM SalesLT.Customer WHERE CustomerID = 30;

DROP TRIGGER SalesLT.modifyCustomers

