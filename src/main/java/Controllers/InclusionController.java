package Controllers;

import java.util.ArrayList;

import Database.InclusionRepository;
import Database.Result;
import Enrollment.Course;
import Enrollment.CourseList;
import Enrollment.CourseSelectionList;

public class InclusionController {
    
    private static InclusionController instance;
    private InclusionRepository repository;

    private CourseList courseList;
    private int maxGroups = 0;

    private InclusionController (int studentID) {
        repository = InclusionRepository.getInstance(studentID);
    }

    public static synchronized InclusionController getInstance(int studentID) {
        if (instance == null) {
            instance = new InclusionController(studentID);
        }
        return instance;
    }

    public void loadCourses (int studentID) {
        Result result = repository.getInclusionCourses(studentID);
        ArrayList<Course> courses = new ArrayList<>();
        int courseIndex = 0;
        if (result.getResultCodes() == 0){
            for (Object course : result.getDataset()) {
                courses.add((Course)(course));
                if (courses.get(courseIndex).getGroups().size() > maxGroups){
                    maxGroups = courses.get(courseIndex).getGroups().size();
                }
            }
        }
        this.courseList = new CourseList(courses);
    }

    public CourseList getCourseList() {
        return this.courseList;
    }

    public double getIdealHeight() {
        int amountElements = maxGroups + courseList.getSize();
        return amountElements * 30;
    }

    public CourseSelectionList getCourseSelection() {
        if (this.courseList == null) {
            return new CourseSelectionList();
        }
        return this.courseList.getCourseSelection();
    }
}