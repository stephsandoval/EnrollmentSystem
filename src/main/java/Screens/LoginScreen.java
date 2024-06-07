package Screens;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Database.AcademicHistoryRepository;
import Database.MatriculaRepository;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class LoginScreen extends GeneralScreen implements Initializable {
    
    @FXML
    private Button loginButton;
    @FXML
    private ImageView background;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setBackground();
        loginButton.setOnAction(event -> {
            loadFirstInformation(event);
        });
    }

    public void loadFirstInformation (ActionEvent event){
        AcademicHistoryRepository.getInstance(studentID);
        MatriculaRepository.getInstance(studentID);
        try {
            loadMatriculaScreen(event);
        } catch (IOException exception) {}
    }

    private void setBackground (){
        Image image = new Image("file:src/main/java/Images/background-login.png");
        background.setImage(image);
    }
}