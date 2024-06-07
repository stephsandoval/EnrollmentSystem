package Enrollment;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class CourseSelection {
    
    private String courseID, schedule;
    private int groupNumber;

    public CourseSelection (String courseID, int groupNumber, String schedule) {
        this.courseID = courseID;
        this.groupNumber = groupNumber;
        this.schedule = schedule;
    }

    public void setCourseID (String courseID) {
        this.courseID = courseID;
    }

    public void setGroupNumber (int groupNumber) {
        this.groupNumber = groupNumber;
    }

    public void setSchedule (String schedule) {
        this.schedule = schedule;
    }

    public String getCourseID() {
        return this.courseID;
    }

    public int getGroupNumber() {
        return this.groupNumber;
    }

    public String getSchedule() {
        return this.schedule;
    }

    public boolean clashesCourse (CourseSelection otherCourse) {
        boolean clashes = false;
        String thisCourseDays, otherCourseDays;
        LocalTime thisCourseStartHour, thisCourseEndHour, otherCourseStartHour, otherCourseEndHour;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        thisCourseDays = getDays(this.schedule);
        otherCourseDays = getDays(otherCourse.getSchedule());

        thisCourseStartHour = LocalTime.parse(getStartHour(this.schedule), formatter);
        thisCourseEndHour = LocalTime.parse(getEndHour(this.schedule), formatter);
        otherCourseStartHour = LocalTime.parse(getStartHour(otherCourse.getSchedule()), formatter);
        otherCourseEndHour = LocalTime.parse(getEndHour(otherCourse.getSchedule()), formatter);

        if (haveCommonDay(thisCourseDays, otherCourseDays)){
            if (otherCourseStartHour.isAfter(thisCourseStartHour) && otherCourseStartHour.isBefore(thisCourseEndHour)){
                clashes = true;
            }
            if (otherCourseEndHour.isAfter(thisCourseStartHour)){
                clashes = true;
            }
        }
        return clashes;
    }

    private String getDays (String schedule) {
        int index = schedule.indexOf(' ');
        if (index != -1) {
            return schedule.substring(0, index);
        }
        return "";
    }

    private String getStartHour (String schedule) {
        int firstIndex = schedule.indexOf(' ');
        if (firstIndex != -1) {
            int secondIndex = schedule.indexOf('-', firstIndex + 1);
            if (secondIndex != -1) {
                return schedule.substring(firstIndex + 1, secondIndex);
            }
            return schedule.substring(firstIndex + 1);
        }
        return "";
    }
    
    private String getEndHour (String schedule) {
        int firstIndex = schedule.indexOf(' ');
        if (firstIndex != -1) {
            int secondIndex = schedule.indexOf('-', firstIndex + 1);
            if (secondIndex != -1) {
                return schedule.substring(secondIndex + 1);
            }
        }
        return "";
    }

    public boolean haveCommonDay (String firstCourseDays, String secondCourseDays) {
        Set<Character> set1 = new HashSet<>();
        for (char c : firstCourseDays.toCharArray()) {
            set1.add(c);
        }
        for (char c : secondCourseDays.toCharArray()) {
            if (set1.contains(c)) {
                return true;
            }
        }
        return false;
    }
}