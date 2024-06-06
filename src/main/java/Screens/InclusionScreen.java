package Screens;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.InclusionController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class InclusionScreen extends GeneralScreen implements Initializable {
    
    @FXML
    private AnchorPane anchorPane;

    private InclusionController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = InclusionController.getInstance(studentID);
        setCourseList();
    }

    private void setCourseList() {
        VBox courseList = controller.getCourseList();
        anchorPane.getChildren().add(courseList);
        AnchorPane.setTopAnchor(courseList, 10.0);
        AnchorPane.setBottomAnchor(courseList, 10.0);
        AnchorPane.setLeftAnchor(courseList, 10.0);
        AnchorPane.setRightAnchor(courseList, 10.0);
    }
}