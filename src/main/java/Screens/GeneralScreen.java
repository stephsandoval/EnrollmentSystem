package Screens;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import Notifications.Notification;
import Notifications.NotificationFactory;
import Notifications.Status;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class GeneralScreen {

    private final String loginPath = "src/main/java/FXML/LoginScreen.fxml";
    private final String matriculaPath = "src/main/java/FXML/MatriculaScreen.fxml";
    private final String academicHistoryPath = "src/main/java/FXML/AcademicHistoryScreen.fxml";
    private final String inclusionPath = "src/main/java/FXML/InclusionScreen.fxml";
    private final String paymentPath = "src/main/java/FXML/PaymentScreen.fxml";

    private NotificationFactory factory = NotificationFactory.getInstance();
    public static final int studentID = 2023395946;

    private String path;

    private void loadScreen (ActionEvent event) throws IOException {
        File file = new File(path);
        InputStream stream = new FileInputStream(file);
        FXMLLoader loader = new FXMLLoader();
        Parent root = loader.load(stream);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.centerOnScreen();
        stage.show();
    }

    public void loadLoginScreen (ActionEvent event) throws IOException {
        path = loginPath;
        loadScreen(event);
    }

    public void loadMatriculaScreen (ActionEvent event) throws IOException {
        path = matriculaPath;
        loadScreen(event);
    }

    public void loadAcademicHistoryScreen (ActionEvent event) throws IOException {
        path = academicHistoryPath;
        loadScreen(event);
    }

    public void loadInclusionScreen (ActionEvent event) throws IOException {
        path = inclusionPath;
        loadScreen(event);
    }

    public void loadPaymentScreen (ActionEvent event) throws IOException {
        path = paymentPath;
        loadScreen(event);
    }

    public void showNotification (Status status, String message){
        Notification notification = factory.createNotification(status);
        notification.notifyUser(message);
    }
}