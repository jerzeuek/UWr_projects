SELECT
	cust.LastName,
	cust.FirstName,
	SUM(sod.OrderQty * sod.UnitPrice) - SUM(sod.LineTotal) AS TotalAmountSaved
FROM
	[SalesLT].[Customer] AS cust
	JOIN [SalesLT].[SalesOrderHeader] AS soh
	ON cust.CustomerID = soh.CustomerID
	JOIN [SalesLT].[SalesOrderDetail] AS sod 
	ON soh.SalesOrderID = sod.SalesOrderID
GROUP BY
	LastName,
	FirstName