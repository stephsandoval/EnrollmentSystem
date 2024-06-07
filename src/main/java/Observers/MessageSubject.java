package Observers;

public interface MessageSubject {
    
    public void registerObserver (MessageObserver observer);
    public void notifyObservers (String message);
}