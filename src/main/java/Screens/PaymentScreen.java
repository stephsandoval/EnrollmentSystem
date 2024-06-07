package Screens;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import Controllers.PaymentController;
import Observers.MessageObserver;
import Observers.QuiteObserver;
import Observers.QuiteSubject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class PaymentScreen extends GeneralScreen implements Initializable, MessageObserver, QuiteSubject {
 
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label totalAmount;

    private ArrayList<QuiteObserver> observers;
    private PaymentController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = PaymentController.getInstance();
        controller.loadPayments(studentID);
        controller.registerObserver(this);
        this.observers = new ArrayList<>();
        setPaymentList();
        registerObserver();
    }

    @Override
    public void update(String message) {
        float currentAmount = Float.parseFloat(totalAmount.getText());
        float updateAmount = Float.parseFloat(message);
        totalAmount.setText(String.valueOf(currentAmount + updateAmount));
        System.out.println(message);
    }

    @Override
    public void registerObserver() {
        observers.add(this.controller);
    }

    @Override
    public void notifyObservers() {
        for (Iterator<QuiteObserver> iterator = observers.iterator(); iterator.hasNext(); ){
            QuiteObserver observer = iterator.next();
            observer.update();
        }
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