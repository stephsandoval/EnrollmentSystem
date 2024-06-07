package Controllers;

import java.util.ArrayList;
import java.util.Iterator;

import Database.PaymentRepository;
import Database.Result;
import Observers.MessageObserver;
import Observers.MessageSubject;
import Observers.QuiteObserver;
import Payments.Payment;
import Payments.PaymentList;

public class PaymentController implements MessageObserver, MessageSubject, QuiteObserver {
    
    private static PaymentController instance;
    private ArrayList<MessageObserver> observers;
    private PaymentRepository repository;
    private int studentID;

    private PaymentList paymentList;
    private int amountPayments = 0;

    public PaymentController(int studentID) {
        repository = PaymentRepository.getInstance();
        this.observers = new ArrayList<>();
        this.studentID = studentID;
    }

    public static synchronized PaymentController getInstance(int studentID) {
        if (instance == null) {
            instance = new PaymentController(studentID);
        }
        return instance;
    }

    @Override
    public void update(String message) {
        notifyObservers(message);
    }

    @Override
    public void registerObserver(MessageObserver observer) {
        for (MessageObserver existingObserver : observers) {
            if (existingObserver.getClass() == observer.getClass()) {
                observers.remove(observers.indexOf(existingObserver));
                break;
            }
        }
        observers.add(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Iterator<MessageObserver> iterator = observers.iterator(); iterator.hasNext(); ){
            MessageObserver observer = iterator.next();
            observer.update(message);
        }
    }

    @Override
    public void update() {
        ArrayList<Payment> selectedPayments = this.paymentList.getSelectedPayments();
        for (Payment payment : selectedPayments) {
            int result = repository.updatePayment(studentID, payment);
            if (result == 0){
                notifyObservers("*Pago exitoso por la suma de ₡" + payment.getTotal());
            } else {
                notifyObservers("+Error al realizar el pago");
            }
        }
    }

    public void loadPayments() {
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