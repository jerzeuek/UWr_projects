-- triggers instead of allow us to modify the query, e.g. insert, in this example they just allow us to add a brand to the brand database
--but at the same time as long as the brand is not accepted it will go to the appropriate table
--but at the same time using view will allow you to see a list of all known brands

DROP TABLE IF EXISTS dbo.brands;
DROP TABLE IF EXISTS dbo.brand_approvals;
DROP TRIGGER IF EXISTS dbo.vw_brands;

CREATE TABLE dbo.brands(
    brand_id INT IDENTITY PRIMARY KEY,
    brand_name VARCHAR(255) NOT NULL
);
GO

CREATE TABLE dbo.brand_approvals(
    brand_id INT IDENTITY PRIMARY KEY,
    brand_name VARCHAR(255) NOT NULL
);
GO

INSERT INTO dbo.brands(brand_name)
VALUES ('test1')
GO

INSERT INTO dbo.brand_approvals(brand_name)
VALUES ('test2')
Go

CREATE VIEW dbo.vw_brands 
AS
SELECT
    brand_name,
    'Approved' approval_status
FROM
    dbo.brands
UNION
SELECT
    brand_name,
    'Pending Approval' approval_status
FROM
    dbo.brand_approvals;
GO

SELECT * FROM dbo.vw_brands
GO

CREATE TRIGGER dbo.trg_vw_brands 
ON dbo.vw_brands
INSTEAD OF INSERT
AS
BEGIN
    SET NOCOUNT ON;
    INSERT INTO dbo.brand_approvals ( 
        brand_name
    )
    SELECT
        i.brand_name
    FROM
        inserted i
    WHERE
        i.brand_name NOT IN (
            SELECT 
                brand_name
            FROM
                dbo.brands
        );
END
GO

INSERT INTO dbo.vw_brands(brand_name)
VALUES('Eddy Merckx');

SELECT
	brand_name,
	approval_status
FROM
	dbo.vw_brands;
GO

select * from dbo.brand_approvals