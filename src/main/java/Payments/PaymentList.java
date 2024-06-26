package Payments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Observers.MessageObserver;
import Observers.MessageSubject;
import javafx.geometry.Insets;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

public class PaymentList extends VBox implements MessageSubject {

    private HashMap<Payment, Boolean> payments;
    private ArrayList<MessageObserver> observers;
    private GridPane paymentsGrid;
    private HBox header;

    public PaymentList(ArrayList<Payment> payments) {
        this.observers = new ArrayList<>();
        this.payments = new HashMap<>();
        for (Payment payment : payments){
            this.payments.put(payment, false);
        }
        setHeader();
        createPaymentList();
        this.getChildren().addAll(header, paymentsGrid);
    }

    @Override
    public void registerObserver(MessageObserver observer) {
        observers.add(observer);
    }

    @Override
    public void notifyObservers(String message) {
        for (Iterator<MessageObserver> iterator = observers.iterator(); iterator.hasNext(); ){
            MessageObserver observer = iterator.next();
            observer.update(message);
        }
    }

    public ArrayList<Payment> getSelectedPayments() {
        ArrayList<Payment> selectedPayments = new ArrayList<>();
        for (Payment payment : payments.keySet()){
            if (payments.get(payment)){
                selectedPayments.add(payment);
            }
        }
        return selectedPayments;
    }

    private void setHeader() {
        header = new HBox();
        header.setSpacing(35);
        header.setPadding(new Insets(10));

        Label selectHeader = new Label("ESCOGER  ");
        Label conventionHeader = new Label("CONVENIO                 ");
        Label descriptionHeader = new Label("DESCRIPCIÓN                             ");
        Label periodHeader = new Label("PERIODO  ");
        Label amountHeader = new Label("MONTO");

        header.getChildren().addAll(selectHeader, conventionHeader, descriptionHeader, periodHeader, amountHeader);

        HBox.setHgrow(selectHeader, Priority.ALWAYS);
        HBox.setHgrow(conventionHeader, Priority.ALWAYS);
        HBox.setHgrow(descriptionHeader, Priority.ALWAYS);
        HBox.setHgrow(periodHeader, Priority.ALWAYS);
        HBox.setHgrow(amountHeader, Priority.ALWAYS);
    }

    private void createPaymentList() {
        paymentsGrid = new GridPane();
        paymentsGrid.setHgap(10);
        paymentsGrid.setVgap(10);
        paymentsGrid.setPadding(new Insets(10));

        ColumnConstraints selection = new ColumnConstraints();
        selection.setPercentWidth(15);
        ColumnConstraints convention = new ColumnConstraints();
        convention.setPercentWidth(25);
        ColumnConstraints description = new ColumnConstraints();
        description.setPercentWidth(35);
        ColumnConstraints period = new ColumnConstraints();
        period.setPercentWidth(15);
        ColumnConstraints amount = new ColumnConstraints();
        amount.setPercentWidth(10);
    
        paymentsGrid.getColumnConstraints().addAll(selection, convention, description, period, amount);

        int rowIndex = 0;
        for (Payment payment : payments.keySet()) {
            CheckBox checkBox = new CheckBox();
            checkBox.setOnAction(event -> {
                String message = "";
                if (checkBox.isSelected()) {
                    message = String.valueOf(payment.getTotal());
                    payments.put(payment, true);
                } else {
                    message = "-" + payment.getTotal();
                    payments.put(payment, false);
                }
                notifyObservers(message);
            });
            Label conventionLabel = new Label(payment.getConvention());
            Label descriptionLabel = new Label(payment.getDescription());
            Label periodLabel = new Label(payment.getPeriod());
            Label amountLabel = new Label(String.valueOf(payment.getTotal()));

            paymentsGrid.add(checkBox, 0, rowIndex);
            paymentsGrid.add(conventionLabel, 1, rowIndex);
            paymentsGrid.add(descriptionLabel, 2, rowIndex);
            paymentsGrid.add(periodLabel, 3, rowIndex);
            paymentsGrid.add(amountLabel, 4, rowIndex);

            rowIndex++;
        }
    }
}