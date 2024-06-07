DECLARE @out INT;
EXEC dbo.getAcademicHistory 2023395946, @out OUTPUT
EXEC dbo.getEnrollmentCourses 2023395946, @out OUTPUT
EXEC dbo.getEnrollmentGroupOptions 2023395946, 'IC4700', @out OUTPUT
EXEC dbo.getInclusionCourses 2023395946, @out OUTPUT
EXEC dbo.getInclusionGroupOptions 2023395946, 'IC4700', @out OUTPUT
EXEC dbo.getPendingPayments 2023395946, @out OUTPUT

SELECT * FROM Payment;