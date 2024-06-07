package Controllers;

import java.util.ArrayList;
import java.util.Iterator;

import Database.PaymentRepository;
import Database.Result;
import Observers.Observer;
import Observers.Subject;
import Payments.Payment;
import Payments.PaymentList;

public class PaymentController implements Observer, Subject {
    
    private static PaymentController instance;
    private ArrayList<Observer> observers;
    private PaymentRepository repository;

    private PaymentList paymentList;
    private int amountPayments = 0;

    public PaymentController() {
        repository = PaymentRepository.getInstance();
        this.observers = new ArrayList<>();
    }

    public static synchronized PaymentController getInstance() {
        if (instance == null) {
            instance = new PaymentController();
        }
        return instance;
    }

    @Override
    public void update(String message) {
        notifyObservers(message);
    }

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Iterator<Observer> iterator = observers.iterator(); iterator.hasNext(); ){
            Observer observer = iterator.next();
            observer.update(message);
        }
    }

    public void loadPayments (int studentID) {
        Result result = repository.getPendingPayments(studentID);
        ArrayList<Payment> payments = new ArrayList<>();
        if (result.getResultCodes() == 0) {
            for (Object payment : result.getDataset()) {
                payments.add((Payment)(payment));
                amountPayments++;
            }
        }
        this.paymentList = new PaymentList(payments);
        this.paymentList.registerObserver(this);
    }

    public PaymentList getPaymentList() {
        return paymentList;
    }

    public double getIdealHeight() {
        return amountPayments * 30;
    }
}