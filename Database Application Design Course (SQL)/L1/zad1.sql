SELECT DISTINCT adr.City 
FROM 
	[SalesLT].[Address] AS adr
	JOIN [SalesLT].[SalesOrderHeader] AS soh 
	ON adr.AddressID = soh.ShipToAddressID 
WHERE
   soh.ShipDate IS NOT NULL
ORDER BY adr.City 