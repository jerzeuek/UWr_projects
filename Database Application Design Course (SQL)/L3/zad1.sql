DROP TABLE IF EXISTS [SalesLT].[Product_Backup1];
DROP TABLE IF EXISTS [SalesLT].[Product_Backup2];

DECLARE @start1 DATETIME
DECLARE @start2 DATETIME
DECLARE @end1 DATETIME
DECLARE @end2 DATETIME;

-- Non-cursor part:
SET @start1= GETDATE();

SELECT ProductID, Name, ProductModelID
INTO SalesLT.Product_Backup1
FROM SalesLT.Product
SET @end1 = GETDATE();

PRINT 'Non-Cursor: ' + CAST(Datediff(MILlISECOND, @end1, @start1) as varchar(20)) + 'ms';

--Cursor part - creating table then adding row by row using cursor:

SET @start2= GETDATE();
CREATE TABLE [SalesLT].[Product_Backup2](
	[ProductID] [int] IDENTITY(1,1) NOT NULL,
	[Name] [dbo].[Name] NOT NULL,
	[ProductModelID] [int] NULL)


DECLARE c CURSOR FOR SELECT ProductID, NAME, ProductModelID FROM SalesLT.Product

OPEN c

declare @pID INT
declare @pN NAME
declare @pmID INT

FETCH NEXT FROM c INTO @pID, @pN, @pmID 
WHILE ( @@fetch_status=0 )
BEGIN
	INSERT INTO SalesLT.Product_Backup2 VALUES (@pN, @pmID)
	FETCH NEXT FROM c INTO @pID, @pN, @pmID  
END
PRINT 'Status of last fetch instruction: ' + CAST(@@fetch_status AS VARCHAR)
CLOSE c
DEALLOCATE c

SET @end2 = GETDATE();

PRINT 'Cursor:' + CAST(Datediff(MILlISECOND, @start2, @end2) AS VARCHAR(20)) + 'ms'
