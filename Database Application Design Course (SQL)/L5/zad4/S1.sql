--SET TRANSACTION ISOLATION LEVEL READ COMMITTED
--SET TRANSACTION ISOLATION LEVEL REPEATABLE READ
--SET TRANSACTION ISOLATION LEVEL SERIALIZABLE

--S1:
drop table if exists dbo.liczby1;
drop table if exists dbo.liczby2;
create table dbo.liczby1 ( liczba int )
create table dbo.liczby2 ( liczba int )
go

SET TRANSACTION ISOLATION LEVEL READ COMMITTED
GO

begin tran
insert dbo.liczby1 values ( 1 )
WAITFOR DELAY '00:00:10'
update dbo.liczby2 set liczba=10
WAITFOR DELAY '00:00:10'


--COMMIT

SELECT * FROM dbo.liczby1

ROLLBACK