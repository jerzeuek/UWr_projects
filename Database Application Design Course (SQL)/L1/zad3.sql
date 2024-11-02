SELECT
	adr.City, 
	COUNT(DISTINCT custadr.CustomerID) AS NumOfCustomers, 
	COUNT(DISTINCT cust.SalesPerson) AS NumOfSalesPer
FROM 
	[SalesLT].[Address] AS adr
	JOIN [SalesLT].[CustomerAddress] AS custadr
	ON adr.AddressID = custadr.AddressID
	
	JOIN [SalesLT].[Customer] AS cust
	ON custadr.CustomerID = cust.CustomerID
GROUP BY adr.City
