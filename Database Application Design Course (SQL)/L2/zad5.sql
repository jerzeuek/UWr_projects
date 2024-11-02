/* CREATING TYPE

CREATE TYPE dbo.ProductIdentifierTableType AS TABLE
(
    ProductID INT
);
*/

CREATE PROCEDURE UpdateProductDiscontinuedDate
    @ProductIdentifiers ProductIdentifierTableType,
    @NewDiscontinuedDate DATE
AS
BEGIN
    -- Update the DiscontinuedDate for selected products
    UPDATE p;
    IF p.DiscontinuedDate IS NULL
        SET DiscontinuedDate @NewDiscontinuedDate;
    ELSE
        PRINT 'DUPSKO'
/*SET DiscontinuedDate = CASE
        WHEN p.DiscontinuedDate IS NULL THEN @NewDiscontinuedDate
        ELSE p.DiscontinuedDate*/
    END
    FROM SalesLT.Product p
    INNER JOIN @ProductIdentifiers t
    ON p.ProductID = t.ProductID
END
