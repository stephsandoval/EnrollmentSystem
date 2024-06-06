package Screens;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.MatriculaController;
import Json.JsonParser;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class MatriculaScreen extends GeneralScreen implements Initializable {

    @FXML
    private ComboBox<String> screenEnrollmentOptions;
    @FXML
    private AnchorPane anchorPane;

    private MatriculaController controller;
    private JsonParser jsonParser = JsonParser.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        screenEnrollmentOptions.getItems().setAll(jsonParser.getEnrollmentOptions());
        controller = new MatriculaController(studentID);
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