-- create tables
DROP TABLE IF EXISTS Cache, History, Parameters
GO

CREATE TABLE Cache(
    ID INT IDENTITY PRIMARY KEY, 
    UrlAddress VARCHAR(255), 
    LastAccess DATETIME
)

CREATE TABLE History(
    ID INT IDENTITY PRIMARY KEY, 
    UrlAddress VARCHAR(255), 
    LastAccess DATETIME
)

CREATE TABLE Parameters(
    Name VARCHAR(255), 
    Value INT
)

INSERT INTO Parameters
VALUES ('max_cache', 4)

DROP TRIGGER IF EXISTS TR_toCache
GO

-- INSTEAD OF trigger on INSERT to Cache
CREATE TRIGGER TR_toCache ON Cache INSTEAD OF INSERT
AS
BEGIN
    -- get inserted row
    DECLARE @in_UrlAddress VARCHAR(255), @in_LastAccess DATETIME
    SELECT 
        @in_UrlAddress = UrlAddress, 
        @in_LastAccess = LastAccess
    FROM 
        inserted

    -- if a page with the address we are inserting exists in the Cache, just modify the time of its last access
    IF (EXISTS (SELECT 1 FROM Cache WHERE UrlAddress = @in_UrlAddress))
        UPDATE Cache 
        SET LastAccess = @in_LastAccess
        WHERE UrlAddress = @in_UrlAddress
    ELSE
    BEGIN

		-- if an inserted page in not in Cache we check, 
		-- if number of rows before insert is smaller than the one specified in max_cache parameter in Parameters table
        DECLARE @Cache_wierszy INT = (SELECT COUNT(*) FROM Cache)
        DECLARE @Cache_limit INT = (SELECT TOP 1 Value FROM Parameters)

		-- if it's smaller - we insert the row and end 
        IF (@Cache_wierszy < @Cache_limit)
            INSERT INTO Cache 
                VALUES(@in_UrlAddress, @in_LastAccess)
        ELSE
        BEGIN
        
            -- move oldest entry to cache
            DECLARE @temp_ID INT, @temp_UrlAddress VARCHAR(255), @temp_LastAccess DATETIME
            -- we choose the first one possible
            SELECT TOP 1
                @temp_ID = ID,
                @temp_UrlAddress = UrlAddress, 
                @temp_LastAccess = LastAccess
            FROM 
                Cache
            ORDER BY LastAccess

            -- entry is already in cache
            IF (EXISTS (SELECT 1 FROM History WHERE UrlAddress = @temp_UrlAddress))
                UPDATE History
                SET LastAccess = @in_LastAccess
                WHERE UrlAddress = @temp_UrlAddress
            ELSE
                INSERT INTO History
                VALUES(@temp_UrlAddress, @temp_LastAccess)

			-- deleting the oldest entry and adding the new one into Cache
            DELETE FROM Cache WHERE ID = @temp_ID
            INSERT INTO Cache 
                VALUES(@in_UrlAddress, @in_LastAccess)
        END
    END
END

INSERT INTO Cache VALUES('test.com', '2022-03-21 15:13:00');
GO

INSERT INTO Cache VALUES('google.com', '2022-03-25 15:23:00');
GO

INSERT INTO Cache VALUES('youtube.com', '2022-03-24 07:26:00');
GO

INSERT INTO Cache VALUES('example.com', '2022-03-22 05:37:00');
GO

INSERT INTO Cache VALUES('skos.ii.uni.wroc.pl', '2022-03-24 23:47:00');
GO

SELECT * FROM Cache
SELECT * FROM History


DROP TABLE IF EXISTS Cache, History, Parameters
GO

DROP TRIGGER IF EXISTS TR_toCache
GO
     
