CREATE TABLE Campus (
    CampusID INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    CampusName VARCHAR(64) NOT NULL,
	CampusShortName VARCHAR(16) NOT NULL
);

CREATE TABLE Career (
    CareerID INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    CareerName VARCHAR(64) NOT NULL
);

CREATE TABLE CareerPlan (
    CareerPlanID INT NOT NULL PRIMARY KEY,
    CareerID INT NOT NULL,
    FOREIGN KEY (CareerID) REFERENCES Career(CareerID)
);

CREATE TABLE Student (
    StudentID INT NOT NULL PRIMARY KEY,
    StudentName VARCHAR(64) NOT NULL,
    CareerPlanID INT NOT NULL,
    CampusID INT NOT NULL,
    FOREIGN KEY (CareerPlanID) REFERENCES CareerPlan(CareerPlanID),
    FOREIGN KEY (CampusID) REFERENCES Campus(CampusID)
);

CREATE TABLE ModalityType (
    ModalityID INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    Modality VARCHAR(16) NOT NULL
);

CREATE TABLE Course (
    CourseID VARCHAR(8) NOT NULL PRIMARY KEY,
    CourseName VARCHAR(64) NOT NULL,
    Credits INT NOT NULL,
);

CREATE TABLE Requirement (
    CourseID VARCHAR(8) NOT NULL,
    RequirementID VARCHAR(8) NOT NULL,
    PRIMARY KEY (CourseID, RequirementID),
    FOREIGN KEY (CourseID) REFERENCES Course(CourseID),
    FOREIGN KEY (RequirementID) REFERENCES Course(CourseID)
);

CREATE TABLE Corequirement (
	CourseID VARCHAR(8) NOT NULL,
	CorequirementID VARCHAR(8) NOT NULL,
	PRIMARY KEY (CourseID, CorequirementID),
	FOREIGN KEY (CourseID) REFERENCES Course(CourseID),
    FOREIGN KEY (CorequirementID) REFERENCES Course(CourseID)
);

CREATE TABLE CoursePerCareerPlan (
    CareerPlanID INT NOT NULL,
    CourseID VARCHAR(8) NOT NULL,
    PRIMARY KEY (CareerPlanID, CourseID),
    FOREIGN KEY (CareerPlanID) REFERENCES CareerPlan(CareerPlanID),
    FOREIGN KEY (CourseID) REFERENCES Course(CourseID)
);

CREATE TABLE Teacher (
    TeacherID INT NOT NULL PRIMARY KEY,
    TeacherName VARCHAR(64) NOT NULL
);

CREATE TABLE Classroom (
    ClassroomID VARCHAR(8) NOT NULL PRIMARY KEY
);
CREATE TABLE EnrollmentCourse (
    EnrollmentCourseID INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    CourseID VARCHAR(8) NOT NULL,
    TeacherID INT NOT NULL,
    ClassroomID VARCHAR(8) NOT NULL,
	ModalityID INT NOT NULL,
	CampusID INT NOT NULL,
	GroupNumber INT NOT NULL,
	Capacity INT NOT NULL,
	ClassDays VARCHAR(8) NOT NULL,
	StartHour TIME(0) NOT NULL,
	EndHour TIME(0) NOT NULL, 
    FOREIGN KEY (CourseID) REFERENCES Course(CourseID),
    FOREIGN KEY (TeacherID) REFERENCES Teacher(TeacherID),
    FOREIGN KEY (ClassroomID) REFERENCES Classroom(ClassroomID),
	FOREIGN KEY (ModalityID) REFERENCES ModalityType(ModalityID),
	FOREIGN KEY (CampusID) REFERENCES Campus(CampusID)
);

