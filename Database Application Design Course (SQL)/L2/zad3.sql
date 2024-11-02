CREATE PROCEDURE IntroduceNewReader
    @PESEL CHAR(11),
    @Nazwisko VARCHAR(30),
    @Miasto VARCHAR(30),
    @Data_Urodzenia DATE
AS
BEGIN
    -- Check PESEL format validity
    IF (LEN(@PESEL) <> 11) OR (NOT @PESEL LIKE '[0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9][0-9]')
    BEGIN;
        THROW 50000, 'Error: PESEL is not in a valid format', 1;
    END

    -- Check last name format and capital letter
    IF (LEN(@Nazwisko) < 2 OR LEFT(@Nazwisko, 1) COLLATE Latin1_General_CS_AI <> UPPER(LEFT(@Nazwisko, 1)))
    BEGIN;
        THROW 50001, 'Error: Last name is not in a valid format', 1;
    END

    -- Check birth date format
    IF @Data_Urodzenia > GETDATE() OR @Data_Urodzenia < '1900-01-01' 
    BEGIN;
        THROW 50002, 'Error: Incorrect birth date', 1;
    END

    -- Insert the new reader if all validations are passed
    INSERT INTO Czytelnik (PESEL, Nazwisko, Miasto, Data_Urodzenia)
    VALUES (@PESEL, @Nazwisko, @Miasto, @Data_Urodzenia);
END;