package AcademicHistory;

public class Record {
    
    private String courseID, courseName, period, condition;
    private int groupNumber, credits, grade;

    public Record (String courseID, String courseName, int groupNumber, int credits, String condition, int grade,  String period) {
        this.courseID = courseID;
        this.courseName = courseName;
        this.grade = grade;
        this.groupNumber = groupNumber;
        this.period = period;
        this.condition = condition;
        this.credits = credits;
    }

    public void setCourseID (String courseID) {
        this.courseID = courseID;
    }

    public void setCourseName (String courseName) {
        this.courseName = courseName;
    }

    public void setGrade (int grade) {
        this.grade = grade;
    }

    public void setGroupNumber (int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public void setPeriod (String period) {
        this.period = period;
    }

    public void setCondition (String condition) {
        this.condition = condition;
    }

    public void setCredits (int credits) {
        this.credits = credits;
    }

    public String getCourseID() {
        return this.courseID;
    }

    public String getCourseName() {
        return this.courseName;
    }

    public int getGrade() {
        return this.grade;
    }

    public int getGroupNumber() {
        return this.groupNumber;
    }

    public String getPeriod() {
        return this.period;
    }

    public String getCondition() {
        return this.condition;
    }

    public int getCredits() {
        return this.credits;
    }
}