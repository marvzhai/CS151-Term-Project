package application.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import application.CommonObjects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class EditScheduledTransactionController {

		@FXML private TextField transactionNameField;
	 	@FXML private ComboBox<String> accountComboBox;
	    @FXML private ComboBox<String> transactionTypeComboBox;
	    @FXML private ComboBox<String> frequencyComboBox;
	    @FXML private TextField dueDateField;
	    @FXML private TextField paymentAmountField;	   
	    @FXML private Label validationLabel;
	    
	    private ScheduledTransaction transaction;
	    
	    private CommonObjects commonObjects = CommonObjects.getInstance();

	    @FXML
	    public void initialize() {
	    	// load data 
	        loadAccounts();
	        loadTransactionTypes();	     

	        frequencyComboBox.getItems().addAll("Monthly");
	        accountComboBox.getSelectionModel().selectFirst();
	        transactionTypeComboBox.getSelectionModel().selectFirst();
	    }
	    
	    public void setScheduledTransaction(ScheduledTransaction transaction) {
	        this.transaction = transaction;
	        
	        transactionNameField.setText(transaction.getName());
	        accountComboBox.setValue(transaction.getAccountName());
	        transactionTypeComboBox.setValue(transaction.getType());	        
	        dueDateField.setText(transaction.getDueDate());
	        frequencyComboBox.setValue(transaction.getFrequency());
	        paymentAmountField.setText(transaction.getPayment().toString());
	        
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
	        String name = transactionNameField.getText();
	        String account = accountComboBox.getValue();
	        String transactionType = transactionTypeComboBox.getValue();
	        String frequency = frequencyComboBox.getValue();
	        String dueDate = dueDateField.getText();
	        String paymentAmount = paymentAmountField.getText();

	        if (name.isEmpty()) {
	            validationLabel.setText("Transaction name is required.");
	            return;
	        }

	        if (frequency == null) {
	            validationLabel.setText("Frequency is required");
	            return;
	        }

	        if (dueDate.isEmpty()) {
	            validationLabel.setText("Due date is required");
	            return;
	        }

	        if (Integer.parseInt(dueDate) > 31) {
	            validationLabel.setText("Enter a valid day of the month");
	            return;
	        }

	        if (paymentAmount.isEmpty()) {
	            validationLabel.setText("Payment amount is required.");
	            return;
	        }

	        try {
	            double payment = Double.parseDouble(paymentAmount);

	            // Locate and update the existing transaction in scheduledTransactions.csv
	            File transactionsFile = new File("data/scheduledTransactions.csv");
	            File tempFile = new File("data/scheduledTransactions_temp.csv");
	            boolean transactionUpdated = false;

	            try (BufferedReader reader = new BufferedReader(new FileReader(transactionsFile));
	                 BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))) {

	                String line;
	                while ((line = reader.readLine()) != null) {
	                    String[] fields = line.split(",");

	                    if (fields.length == 6) {
	                        String existingName = fields[0];
	                        String existingAccount = fields[1];
	                        String existingType = fields[2];
	                        String existingFrequency = fields[3];
	                        String existingDueDate = fields[4];

	                        // Match the transaction by unique fields (name, account, type, etc.)
	                        if (existingName.equals(transaction.getName()) &&
	                            existingAccount.equals(transaction.getAccountName()) &&
	                            existingType.equals(transaction.getType()) &&
	                            existingFrequency.equals(transaction.getFrequency()) &&
	                            existingDueDate.equals(transaction.getDueDate())) {

	                            // Update the transaction
	                            line = String.format("%s,%s,%s,%s,%s,%.2f",
	                                    name, account, transactionType, frequency, dueDate, payment);
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

	            // Replace the original file with the updated file
	            java.nio.file.Files.move(
	                    tempFile.toPath(),
	                    transactionsFile.toPath(),
	                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
	            );

	            validationLabel.setText("Transaction updated successfully.");

	            // Navigate back to the main page
	            URL url = getClass().getClassLoader().getResource("view/Content8.fxml");
	            AnchorPane pane = FXMLLoader.load(url);
	            HBox mainBox = commonObjects.getMainBox();

	            if (mainBox.getChildren().size() > 1) {
	                mainBox.getChildren().remove(1);
	            }

	            mainBox.getChildren().add(pane);
	            mainBox.requestLayout();
	        } catch (NumberFormatException e) {
	            validationLabel.setText("Invalid payment amount. Please enter a number.");
	        } catch (IOException e) {
	            validationLabel.setText("An error occurred while saving changes.");
	            e.printStackTrace();
	        }
	    }

}
