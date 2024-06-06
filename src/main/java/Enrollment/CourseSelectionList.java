package Enrollment;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseSelectionList {
 
    private HashMap<String, CourseSelection> courseSelection;

    public CourseSelectionList() {
        courseSelection = new HashMap<>();
    }

    public void addCourseSelection (CourseSelection selection) {
        if (courseSelection.containsKey(selection.getCourseID())){
            courseSelection.replace(selection.getCourseID(), selection);
        }
        courseSelection.put(selection.getCourseID(), selection);
    }

    public ArrayList<CourseSelection> getSelectedCourses() {
        ArrayList<CourseSelection> selectedCourses = new ArrayList<>();
        for (CourseSelection course : courseSelection.values()) {
            selectedCourses.add(course);
        }
        return selectedCourses;
    }
}