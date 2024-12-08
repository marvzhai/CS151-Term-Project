package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.Node;
import javafx.scene.layout.HBox;
import application.controller.Transaction;
import application.CommonObjects;

public class TransactionDetailController {

    @FXML private Label accountLabel;
    @FXML private Label typeLabel;
    @FXML private Label dateLabel;
    @FXML private Label descLabel;
    @FXML private Label paymentLabel;
    @FXML private Label depositLabel;

    private Node previousContent;
    private Transaction transaction;

    public void setTransaction(Transaction t) {
        this.transaction = t;
        accountLabel.setText("Account: " + t.getName());
        typeLabel.setText("Type: " + t.getType());
        dateLabel.setText("Date: " + t.getDate());
        descLabel.setText("Description: " + t.getDesc());
        paymentLabel.setText("Payment: " + t.getPayment());
        depositLabel.setText("Deposit: " + t.getDeposit());
    }

    public void setPreviousContent(Node node) {
        this.previousContent = node;
    }

    @FXML
    private void goBack() {
        HBox mainBox = CommonObjects.getInstance().getMainBox();
        if (mainBox.getChildren().size() > 1) {
            mainBox.getChildren().remove(1);
        }
        if (previousContent != null) {
            mainBox.getChildren().add(previousContent);
        }
    }
}
