package Controllers;

import java.util.ArrayList;
import java.util.Arrays;

import Enrollment.Course;
import Enrollment.CourseList;
import Enrollment.Group;

public class MatriculaController {
 
    private ArrayList<Course> courses;
    private CourseList courseList;
    private int studentID;

    public MatriculaController(int studentID) {
        this.studentID = studentID;
        courses = getCourses();
        courseList = new CourseList(courses);
    }

    private ArrayList<Course> getCourses() {
        ArrayList<Course> courses = new ArrayList<>();
        courses.add(new Course("CI1107", "Comunicación Oral", new ArrayList<>(Arrays.asList(
                new Group("Cartago", 1, "L 09:30-12:20", "Amador Solano Gabriela", 30, "Regular"),
                new Group("Cartago", 2, "K 09:30-12:20", "Amador Solano Gabriela", 30, "Regular"),
                new Group("Cartago", 3, "V 13:00-15:50", "Romero Álvarez Ericka", 30, "Regular")
        ))));
        courses.add(new Course("CI1231", "Inglés II", new ArrayList<>(Arrays.asList(
                new Group("Cartago", 1, "M 11:00-12:20", "Smith Johnson", 25, "Regular")
        ))));
        courses.add(new Course("IC3002", "Análisis de Algoritmos", new ArrayList<>(Arrays.asList(
                new Group("Cartago", 2, "J 13:00-15:50", "Doe Jane", 20, "Regular")
        ))));
        return courses;
    }

    public CourseList getCourseList() {
        return this.courseList;
    }
}