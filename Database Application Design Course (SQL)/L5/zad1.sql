IF EXISTS (SELECT name FROM sys.objects WHERE name = N'UpdateContactInfo')  
    DROP PROCEDURE UpdateContactInfo;  
GO

CREATE PROCEDURE UpdateContactInfo  
    @PersonID INT,
    @NewEmail NVARCHAR(255),
    @NewPhone NVARCHAR(20)
AS  
BEGIN
    DECLARE @TranCounter INT;  
    SET @TranCounter = @@TRANCOUNT;  
    
    IF @TranCounter > 0
        SAVE TRANSACTION ProcedureSave;  
    ELSE  
        BEGIN TRANSACTION;  
    
    BEGIN TRY  
        UPDATE [SalesLT].Customer
        SET EmailAddress = @NewEmail,
            Phone = @NewPhone
        WHERE CustomerID = @PersonID;
        
        IF @TranCounter = 0
            COMMIT TRANSACTION;  
    END TRY  
    
    BEGIN CATCH  
        IF @TranCounter = 0
            ROLLBACK TRANSACTION;  
        ELSE  
            IF XACT_STATE() <> -1  
                ROLLBACK TRANSACTION ProcedureSave;  

        DECLARE @ErrorMessage NVARCHAR(4000);  
        DECLARE @ErrorSeverity INT;  
        DECLARE @ErrorState INT;  
  
        SELECT @ErrorMessage = ERROR_MESSAGE();  
        SELECT @ErrorSeverity = ERROR_SEVERITY();  
        SELECT @ErrorState = ERROR_STATE();  
  
        RAISERROR (@ErrorMessage, 
                   @ErrorSeverity, 
                   @ErrorState
                   );  
    END CATCH  
END;
