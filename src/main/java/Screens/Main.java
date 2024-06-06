package Screens;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

import Enrollment.Course;
import Enrollment.CourseList;
import Enrollment.Group;

public class Main extends Application {

    @Override
    public void start(Stage stage) {
        AnchorPane root = new AnchorPane();
        VBox courseList;

        // Example ArrayList of courses
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

        courseList = new CourseList(courses);

        // Add the Accordion to the root AnchorPane
        root.getChildren().add(courseList);
        AnchorPane.setTopAnchor(courseList, 10.0);
        AnchorPane.setBottomAnchor(courseList, 10.0);
        AnchorPane.setLeftAnchor(courseList, 10.0);
        AnchorPane.setRightAnchor(courseList, 10.0);

        // Set up the scene and stage
        Scene scene = new Scene(root, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Dynamic Accordion Example");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}