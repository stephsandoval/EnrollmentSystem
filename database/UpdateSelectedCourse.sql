ALTER PROCEDURE dbo.updateSelectedCourse (
	  @inStudentID INT
	, @inCourseID VARCHAR(8)
	, @inGroupNumber INT
	, @inSelected BIT
	, @outResultCode INT OUTPUT
)
AS
BEGIN
	SET NOCOUNT ON;
	BEGIN TRY

		SET @outResultCode = 0;

		SELECT @outResultCode AS outResultCode;

		IF EXISTS (SELECT 1 FROM dbo.SelectedCourse SC
			WHERE SC.CourseID = @inCourseID AND SC.StudentID = @inStudentID)
		BEGIN
			UPDATE SC
			SET 
				  SC.Selected = @inSelected
				, SC.GroupNumber = @inGroupNumber
			WHERE SC.CourseID = @inCourseID AND SC.StudentID = @inStudentID
		END
		ELSE
		BEGIN
			INSERT INTO dbo.SelectedCourse (
				  CourseID
				, StudentID
				, GroupNumber
				, Selected
			)
			VALUES (
				  @inCourseID
				, @inStudentID
				, @inGroupNumber
				, @inSelected
			);
		END

	END TRY
	BEGIN CATCH

		INSERT INTO DatabaseError VALUES (
			  SUSER_SNAME()
			, ERROR_NUMBER()
			, ERROR_STATE()
			, ERROR_SEVERITY()
			, ERROR_LINE()
			, ERROR_PROCEDURE()
			, ERROR_MESSAGE()
			, GETDATE()
		);

		SET @outResultCode = 50008;
		SELECT @outResultCode AS outResultCode;

	END CATCH;
	SET NOCOUNT OFF;
END