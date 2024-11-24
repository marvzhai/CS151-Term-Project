package application.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import application.CommonObjects;

public class EditTransactionsController {

    @FXML private ComboBox<String> accountComboBox;
    @FXML private ComboBox<String> transactionTypeComboBox;
    @FXML private DatePicker transactionDatePicker;
    @FXML private TextField transactionDescriptionField;
    @FXML private TextField paymentAmountField;
    @FXML private TextField depositAmountField;
    @FXML private Label validationLabel;
	   
    private Transaction transaction;
    
    private CommonObjects commonObjects = CommonObjects.getInstance();

    @FXML public void initialize() {   
        loadAccounts();
        loadTransactionTypes();
        
        accountComboBox.getSelectionModel().selectFirst();
        transactionTypeComboBox.getSelectionModel().selectFirst();
    }
    
    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
        
        
        accountComboBox.setValue(transaction.getName());
        transactionTypeComboBox.setValue(transaction.getType());
        transactionDatePicker.setValue(transaction.getDate());
        transactionDescriptionField.setText(transaction.getDesc());
        paymentAmountField.setText(transaction.getPayment().toString());
        depositAmountField.setText(transaction.getDeposit().toString());
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
    private void saveChanges() {
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

            // Read transactions and update the matching record
            File transactionsFile = new File("data/transactions.csv");
            File tempFile = new File("data/transactions_temp.csv");

            boolean transactionUpdated = false;

            try (BufferedReader reader = new BufferedReader(new FileReader(transactionsFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(",");

                    if (fields.length == 6) {
                        String existingAccount = fields[0];
                        String existingType = fields[1];
                        LocalDate existingDate = LocalDate.parse(fields[2]);
                        String existingDescription = fields[3];
                   
                        if (existingAccount.equals(transaction.getName()) &&
                            existingType.equals(transaction.getType()) &&
                            existingDate.equals(transaction.getDate()) &&
                            existingDescription.equals(transaction.getDesc())) {

                          
                            line = String.format("%s,%s,%s,%s,%.2f,%.2f",
                                    account, transactionType, transactionDate, transactionDescription, payment, deposit);
                            transactionUpdated = true;
                        }
                    }
                    writer.write(line);
                    writer.newLine();
                }
            }

            if (!transactionUpdated) {
                validationLabel.setText("Transaction not found.");
                tempFile.delete();
                return;
            }

            java.nio.file.Files.move(
                    tempFile.toPath(),
                    transactionsFile.toPath(),
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            validationLabel.setText("Transaction updated successfully.");

            // Navigate back to the main page
            URL url = getClass().getClassLoader().getResource("view/Content6.fxml");
            AnchorPane pane = FXMLLoader.load(url);
            HBox mainBox = commonObjects.getMainBox();

            if (mainBox.getChildren().size() > 1) {
                mainBox.getChildren().remove(1);
            }

            mainBox.getChildren().add(pane);
            mainBox.requestLayout();

        } catch (IOException | NumberFormatException e) {
            validationLabel.setText("An error occurred while saving changes.");
            e.printStackTrace();
        }
    }

}
