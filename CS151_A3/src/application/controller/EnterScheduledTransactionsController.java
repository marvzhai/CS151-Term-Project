package application.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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

public class EnterScheduledTransactionsController {

		@FXML private TextField transactionNameField;
	 	@FXML private ComboBox<String> accountComboBox;
	    @FXML private ComboBox<String> transactionTypeComboBox;
	    @FXML private ComboBox<String> frequencyComboBox;
	    @FXML private TextField dueDateField;
	    @FXML private TextField paymentAmountField;	   
	    @FXML private Label validationLabel;
	    
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
	        	validationLabel.setText("Enter valid day of the month");
	        	return;
	        }
	        
	        if (paymentAmount.isEmpty()) {
	            validationLabel.setText("Payment amount is required.");
	            return;
	        }
	        

	        try {
	            double payment = Double.parseDouble(paymentAmount); // Validate numeric payment amount

	            // Record transaction details in transactions.csv
	            try (BufferedWriter writer = new BufferedWriter(new FileWriter("data/scheduledTransactions.csv", true))) {
	                String transactionRecord = String.format("%s,%s,%s,%s,%s,%.2f",
	                    name, account, transactionType, frequency, dueDate, payment);
	                writer.write(transactionRecord);
	                writer.newLine();
	                validationLabel.setText("Transaction saved and balance updated.");
	            }

	            // Display success message
	            validationLabel.setText("Transaction saved successfully.");
	        } catch (NumberFormatException e) {
	            validationLabel.setText("Invalid payment amount. Please enter a number.");
	        } catch (IOException e) {
	            validationLabel.setText("Error saving transaction.");
	            e.printStackTrace();
	        }
	        
	        URL url = getClass().getClassLoader().getResource("view/Content8.fxml");
	        try {
				AnchorPane pane= (AnchorPane) FXMLLoader.load(url);
				
				HBox mainBox = commonObjects.getMainBox();
				
				if (mainBox.getChildren().size() > 1) {
					mainBox.getChildren().remove(1);
				}
				
				mainBox.getChildren().add(pane);
				mainBox.requestLayout(); // refresh ui line
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
}
