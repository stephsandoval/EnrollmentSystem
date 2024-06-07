package Notifications;

import org.controlsfx.control.Notifications;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class PayNotification implements Notification{

    private String imagePath = "file:src/main/java/Images/payIcon.png";
    private static PayNotification instance;

    private PayNotification (){}

    public static synchronized PayNotification getInstance (){
        if (instance == null){
            instance = new PayNotification();
        }
        return instance;
    }

    @Override
    public void notifyUser(String message) {
        Image image = new Image(imagePath);
        Notifications notification = Notifications.create();
        notification.graphic(new ImageView(image));
        notification.text("    " + message);
        notification.title("PAGO EXITOSO");
        notification.hideAfter(Duration.seconds(4));
        notification.hideCloseButton();
        notification.show();
    }
}