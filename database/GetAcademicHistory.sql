ALTER PROCEDURE dbo.getAcademicHistory (
	  @inStudent INT
	, @outResultCode INT OUTPUT
)
AS
BEGIN
	SET NOCOUNT ON;
	BEGIN TRY

		SET @outResultCode = 0;

		SELECT @outResultCode AS outResultCode;

		SELECT AH.CourseID
			, C.CourseName
			, AH.GroupNumber
			, C.Credits
			, AH.Condition
			, AH.Grade
			, AH.EnrollmentPeriod
		FROM dbo.AcademicHistory AH
		INNER JOIN dbo.Course C ON C.CourseID = AH.CourseID
		WHERE AH.StudentID = @inStudent;

	END TRY
	BEGIN CATCH

		INSERT INTO DatabaseError VALUES (
			SUSER_SNAME(),
			ERROR_NUMBER(),
			ERROR_STATE(),
			ERROR_SEVERITY(),
			ERROR_LINE(),
			ERROR_PROCEDURE(),
			ERROR_MESSAGE(),
			GETDATE()
		);

		SET @outResultCode = 50008;
		SELECT @outResultCode AS outResultCode;

	END CATCH;
	SET NOCOUNT OFF;
END