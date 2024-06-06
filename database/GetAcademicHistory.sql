ALTER FUNCTION dbo.GetAcademicHistory (@inStudent INT)
RETURNS @StudentAcademicHistory TABLE (
	  CourseID VARCHAR(8)
	, CourseName VARCHAR(64)
	, GroupNumber INT
	, Credits INT
	, Condition VARCHAR(16)
	, Grade INT
	, EnrollmentPeriod VARCHAR(8)
)
AS
BEGIN

	INSERT INTO @StudentAcademicHistory (
		  CourseID
		, CourseName
		, GroupNumber
		, Credits
		, Condition
		, Grade
		, EnrollmentPeriod
	)
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

	RETURN;

END