package Screens;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;

public class GeneralScreen {

    private final String loginPath = "src/main/java/FXML/LoginScreen.fxml";
    private final String matriculaPath = "src/main/java/FXML/MatriculaScreen.fxml";
    protected int studentID = 2023395946;

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

    public void loadMatriculaScreen (ActionEvent event) throws IOException {
        path = matriculaPath;
        loadScreen(event);
    }

    public void loadLoginScreen (ActionEvent event) throws IOException {
        path = loginPath;
        loadScreen(event);
    }
}