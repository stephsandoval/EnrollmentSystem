package Screens;

import java.net.URL;
import java.util.ResourceBundle;

import Controllers.PaymentController;
import Observers.Observer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class PaymentScreen extends GeneralScreen implements Initializable, Observer {
 
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label totalAmount;

    private PaymentController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = PaymentController.getInstance();
        controller.loadPayments(studentID);
        controller.registerObserver(this);
        setPaymentList();
    }

    @Override
    public void update(String message) {
        float currentAmount = Float.parseFloat(totalAmount.getText());
        float updateAmount = Float.parseFloat(message);
        totalAmount.setText(String.valueOf(currentAmount + updateAmount));
        System.out.println(message);
    }

    private void setPaymentList() {
        VBox courseList = controller.getPaymentList();
        anchorPane.setPrefHeight(controller.getIdealHeight());
        anchorPane.getChildren().add(courseList);
        AnchorPane.setTopAnchor(courseList, 10.0);
        AnchorPane.setBottomAnchor(courseList, 10.0);
        AnchorPane.setLeftAnchor(courseList, 10.0);
        AnchorPane.setRightAnchor(courseList, 10.0);
    }
}