package Screens;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.MatriculaController;
import Notifications.Status;
import Observers.MessageObserver;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class MatriculaScreen extends GeneralScreen implements Initializable, MessageObserver {

    @FXML
    private Pane anchorPane;
    @FXML
    private ImageView background;

    private MatriculaController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = MatriculaController.getInstance(studentID);
        controller.registerObserver(this);
        setCourseList();
        setBackground();
    }

    @Override
    public void update(String message) {
        System.out.println("in notification");
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

    private void setBackground (){
        Image image = new Image("file:src/main/java/Images/background.png");
        background.setImage(image);
    }
}