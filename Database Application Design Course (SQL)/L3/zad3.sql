DROP TABLE IF EXISTS dbo.Prices;
DROP TABLE IF EXISTS dbo.Rates;
DROP TABLE IF EXISTS dbo.Products;

-- Adding tables and data:
CREATE TABLE dbo.Products (
	ID INT PRIMARY KEY IDENTITY,
	ProductName VARCHAR(50)
)
CREATE TABLE dbo.Rates (
	Currency VARCHAR(5) PRIMARY KEY,
	PricePLN FLOAT
)

CREATE TABLE dbo.Prices (
	ProductID INT FOREIGN KEY REFERENCES dbo.Products(ID),
	Currency VARCHAR(5),
	Price FLOAT
)

INSERT INTO dbo.Products VALUES ('p1'), ('p2'), ('p3')
INSERT INTO dbo.Rates VALUES ('PLN', 1.0), ('GBP', 5.51), ('EUR', 4.52), ('USD', 4.34)
INSERT INTO dbo.Prices VALUES (1, 'PLN', 220.55), (2, 'GBP', 2), (3, 'EUR', 10)

DELETE FROM dbo.Rates WHERE Currency = 'EUR'


-- Cursor, we take row by row from Prices
-- If record has currency other than PLN and a currency rate exists, then we update price and change the currency
-- If a currency rate don't exist, then we delete a record from Prices

DECLARE c CURSOR FOR SELECT * FROM dbo.Prices
OPEN c
DECLARE @pid INT
DECLARE @curr VARCHAR(5)
DECLARE @price FLOAT

FETCH NEXT FROM c INTO @pid, @curr, @price
WHILE ( @@fetch_status=0 )
BEGIN
	IF @curr <> 'PLN'
		BEGIN
			IF EXISTS(SELECT * FROM dbo.Rates WHERE Currency = @curr)
				UPDATE dbo.Prices SET Currency = 'PLN', Price = @price * (SELECT PricePLN FROM dbo.Rates WHERE Currency = @curr) WHERE Currency = @curr
			ELSE
				DELETE FROM dbo.Prices WHERE ProductID = @pid
		END

	FETCH NEXT FROM c INTO @pid, @curr, @price
END
PRINT 'Last fetch instruction status: ' + CAST(@@fetch_status AS VARCHAR)
CLOSE c
DEALLOCATE c

SELECT * FROM Prices
SELECT * FROM Products
SELECT * FROM Rates


