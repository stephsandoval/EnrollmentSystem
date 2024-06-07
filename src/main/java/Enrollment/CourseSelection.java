package Enrollment;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

public class CourseSelection {
    
    private String courseID, schedule;
    private int groupNumber;
    private boolean selected;

    public CourseSelection (String courseID, int groupNumber, String schedule, boolean selected) {
        this.courseID = courseID;
        this.groupNumber = groupNumber;
        this.schedule = schedule;
        this.selected = selected;
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

    public void setSelected (boolean condition) {
        this.selected = condition;
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

    public boolean isSelected() {
        return this.selected;
    }

    public boolean clashesCourse(CourseSelection otherCourse) {

        if (this.courseID.equals(otherCourse.getCourseID()) && this.groupNumber == otherCourse.getGroupNumber()){
            return false;
        }

        boolean clashes = false;
        String thisCourseDays = getDays(this.schedule);
        String otherCourseDays = getDays(otherCourse.getSchedule());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");

        LocalTime thisCourseStartHour = LocalTime.parse(getStartHour(this.schedule), formatter);
        LocalTime thisCourseEndHour = LocalTime.parse(getEndHour(this.schedule), formatter);
        LocalTime otherCourseStartHour = LocalTime.parse(getStartHour(otherCourse.getSchedule()), formatter);
        LocalTime otherCourseEndHour = LocalTime.parse(getEndHour(otherCourse.getSchedule()), formatter);

        if (haveCommonDay(thisCourseDays, otherCourseDays)) {
            if (otherCourseStartHour.isBefore(thisCourseEndHour) && thisCourseStartHour.isBefore(otherCourseEndHour)) {
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