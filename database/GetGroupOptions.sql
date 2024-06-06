ALTER FUNCTION dbo.GetGroupOptions (@inStudent INT)
RETURNS @GroupOptions TABLE (
	  CourseID VARCHAR(8)
	, Campus VARCHAR(16)
	, GroupNumber INT
	, ClassDays VARCHAR(8)
	, StartHour TIME(0)
	, EndHour TIME(0)
	, TeacherName VARCHAR(64)
	, Capacity INT
	, Modality VARCHAR(16)
)
AS
BEGIN
	
	DECLARE @CourseOptions TABLE (
		  CourseID VARCHAR(8)
		, CourseName VARCHAR(64)
	);

	INSERT INTO @CourseOptions (
          CourseID
        , CourseName
    )
    SELECT EC.CourseID
		, EC.CourseName
	FROM dbo.GetEnrollmentCourses (2023395946) EC;

	INSERT INTO @GroupOptions (
		  CourseID
		, Campus
		, GroupNumber
		, ClassDays
		, StartHour
		, EndHour
		, TeacherName
		, Capacity
		, Modality
	)
	SELECT CO.CourseID
		, CA.CampusShortName
		, EC.GroupNumber
		, EC.ClassDays
		, EC.StartHour
		, EC.EndHour
		, T.TeacherName
		, EC.Capacity
		, M.Modality
	FROM dbo.EnrollmentCourse EC
	INNER JOIN dbo.Campus CA ON CA.CampusID = EC.CampusID
	INNER JOIN dbo.Teacher T ON T.TeacherID = EC.TeacherID
	INNER JOIN dbo.ModalityType M ON M.ModalityID = EC.ModalityID
	INNER JOIN dbo.Student S ON S.CampusID = CA.CampusID
	INNER JOIN @CourseOptions CO ON CO.CourseID = EC.CourseID
	WHERE S.StudentID = @inStudent;

	RETURN;

END