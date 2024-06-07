CREATE PROCEDURE dbo.updateSelectedInclusionCourse (
    @inStudentID INT,
    @inCourseSelection dbo.CourseArray READONLY,
    @outResultCode INT OUTPUT
)
AS
BEGIN
    SET NOCOUNT ON;
    BEGIN TRY
        DECLARE @coursesSelected INT;
        DECLARE @currentCourse VARCHAR(8);
        DECLARE @courseIndex INT;
        DECLARE @groupNumber INT;
        DECLARE @selected BIT;

        DECLARE @PreviouslySelectedCourse TABLE (
              SEC INT IDENTITY(1,1)
            , CourseID VARCHAR(8)
            , GroupNumber INT
            , Selected BIT
        );

        SET @outResultCode = 0;

        SELECT @outResultCode AS outResultCode;

        INSERT INTO @PreviouslySelectedCourse (
              CourseID
            , GroupNumber
            , Selected
        )
        SELECT SC.CourseID
            , SC.GroupNumber
            , SC.Selected
        FROM dbo.SelectedInclusionCourse SC
        WHERE SC.StudentID = @inStudentID;

        UPDATE SC
        SET SC.GroupNumber = CS.GroupNumber, SC.Selected = CS.Selected
        FROM dbo.SelectedInclusionCourse SC
        INNER JOIN @inCourseSelection CS ON SC.CourseID = CS.CourseID
        WHERE SC.StudentID = @inStudentID;

        INSERT INTO dbo.SelectedInclusionCourse (StudentID, CourseID, GroupNumber, Selected)
        SELECT @inStudentID, CS.CourseID, CS.GroupNumber, 1
        FROM @inCourseSelection CS
        LEFT JOIN dbo.SelectedInclusionCourse SC ON SC.CourseID = CS.CourseID AND SC.StudentID = @inStudentID
        WHERE SC.CourseID IS NULL;

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
