package Controllers;

import java.util.ArrayList;

import Database.MatriculaRepository;
import Database.Result;
import Enrollment.Course;
import Enrollment.CourseList;
import Enrollment.CourseSelectionList;

public class MatriculaController {
 
    private static MatriculaController instance;
    private MatriculaRepository repository;

    private CourseList courseList;
    private int maxGroups = 0;

    private MatriculaController(int studentID) {
        repository = MatriculaRepository.getInstance(studentID);
        loadCourses(studentID);
    }

    public static synchronized MatriculaController getInstance(int studentID) {
        if (instance == null) {
            instance = new MatriculaController(studentID);
        }
        return instance;
    }

    private void loadCourses (int studentID) {
        Result result = repository.getEnrollmentCourses();
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
        return this.courseList.getCourseSelection();
    }

    public CourseSelectionList getCourseDeselection() {
        return this.courseList.getCourseDeselection();
    }
}