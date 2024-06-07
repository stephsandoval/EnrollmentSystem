package Screens;

import java.net.URL;
import java.util.ResourceBundle;

import AcademicHistory.Record;
import Controllers.AcademicHistoryController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class AcademicHistoryScreen extends GeneralScreen implements Initializable {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView background;

    private AcademicHistoryController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = AcademicHistoryController.getInstance(studentID);
        setRecordList();
        setBackground();
    }

    private void setRecordList() {
        TableView<Record> recordList = controller.getRecordList();
        recordList.setPrefWidth(654);
        recordList.setPrefHeight(382);
        recordList.setLayoutX(232);
        recordList.setLayoutY(103);
        anchorPane.getChildren().add(recordList);
    }
    
    private void setBackground (){
        Image image = new Image("file:src/main/java/Images/background.png");
        background.setImage(image);
    }
}