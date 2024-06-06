ALTER PROCEDURE dbo.getInclusionGroupOptions (
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
			, IC.GroupNumber
			, IC.ClassDays + ' ' + CONVERT(VARCHAR(5), IC.StartHour, 108) + '-' + CONVERT(VARCHAR(5), IC.EndHour, 108) AS 'Schedule'
			, T.TeacherName
			, IC.Capacity
			, M.Modality
		FROM dbo.InclusionCourse IC
		INNER JOIN dbo.Campus CA ON CA.CampusID = IC.CampusID
		INNER JOIN dbo.Teacher T ON T.TeacherID = IC.TeacherID
		INNER JOIN dbo.ModalityType M ON M.ModalityID = IC.ModalityID
		INNER JOIN dbo.Student S ON S.CampusID = CA.CampusID
		WHERE S.StudentID = @inStudent AND IC.CourseID = @inCourseID;

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