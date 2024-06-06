package Enrollment;

import java.util.ArrayList;

public class Course {
    
    private String courseCode, course;
    private ArrayList<Group> groups;

    public Course (String courseCode, String course, ArrayList<Group> groups) {
        this.courseCode = courseCode;
        this.course = course;
        this.groups = groups;
    }

    public void setCourseCode (String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourse (String course) {
        this.course = course;
    }

    public void setGroups (ArrayList<Group> groups) {
        this.groups = groups;
    }

    public String getCourseCode() {
        return this.courseCode;
    }

    public String getCourse() {
        return this.course;
    }

    public ArrayList<Group> getGroups() {
        return this.groups;
    }
}