package Enrollment;

public class CourseSelection {
    
    private String courseID;
    private int groupNumber;

    public CourseSelection (String courseID, int groupNumber) {
        this.courseID = courseID;
        this.groupNumber = groupNumber;
    }

    public void setCourseID (String courseID) {
        this.courseID = courseID;
    }

    public void setGroupNumber (int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public String getCourseID() {
        return this.courseID;
    }

    public int getGroupNumber() {
        return this.groupNumber;
    }
}
