ALTER PROCEDURE dbo.getPendingPayments (
	  @inStudentID INT
	, @outResultCode INT OUTPUT
)
AS
BEGIN
	SET NOCOUNT ON;
	BEGIN TRY

		SET @outResultCode = 0;

		SELECT @outResultCode AS outResultCode;

		SELECT P.Convention
			, P.PaymentDescription
			, P.PaymentPeriod
			, P.Total
		FROM dbo.Payment P
		WHERE P.StudentID = @inStudentID AND P.IsPaid = 0;

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