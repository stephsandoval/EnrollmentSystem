ALTER FUNCTION dbo.GetEnrollmentCourses (@inStudent INT)
RETURNS @CourseOptions TABLE (
	  CourseID VARCHAR(8)
	, CourseName VARCHAR(64)
)
AS
BEGIN
	
	INSERT INTO @CourseOptions (
		  CourseID
		, CourseName
	)
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
			WHERE R.RequirementID = AH.CourseID AND AH.Aprobado = 1);

	RETURN;
END