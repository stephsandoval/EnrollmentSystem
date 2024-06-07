package Notifications;

import org.controlsfx.control.Notifications;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class ErrorNotification implements Notification{

    private String imagePath = "file:src/main/java/Images/errorIcon.png";
    private static ErrorNotification instance;

    private ErrorNotification (){}

    public static synchronized ErrorNotification getInstance (){
        if (instance == null){
            instance = new ErrorNotification();
        }
        return instance;
    }

    @Override
    public void notifyUser(String message) {
        Image image = new Image(imagePath);
        Notifications notification = Notifications.create();
        notification.graphic(new ImageView(image));
        notification.text("    " + message);
        notification.title("ERROR");
        notification.hideAfter(Duration.seconds(4));
        notification.hideCloseButton();
        notification.show();
    }
}