package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
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
            
            
            // Read accounts and update balance
            File accountsFile = new File("data/accounts.csv");
            File tempFile = new File("data/accounts_temp.csv");

            boolean accountUpdated = false;

            try (BufferedReader reader = new BufferedReader(new FileReader(accountsFile));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                	System.out.println("test: " + line);
                    String[] accountData = line.split(",");
                    if (accountData.length == 3) {
                    	String accountName = accountData[0];
                        LocalDate openingDate = LocalDate.parse(accountData[1]);
                        double currentBalance = Double.parseDouble(accountData[2]);

                        if (accountName.equals(account)) {
                            // Update balance for the matching account
                            double newBalance = currentBalance - payment;
                            
                            if (newBalance < 0 ) {
                            	validationLabel.setText("Not enough funds in account");
                            	return;
                            }
                            
                            line = accountName + "," + openingDate + "," + newBalance;
                            System.out.println("test: " + line);
                            accountUpdated = true;
                        }
                        writer.write(line);
                        writer.newLine();
                    }
                }

                if (!accountUpdated) {
                    validationLabel.setText("Account not found.");
                } else {
                    // Rename temp file to original file
                    accountsFile.delete();
                    tempFile.renameTo(accountsFile);
                    
                    // save transaction
                    try (BufferedWriter writer2 = new BufferedWriter(new FileWriter("data/transactions.csv", true))) {
                        line = String.format("%s,%s,%s,%s,%.2f,%.2f",
                                account, transactionType, transactionDate, transactionDescription, payment, deposit);
                        writer2.write(line);
                        writer2.newLine();
                        validationLabel.setText("Transaction saved and balance updated.");
                        
                    } catch (IOException e) {
                        validationLabel.setText("Error saving transaction.");
                        e.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
        } catch (NumberFormatException e) {
            validationLabel.setText("Invalid amount values.");
        }
    }
}
