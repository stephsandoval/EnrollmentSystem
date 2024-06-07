package Screens;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.MatriculaController;
import Notifications.Status;
import Observers.Observer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MatriculaScreen extends GeneralScreen implements Initializable, Observer {

    @FXML
    private AnchorPane anchorPane;

    private MatriculaController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = MatriculaController.getInstance(studentID);
        controller.registerObserver(this);
        setCourseList();
    }

    @Override
    public void update(String message) {
        showNotification(Status.ERROR, message);
    }
    
    private void setCourseList() {
        VBox courseList = controller.getCourseList();
        anchorPane.setPrefHeight(controller.getIdealHeight());
        anchorPane.getChildren().add(courseList);
        AnchorPane.setTopAnchor(courseList, 10.0);
        AnchorPane.setBottomAnchor(courseList, 10.0);
        AnchorPane.setLeftAnchor(courseList, 10.0);
        AnchorPane.setRightAnchor(courseList, 10.0);
    }
}