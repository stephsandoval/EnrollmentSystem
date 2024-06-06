package Enrollment;

import java.util.ArrayList;

public class Course {
    
    private String courseCode, course;
    private ArrayList<Group> groups;

    public Course (String courseCode, String course, ArrayList<Object> groups) {
        this.courseCode = courseCode;
        this.course = course;
        this.groups = loadGroups(groups);
    }

    private ArrayList<Group> loadGroups (ArrayList<Object> groups) {
        ArrayList<Group> groupList = new ArrayList<>();
        for (Object group : groups) {
            groupList.add((Group) group);
        }
        return groupList;
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