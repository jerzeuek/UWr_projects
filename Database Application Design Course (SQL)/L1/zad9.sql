/*
Adding new column:

ALTER TABLE [SalesLT].[Customer]
ADD CreditCardNumber VARCHAR(16);

Add approval code:

UPDATE TOP (3) [SalesLT].[SalesOrderHeader]
SET CreditCardApprovalCode = 'AnyValue';

Updating card numbers to 'X':

UPDATE c
SET c.CreditCardNumber = 'X'
FROM SalesLT.Customer AS c
WHERE EXISTS (
    SELECT 1
    FROM SalesLT.SalesOrderHeader AS soh
    WHERE soh.CustomerID = c.CustomerID
    AND soh.CreditCardApprovalCode IS NOT NULL
);
*/