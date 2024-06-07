BEGIN TRY
    BEGIN TRANSACTION;

	DECLARE @xmlData XML

	SELECT @xmlData = X
	FROM OPENROWSET (BULK 'C:\Users\Stephanie\OneDrive - Estudiantes ITCR\Semestre III\Requerimientos de Software\System\datos.xml', SINGLE_BLOB) AS xmlfile(X)

	DECLARE @value INT
	EXEC sp_xml_preparedocument @value OUTPUT, @xmlData

    INSERT INTO dbo.Career (CareerName)
	SELECT C.career.value('@Name', 'VARCHAR(64)') AS CareerName
	FROM @xmlData.nodes('/Data/Careers/Career') AS C(career);

	DECLARE @TempCareerPlans TABLE (
		ID INT,
		Career VARCHAR(64)
	)

	INSERT INTO @TempCareerPlans (ID, Career)
	SELECT CP.careerplan.value('@ID', 'INT') AS ID,
		   CP.careerplan.value('@Name', 'VARCHAR(64)') AS Career
	FROM @xmlData.nodes('/Data/CareerPlans/CareerPlan') AS CP(careerplan)

	INSERT INTO dbo.CareerPlan (CareerPlanID, CareerID)
	SELECT CP.ID, C.CareerID
	FROM @TempCareerPlans CP
	INNER JOIN dbo.Career C ON C.CareerName = CP.Career

	INSERT INTO dbo.Campus (CampusName, CampusShortName)
	SELECT C.campus.value('@Name', 'VARCHAR(64)') AS CampusName,
		   C.campus.value('@Shortname', 'VARCHAR(16)') AS CampusShortname
	FROM @xmlData.nodes('/Data/Campuses/Campus') AS C(campus);

	DECLARE @TempStudents TABLE (
		StudentID INT,
		StudentName VARCHAR(64),
		CareerPlanID INT,
		CampusShortName VARCHAR(16)
	)

	INSERT INTO @TempStudents (StudentID, StudentName, CareerPlanID, CampusShortName)
	SELECT 
		S.student.value('@ID', 'INT') AS StudentID,
		S.student.value('@Name', 'VARCHAR(64)') AS StudentName,
		S.student.value('@Career', 'INT') AS CareerPlanID,
		S.student.value('@Campus', 'VARCHAR(16)') AS CampusShortName
	FROM @xmlData.nodes('/Data/Students/Student') AS S(student);

	INSERT INTO Student (StudentID, StudentName, CareerPlanID, CampusID)
	SELECT 
		TS.StudentID,
		TS.StudentName,
		TS.CareerPlanID,
		C.CampusID
	FROM @TempStudents TS
	JOIN Campus C ON C.CampusShortName = TS.CampusShortName

	INSERT INTO dbo.ModalityType (Modality)
	SELECT MT.modality.value('@Name', 'VARCHAR(16)') AS Modality
	FROM @xmlData.nodes('/Data/ModalityTypes/ModalityType') AS MT(modality);

	INSERT INTO dbo.Teacher (TeacherID, TeacherName)
	SELECT T.teacher.value('@ID', 'INT') AS TeacherID,
		   T.teacher.value('@Name', 'VARCHAR(64)') AS TeacherName
	FROM @xmlData.nodes('/Data/Teachers/Teacher') AS T(teacher);

	INSERT INTO dbo.Classroom (ClassroomID)
	SELECT C.classroom.value('@ID', 'VARCHAR(8)') AS ClassroomID
	FROM @xmlData.nodes('/Data/Classrooms/Classroom') AS C(classroom);

	INSERT INTO dbo.Course (CourseID, CourseName, Credits)
	SELECT C.course.value('@ID', 'VARCHAR(8)') AS CourseID,
		   C.course.value('@Name', 'VARCHAR(64)') AS CourseName,
		   C.course.value('@Credits', 'INT') AS Credits
	FROM @xmlData.nodes('/Data/Courses/Course') AS C(course);

	INSERT INTO dbo.Requirement (CourseID, RequirementID)
	SELECT R.requirement.value('@Course', 'VARCHAR(8)') AS CourseID,
		   R.requirement.value('@Requirement', 'VARCHAR(8)') AS RequirementID
	FROM @xmlData.nodes('/Data/Requirements/Requirement') AS R(requirement);

	INSERT INTO dbo.Corequirement (CourseID, CorequirementID)
	SELECT C.corequirement.value('@Course', 'VARCHAR(8)') AS CourseID,
		   C.corequirement.value('@Corequirement', 'VARCHAR(8)') AS CorequirementID
	FROM @xmlData.nodes('/Data/Corequirements/Corequirement') AS C(corequirement);

	INSERT INTO dbo.CoursePerCareerPlan (CareerPlanID, CourseID)
	SELECT C.course.value('@Plan', 'INT') AS CareerPlanID,
		   C.course.value('@ID', 'VARCHAR(8)') AS CourseID
	FROM @xmlData.nodes('/Data/CoursesPerCareerPlan/Course') AS C(course);

	DECLARE @TempEnrollmentCourses TABLE (
		CourseID VARCHAR(8),
		TeacherID INT,
		ClassroomID VARCHAR(16),
		Modality CHAR(1),
		CampusShortName VARCHAR(16),
		GroupNumber INT,
		Capacity INT,
		ClassDays VARCHAR(8),
		StartHour TIME,
		EndHour TIME
	)

	INSERT INTO @TempEnrollmentCourses (CourseID, TeacherID, ClassroomID, Modality, CampusShortName, GroupNumber, Capacity, ClassDays, StartHour, EndHour)
	SELECT 
		EC.enrollmentcourse.value('@Course', 'VARCHAR(8)') AS CourseID,
		EC.enrollmentcourse.value('@Teacher', 'INT') AS TeacherID,
		EC.enrollmentcourse.value('@Clasroom', 'VARCHAR(16)') AS ClassroomID,
		EC.enrollmentcourse.value('@Modality', 'CHAR') AS Modality,
		EC.enrollmentcourse.value('@Campus', 'VARCHAR(16)') AS CampusShortName,
		EC.enrollmentcourse.value('@Group', 'INT') AS GroupNumber,
		EC.enrollmentcourse.value('@Capacity', 'INT') AS Capacity,
		EC.enrollmentcourse.value('@ClassDays', 'VARCHAR(8)') AS ClassDays,
		EC.enrollmentcourse.value('@Start', 'TIME(0)') AS StartHour,
		EC.enrollmentcourse.value('@End', 'TIME(0)') AS EndHour
	FROM @xmlData.nodes('/Data/EnrollmentCourses/EnrollmentCourse') AS EC(enrollmentcourse);

	INSERT INTO dbo.EnrollmentCourse (CourseID, TeacherID, ClassroomID, ModalityID, CampusID, GroupNumber, Capacity, ClassDays, StartHour, EndHour)
	SELECT 
		TEC.CourseID,
		TEC.TeacherID,
		TEC.ClassroomID,
		M.ModalityID,
		C.CampusID,
		TEC.GroupNumber,
		TEC.Capacity,
		TEC.ClassDays,
		TEC.StartHour,
		TEC.EndHour
	FROM @TempEnrollmentCourses TEC
	JOIN dbo.ModalityType M ON LEFT(M.Modality, 1) = TEC.Modality
	JOIN dbo.Campus C ON C.CampusShortname = TEC.CampusShortName;

	DECLARE @TempInclusionCourses TABLE (
		CourseID VARCHAR(8),
		TeacherID INT,
		ClassroomID VARCHAR(16),
		Modality CHAR(1),
		CampusShortName VARCHAR(16),
		GroupNumber INT,
		Capacity INT,
		ClassDays VARCHAR(8),
		StartHour TIME,
		EndHour TIME
	)

	INSERT INTO @TempInclusionCourses (CourseID, TeacherID, ClassroomID, Modality, CampusShortName, GroupNumber, Capacity, ClassDays, StartHour, EndHour)
	SELECT 
		IC.inclusionCourse.value('@Course', 'VARCHAR(8)') AS CourseID,
		IC.inclusionCourse.value('@Teacher', 'INT') AS TeacherID,
		IC.inclusionCourse.value('@Clasroom', 'VARCHAR(16)') AS ClassroomID,
		IC.inclusionCourse.value('@Modality', 'CHAR') AS Modality,
		IC.inclusionCourse.value('@Campus', 'VARCHAR(16)') AS CampusShortName,
		IC.inclusionCourse.value('@Group', 'INT') AS GroupNumber,
		IC.inclusionCourse.value('@Capacity', 'INT') AS Capacity,
		IC.inclusionCourse.value('@ClassDays', 'VARCHAR(8)') AS ClassDays,
		IC.inclusionCourse.value('@Start', 'TIME(0)') AS StartHour,
		IC.inclusionCourse.value('@End', 'TIME(0)') AS EndHour
	FROM @xmlData.nodes('/Data/InclusionCourses/InclusionCourse') AS IC(inclusionCourse);

	INSERT INTO dbo.InclusionCourse(CourseID, TeacherID, ClassroomID, ModalityID, CampusID, GroupNumber, Capacity, ClassDays, StartHour, EndHour)
	SELECT 
		TIC.CourseID,
		TIC.TeacherID,
		TIC.ClassroomID,
		M.ModalityID,
		C.CampusID,
		TIC.GroupNumber,
		TIC.Capacity,
		TIC.ClassDays,
		TIC.StartHour,
		TIC.EndHour
	FROM @TempInclusionCourses TIC
	JOIN dbo.ModalityType M ON LEFT(M.Modality, 1) = TIC.Modality
	JOIN dbo.Campus C ON C.CampusShortname = TIC.CampusShortName;

	INSERT INTO AcademicHistory (StudentID, CourseID, EnrollmentPeriod, GroupNumber, Grade, Condition, Aprobado)
	SELECT 
		AH.history.value('@Student', 'INT') AS StudentID,
		AH.history.value('@Course', 'VARCHAR(8)') AS CourseID,
		AH.history.value('@Period', 'VARCHAR(8)') AS EnrollmentPeriod,
		AH.history.value('@Group', 'INT') AS GroupNumber,
		AH.history.value('@Grade', 'INT') AS Grade,
		AH.history.value('@Condition', 'VARCHAR(16)') AS Condition,
		CASE
			WHEN AH.history.value('@Condition', 'VARCHAR(16)') = 'Reprobado' THEN 0
			ELSE 1
		END AS Aprobado
	FROM @xmlData.nodes('/Data/AcademicHistory/History') AS AH(history);

	INSERT INTO Payment (StudentID, Convention, PaymentDescription, PaymentPeriod, Total, IsPaid)
	SELECT 
		P.payment.value('@Student', 'INT') AS StudentID,
		P.payment.value('@Convention', 'VARCHAR(64)') AS Convention,
		P.payment.value('@Description', 'VARCHAR(128)') AS PaymentDescription,
		P.payment.value('@Period', 'VARCHAR(8)') AS PaymentPeriod,
		P.payment.value('@Amount', 'INT') AS Total,
		CASE
			WHEN P.payment.value('@Paid', 'VARCHAR(16)') = 'True' THEN 1
			ELSE 0
		END AS IsPaid
	FROM @xmlData.nodes('/Data/Payments/Payment') AS P(payment);

	EXEC sp_xml_removedocument @value

    COMMIT TRANSACTION;
END TRY
BEGIN CATCH

    IF @@TRANCOUNT > 0
        ROLLBACK TRANSACTION;

	INSERT INTO dbo.DatabaseError VALUES (
			SUSER_SNAME(),
			ERROR_NUMBER(),
			ERROR_STATE(),
			ERROR_SEVERITY(),
			ERROR_LINE(),
			ERROR_PROCEDURE(),
			ERROR_MESSAGE(),
			GETDATE()
		);

END CATCH;

SELECT * FROM Student;
SELECT * FROM Career;
SELECT * FROM CareerPlan;
SELECT * FROM Campus;
SELECT * FROM ModalityType;
SELECT * FROM Teacher;
SELECT * FROM Classroom;
SELECT * FROM Course;
SELECT * FROM Requirement;
SELECT * FROM Corequirement;
SELECT * FROM CoursePerCareerPlan;
SELECT * FROM EnrollmentCourse;
SELECT * FROM AcademicHistory;
SELECT * FROM InclusionCourse;
SELECT * FROM Payment;
SELECT * FROM DatabaseError;