SELECT TOP 1
	soh.SalesOrderID,
	soh.SalesOrderNumber,
	soh.PurchaseOrderNumber,
	SUM(sod.LineTotal) AS TotalWithDiscount,
	SUM(sod.OrderQty * sod.UnitPrice) AS TotalWithoutDiscount,
	sod.OrderQty
FROM 
	[SalesLT].[SalesOrderHeader] AS soh
	JOIN [SalesLT].[SalesOrderDetail] AS sod
	ON soh.SalesOrderID = sod.SalesOrderID
GROUP BY
	soh.SalesOrderID,
	soh.SalesOrderNumber,
	soh.PurchaseOrderNumber,
	sod.OrderQty
ORDER BY
	SUM(sod.LineTotal)-SUM(sod.OrderQty * sod.UnitPrice)