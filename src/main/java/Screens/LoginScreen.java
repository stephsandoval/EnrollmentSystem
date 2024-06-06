package Screens;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

public class LoginScreen extends GeneralScreen implements Initializable {
    
    @FXML
    private Button loginButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        //setBackground();
        loginButton.setOnAction(event -> {
            loadFirstInformation(event);
        });
    }

    public void loadFirstInformation (ActionEvent event){
        try {
            loadMatriculaScreen(event);
        } catch (IOException exception) {}
    }
}