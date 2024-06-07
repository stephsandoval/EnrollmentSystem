package Observers;

public interface Subject {
    
    public void registerObserver (Observer observer);
    public void notifyObservers (String message);
}