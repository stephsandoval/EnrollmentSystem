ALTER PROCEDURE dbo.getEnrollmentGroupOptions (
	  @inStudent INT
	, @inCourseID VARCHAR(8)
	, @outResultCode INT OUTPUT
)
AS
BEGIN
	SET NOCOUNT ON;
	BEGIN TRY

		SET @outResultCode = 0;

		SELECT @outResultCode AS outResultCode;

		SELECT @inCourseID AS 'CourseID'
			, CA.CampusShortName
			, EC.GroupNumber
			, EC.ClassDays + ' ' + CONVERT(VARCHAR(5), EC.StartHour, 108) + '-' + CONVERT(VARCHAR(5), EC.EndHour, 108) AS 'Schedule'
			, T.TeacherName
			, EC.Capacity
			, M.Modality
			, CASE
				WHEN EXISTS (SELECT 1 FROM dbo.SelectedEnrollmentCourse SEC 
					WHERE SEC.CourseID = @inCourseID AND SEC.StudentID = @inStudent
						AND SEC.GroupNumber = EC.GroupNumber AND SEC.Selected = 1) THEN 1
				ELSE 0
			  END AS 'Selected'
		FROM dbo.EnrollmentCourse EC
		INNER JOIN dbo.Campus CA ON CA.CampusID = EC.CampusID
		INNER JOIN dbo.Teacher T ON T.TeacherID = EC.TeacherID
		INNER JOIN dbo.ModalityType M ON M.ModalityID = EC.ModalityID
		INNER JOIN dbo.Student S ON S.CampusID = CA.CampusID
		WHERE S.StudentID = @inStudent AND EC.CourseID = @inCourseID;

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