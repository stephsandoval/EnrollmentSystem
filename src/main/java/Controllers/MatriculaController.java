package Controllers;

import java.util.ArrayList;
import java.util.Iterator;

import Database.MatriculaRepository;
import Database.Result;
import Enrollment.Course;
import Enrollment.CourseList;
import Enrollment.CourseSelectionList;
import Observers.MessageObserver;
import Observers.MessageSubject;

public class MatriculaController implements MessageObserver, MessageSubject {
 
    private static MatriculaController instance;
    private MatriculaRepository repository;
    private ArrayList<MessageObserver> observers;

    private CourseList courseList;
    private int maxGroups = 0;

    private MatriculaController(int studentID) {
        repository = MatriculaRepository.getInstance(studentID);
        observers = new ArrayList<MessageObserver>();
        loadCourses(studentID);
    }

    public static synchronized MatriculaController getInstance(int studentID) {
        if (instance == null) {
            instance = new MatriculaController(studentID);
        }
        return instance;
    }

    @Override
    public void update(String message) {
        notifyObservers(message);
    }

    @Override
    public void registerObserver(MessageObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Iterator<MessageObserver> iterator = observers.iterator(); iterator.hasNext(); ){
            MessageObserver observer = iterator.next();
            observer.update(message);
        }
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
        this.courseList.registerObserver(this);
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
}