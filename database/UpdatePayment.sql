ALTER PROCEDURE dbo.updatePayment (
	  @inStudentID INT
	, @inConvention VARCHAR(64)
	, @inDescription VARCHAR(128)
	, @inPeriod VARCHAR(8)
	, @outResultCode INT OUTPUT
)
AS
BEGIN
	SET NOCOUNT ON;
	BEGIN TRY

		SET @outResultCode = 0;

		SELECT @outResultCode AS outResultCode;

		UPDATE P
		SET 
			P.IsPaid = 1
		FROM dbo.Payment P
		WHERE P.StudentID = @inStudentID 
			AND P.Convention = @inConvention
			AND P.PaymentDescription = @inDescription
			AND P.PaymentPeriod = @inPeriod;

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