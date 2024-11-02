SET NOCOUNT ON

DROP TABLE IF EXISTS liczby
GO
CREATE TABLE liczby( nr INT PRIMARY KEY, liczba INT )
GO
DECLARE @a INT
SET @a=1
WHILE ( @a<=60)
BEGIN
  INSERT liczby VALUES ( @a, @a )
  SET @a=@a+1
END
GO

DECLARE @x INT
SET @x=10

-- We will look at results and messages!!!

-- the static cursor has a 'copy' of the table it is moving through, so deletion has no effect on its work in reading records
declare c cursor static for select liczba from liczby where liczba<=@x

-- as the dynamic cursor does not have a 'copy' of the table it is browsing, it will browse every second row (because every second row will be deleted)
--declare c cursor for select liczba from liczby where liczba<=@x

-- keyset cursor has a copy of the keys themselves. It will execute once (so it will delete the record with No. = 2), and then it will want to check the record with No. = 2 (the next key),
-- but since it no longer exists, the cursor will throw an error and finish the job and give the rest of the records, without deleting any of them.
--declare c cursor keyset for select liczba from liczby where liczba<=@x

set @x=20

open c

declare @aux int, @licznik int
set @licznik=2

print 'Successive numbers from the cursor:'
fetch next from c into @aux 
while ( @@fetch_status=0 )
begin
  print @aux;
  --  PRINT 'Liczba: '+cast(@aux as varchar)
  --  PRINT 'Licznik: '+cast(@licznik as varchar)
  delete from liczby where liczba=@licznik
  fetch next from c into @aux 
  set @licznik=@licznik+2
end
print 'Last fetch instruction status: ' + cast(@@fetch_status as varchar)
close c
deallocate c

select * from liczby where liczba<=10