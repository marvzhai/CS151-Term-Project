package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EnterTransactionsController {

    @FXML private ComboBox<String> accountComboBox;
    @FXML private ComboBox<String> transactionTypeComboBox;
    @FXML private DatePicker transactionDatePicker;
    @FXML private TextField transactionDescriptionField;
    @FXML private TextField paymentAmountField;
    @FXML private TextField depositAmountField;
    @FXML private Label validationLabel;

    @FXML
    public void initialize() {
    	// load data 
        loadAccounts();
        loadTransactionTypes();

        accountComboBox.getSelectionModel().selectFirst();
        transactionTypeComboBox.getSelectionModel().selectFirst();
        transactionDatePicker.setValue(LocalDate.now());
    }

    private void loadAccounts() {
        List<String> accounts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("data/accounts.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] fields = line.split(",");
                if (fields.length > 0) {
                    accounts.add(fields[0]); 
                }
            }
        } catch (IOException e) {
            validationLabel.setText("Error loading accounts.");
            e.printStackTrace();
        }
        accountComboBox.getItems().addAll(accounts);
    }

    private void loadTransactionTypes() {
        List<String> transactionTypes = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("data/transactiontypes.csv"))) {
            String line;
            while ((line = reader.readLine()) != null) {
                transactionTypes.add(line);
            }
        } catch (IOException e) {
            validationLabel.setText("Error loading transaction types.");
            e.printStackTrace();
        }
        transactionTypeComboBox.getItems().addAll(transactionTypes);
    }

    @FXML
    private void saveTransaction() {
        String account = accountComboBox.getValue();
        String transactionType = transactionTypeComboBox.getValue();
        LocalDate transactionDate = transactionDatePicker.getValue();
        String transactionDescription = transactionDescriptionField.getText();
        String paymentAmount = paymentAmountField.getText();
        String depositAmount = depositAmountField.getText();

        if (transactionDescription.isEmpty()) {
            validationLabel.setText("Transaction description is required.");
            return;
        }

        if (paymentAmount.isEmpty() && depositAmount.isEmpty()) {
            validationLabel.setText("Either payment amount or deposit amount is required.");
            return;
        }

        try {
            double payment = paymentAmount.isEmpty() ? 0.0 : Double.parseDouble(paymentAmount);
            double deposit = depositAmount.isEmpty() ? 0.0 : Double.parseDouble(depositAmount);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/transactions.csv", true))) {
                String line = String.format("%s,%s,%s,%s,%.2f,%.2f",
                        account, transactionType, transactionDate, transactionDescription, payment, deposit);
                writer.write(line);
                writer.newLine();
                validationLabel.setText("Transaction saved.");
            } catch (IOException e) {
                validationLabel.setText("Error saving transaction.");
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            validationLabel.setText("Invalid amount values.");
        }
    }
}
