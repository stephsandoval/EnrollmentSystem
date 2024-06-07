package Notifications;

import java.util.HashMap;

public class NotificationFactory {

    private HashMap<Status, Notification> actionMap;
    private static NotificationFactory instance;

    private NotificationFactory (){
        populateMap();
    }

    public static synchronized NotificationFactory getInstance (){
        if (instance == null){
            instance = new NotificationFactory();
        }
        return instance;
    }
 
    public Notification createNotification (Status status){
        return actionMap.get(status);
    }

    private void populateMap (){
        actionMap = new HashMap<>();
        actionMap.put(Status.ERROR, ErrorNotification.getInstance());
    }
}