CREATE TABLE InclusionCourse (
    InclusionCourseID INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    CourseID VARCHAR(8) NOT NULL,
    TeacherID INT NOT NULL,
    ClassroomID VARCHAR(8) NOT NULL,
	ModalityID INT NOT NULL,
	CampusID INT NOT NULL,
	GroupNumber INT NOT NULL,
	Capacity INT NOT NULL,
	ClassDays VARCHAR(8) NOT NULL,
	StartHour TIME(0) NOT NULL,
	EndHour TIME(0) NOT NULL, 
    FOREIGN KEY (CourseID) REFERENCES Course(CourseID),
    FOREIGN KEY (TeacherID) REFERENCES Teacher(TeacherID),
    FOREIGN KEY (ClassroomID) REFERENCES Classroom(ClassroomID),
	FOREIGN KEY (ModalityID) REFERENCES ModalityType(ModalityID),
	FOREIGN KEY (CampusID) REFERENCES Campus(CampusID)
);

CREATE TABLE AcademicHistory (
    AcademicHistoryID INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    StudentID INT NOT NULL,
    CourseID VARCHAR(8) NOT NULL,
	GroupNumber INT NOT NULL,
    EnrollmentPeriod VARCHAR(8) NOT NULL,
    Grade INT NOT NULL,
    Condition VARCHAR(16) NOT NULL,
	Aprobado BIT NOT NULL,
    FOREIGN KEY (StudentID) REFERENCES Student(StudentID),
    FOREIGN KEY (CourseID) REFERENCES Course(CourseID)
);

CREATE TABLE Payment (
    PaymentID INT NOT NULL PRIMARY KEY IDENTITY(1,1),
    StudentID INT NOT NULL,
    Total MONEY NOT NULL,
    IsPaid BIT NOT NULL,
    FOREIGN KEY (StudentID) REFERENCES Student(StudentID)
);

CREATE TABLE SelectedEnrollmentCourse (
	CourseID VARCHAR(8) NOT NULL,
	StudentID INT NOT NULL,
	GroupNumber INT NOT NULL,
	Selected BIT NOT NULL,
	PRIMARY KEY (CourseID, StudentID),
	FOREIGN KEY (CourseID) REFERENCES Course(CourseID),
	FOREIGN KEY (StudentID) REFERENCES Student(StudentID)
);

CREATE TABLE SelectedInclusionCourse (
	CourseID VARCHAR(8) NOT NULL,
	StudentID INT NOT NULL,
	GroupNumber INT NOT NULL,
	Selected BIT NOT NULL,
	PRIMARY KEY (CourseID, StudentID),
	FOREIGN KEY (CourseID) REFERENCES Course(CourseID),
	FOREIGN KEY (StudentID) REFERENCES Student(StudentID)
);

--CREATE TABLE DatabaseError (
--	ErrorID INT NOT NULL IDENTITY(1,1),
--	Username VARCHAR(64) NULL,
--	ErrorNumber INT NULL,
--	ErrorState INT NULL,
--	ErrorSeverity INT NULL,
--	ErrorLine INT NULL,
--	ErrorProcedure VARCHAR(MAX) NULL,
--	ErrorMessage VARCHAR(MAX) NULL,
--	ErrorDateTime DATETIME NULL
--) ON [PRIMARY] TEXTIMAGE_ON [PRIMARY];

--DROP TABLE EnrollmentCourse;
--DROP TABLE InclusionCourse;
--DROP TABLE SelectedEnrollmentCourse;
--DROP TABLE SelectedInclusionCourse;
--DROP TABLE Teacher;
--DROP TABLE Classroom;
--DROP TABLE Payment;
--DROP TABLE AcademicHistory;
--DROP TABLE CoursePerCareerPlan;
--DROP TABLE Requirement;
--DROP TABLE Corequirement;
--DROP TABLE Course;
--DROP TABLE ModalityType;
--DROP TABLE Student;
--DROP TABLE Campus;
--DROP TABLE CareerPlan;
--DROP TABLE Career;

--CREATE TYPE dbo.CourseArray AS TABLE (
--      CourseID VARCHAR(8)
--	, GroupNumber INT
--	, Selected BIT
--);

--DROP TYPE dbo.CourseArray