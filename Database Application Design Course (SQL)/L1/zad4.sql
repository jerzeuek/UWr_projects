WITH LeafCategories AS (
    SELECT DISTINCT C1.ProductCategoryID
    FROM [SalesLT].[ProductCategory] AS C1
    WHERE NOT EXISTS (
        SELECT 1
        FROM [SalesLT].[ProductCategory] AS C2
        WHERE C2.ParentProductCategoryID = C1.ProductCategoryID
    )
)

SELECT C.Name, P.Name
FROM [SalesLT].[ProductCategory] AS C
JOIN [SalesLT].[Product] AS P ON C.ProductCategoryID = P.ProductCategoryID
WHERE C.ProductCategoryID NOT IN (SELECT ProductCategoryID FROM LeafCategories);


/*
CREATING TEST DATA:

-- Create a Categories table with a CategoryID and ParentCategoryID to represent the hierarchy.
CREATE TABLE ProductCategoryTest (
    ProductCategoryID INT PRIMARY KEY,
    ParentProductCategoryID INT,
    Name VARCHAR(50)
);

CREATE TABLE ProductTest (
    ProductID INT PRIMARY KEY,
    ProductCategoryID INT,
	Name VARCHAR(100)
);

INSERT INTO ProductCategoryTest (ProductCategoryID, ParentProductCategoryID, Name)
VALUES
    (1, NULL, 'Electronics'),
    (2, 1, 'Computers'),
    (3, 2, 'Laptops'),
    (4, 2, 'Desktops'),
    (5, NULL, 'Appliances'),
    (6, 5, 'Refrigerators');

INSERT INTO ProductTest (ProductID, ProductCategoryID, Name)
VALUES
    (101, 3, 'High-end Laptop'),
    (102, 4, 'Gaming Desktop'),
    (103, 6, 'Smart Refrigerator'),
    (104, 1, 'Tablet'),
	(105, 2, 'Office Laptop');
	*/

/*
QUERY ON TEST DATA:  
WITH LeafCategories AS (
    SELECT DISTINCT C1.ProductCategoryID
    FROM [dbo].[ProductCategoryTest] AS C1
    WHERE NOT EXISTS (
        SELECT 1
        FROM [dbo].[ProductCategoryTest] AS C2
        WHERE C2.ParentProductCategoryID = C1.ProductCategoryID
    )
)

SELECT C.Name, P.Name
FROM [dbo].[ProductCategoryTest] AS C
JOIN [dbo].[ProductTest] AS P ON C.ProductCategoryID = P.ProductCategoryID
WHERE C.ProductCategoryID NOT IN (SELECT ProductCategoryID FROM LeafCategories);
*/