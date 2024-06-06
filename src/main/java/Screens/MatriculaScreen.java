package Screens;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import Controllers.MatriculaController;
import Enrollment.CourseSelection;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.util.Pair;

public class MatriculaScreen extends GeneralScreen implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    private MatriculaController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = MatriculaController.getInstance(studentID);
        setCourseList();
    }
    
    private void setCourseList() {
        VBox courseList = controller.getCourseList();
        anchorPane.setPrefHeight(controller.getIdealHeight());
        anchorPane.getChildren().add(courseList);
        AnchorPane.setTopAnchor(courseList, 10.0);
        AnchorPane.setBottomAnchor(courseList, 10.0);
        AnchorPane.setLeftAnchor(courseList, 10.0);
        AnchorPane.setRightAnchor(courseList, 10.0);
        printCoursesSelected();
    }

    private void printCoursesSelected() {
        System.out.println("here");
        ArrayList<CourseSelection> coursesSelected;
        coursesSelected = controller.getCourseSelection().getSelectedCourses();
        for (CourseSelection course : coursesSelected) {
            System.out.println(course.getCourseID() + " " + course.getGroupNumber());
        }
    }
}