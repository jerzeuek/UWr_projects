ALTER DATABASE AdventureWorksLT SET RECURSIVE_TRIGGERS ON
GO

DROP TABLE IF EXISTS budget;
GO
CREATE TABLE budget
(
  department_name VARCHAR(30) NOT NULL,
  parent_department_name VARCHAR(30) NULL,
  monthly_budget INT NOT NULL
)
GO

DELETE FROM budget
INSERT budget VALUES( 'Domestic training', 'Training', 10 )
INSERT budget VALUES( 'Training', 'Educational services', 100 )
INSERT budget VALUES( 'Educational services', 'Services', 500 )
INSERT budget VALUES( 'Services', NULL, 1200 )
GO

DROP TRIGGER IF EXISTS update_budget
GO
CREATE TRIGGER update_budget ON budget AFTER UPDATE AS
BEGIN
  DECLARE @rows INT
  SET @rows=@@rowcount
  IF ( @rows=0 ) RETURN
  IF ( @rows>1 ) BEGIN			--if the number of modified rows > 1 than we undo the action
    PRINT 'You can modify only one row at the time!'
    ROLLBACK TRANSACTION
    RETURN
  END
  IF ( ( SELECT parent_department_name FROM inserted ) IS NULL ) RETURN --if the budget of a subordinate department changes, the difference in the budget is added to any budget of the parent department, and in fact, for this example, all budgets increase by 10
  UPDATE budget SET monthly_budget = monthly_budget +
    ( SELECT monthly_budget FROM inserted ) -
    ( SELECT monthly_budget FROM deleted )
    WHERE department_name = ( SELECT parent_department_name FROM deleted )
END
GO

SELECT * FROM budget

UPDATE budget SET monthly_budget=20 WHERE department_name = 'Domestic training'

SELECT * FROM budget
