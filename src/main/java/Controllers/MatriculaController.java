package Controllers;

import java.util.ArrayList;

import Database.MatriculaRepository;
import Database.Result;
import Enrollment.Course;
import Enrollment.CourseList;

public class MatriculaController {
 
    private static MatriculaController instance;
    private MatriculaRepository repository;

    private CourseList courseList;
    private int maxGroups = 0;

    private MatriculaController() {
        repository = MatriculaRepository.getInstance();
    }

    public static synchronized MatriculaController getInstance() {
        if (instance == null) {
            instance = new MatriculaController();
        }
        return instance;
    }

    public void loadCourses (int studentID) {
        Result result = repository.getEnrollmentCourses(studentID);
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
}