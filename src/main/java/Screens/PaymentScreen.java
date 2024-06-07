package Screens;

import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ResourceBundle;

import Controllers.PaymentController;
import Notifications.Status;
import Observers.MessageObserver;
import Observers.QuiteObserver;
import Observers.QuiteSubject;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class PaymentScreen extends GeneralScreen implements Initializable, MessageObserver, QuiteSubject {
 
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label totalAmount;
    @FXML
    private ImageView background;

    private ArrayList<QuiteObserver> observers;
    private PaymentController controller;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        controller = PaymentController.getInstance(studentID);
        controller.loadPayments();
        controller.registerObserver(this);
        this.observers = new ArrayList<>();
        setPaymentList();
        registerObserver();
        setBackground();
    }

    @Override
    public void update(String message) {
        switch (message.charAt(0)) {
            case '*':
                showNotification(Status.PAYMENT, message.substring(1));
                break;
            case '+':
                showNotification(Status.ERROR, message.substring(1));
                break;
            default:
                float currentAmount = Float.parseFloat(totalAmount.getText());
                float updateAmount = Float.parseFloat(message);
                totalAmount.setText(String.valueOf(currentAmount + updateAmount));
        }
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
        anchorPane.getChildren().clear();
        controller.loadPayments();
        totalAmount.setText("0");
        setPaymentList();
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

    private void setBackground (){
        Image image = new Image("file:src/main/java/Images/background.png");
        background.setImage(image);
    }
}