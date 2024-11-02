SELECT 
	mod.Name, 
	COUNT(*)
FROM 
	[SalesLT].[ProductModel] AS mod
	INNER JOIN [SalesLT].[Product] AS prod
	ON mod.ProductModelID = prod.ProductModelID 
GROUP BY mod.Name 
HAVING COUNT(*) > 1
