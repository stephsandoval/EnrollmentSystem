package Screens;

import java.net.URL;
import java.util.ResourceBundle;

import AcademicHistory.Record;
import Controllers.AcademicHistoryController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;

public class AcademicHistoryScreen extends GeneralScreen implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    private AcademicHistoryController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = AcademicHistoryController.getInstance(studentID);
        setRecordList();
    }

    private void setRecordList() {
        TableView<Record> recordList = controller.getRecordList();
        anchorPane.getChildren().add(recordList);
        AnchorPane.setTopAnchor(recordList, 10.0);
        AnchorPane.setBottomAnchor(recordList, 10.0);
        AnchorPane.setLeftAnchor(recordList, 10.0);
        AnchorPane.setRightAnchor(recordList, 10.0);
    }
    
}