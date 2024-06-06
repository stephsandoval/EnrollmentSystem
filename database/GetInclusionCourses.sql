CREATE PROCEDURE dbo.getInclusionCourses (
	  @inStudent INT
	, @outResultCode INT OUTPUT
)
AS
BEGIN
	SET NOCOUNT ON;
	BEGIN TRY

		SET @outResultCode = 0;

		SELECT @outResultCode AS outResultCode;

		SELECT DISTINCT C.CourseID
			, C.CourseName
		FROM dbo.Course C
		INNER JOIN dbo.CoursePerCareerPlan CPP ON CPP.CourseID = C.CourseID
		INNER JOIN dbo.Requirement R ON R.CourseID = C.CourseID
		INNER JOIN dbo.Student S ON S.CareerPlanID = CPP.CareerPlanID
		WHERE S.StudentID = @inStudent
			AND NOT EXISTS (SELECT 1 FROM dbo.AcademicHistory AH
				WHERE AH.CourseID = C.CourseID AND AH.Aprobado = 1)
			AND EXISTS (SELECT 1 FROM dbo.AcademicHistory AH
				WHERE R.RequirementID = AH.CourseID AND AH.Aprobado = 1)
			AND EXISTS (SELECT 1 FROM dbo.InclusionCourse IC
				WHERE IC.CourseID = C.CourseID);

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