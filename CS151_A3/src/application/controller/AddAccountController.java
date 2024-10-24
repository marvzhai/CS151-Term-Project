package application.controller;

import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.net.URL;
import application.CommonObjects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class AddAccountController {
	
	@FXML 
	private TextField accountNameField;
	
	@FXML 
	private TextField accountTypeField;
	
	@FXML
	private DatePicker openingDateField;
	
	@FXML 
	private TextField openingBalanceField;
	
	@FXML 
	private Button addButton;
	
	@FXML
	private Label accountNameError;

	@FXML
	private Label accountTypeError;

	@FXML
	private Label openingDateError;

	@FXML
	private Label openingBalanceError;
	
	private ObservableList<BankAccount> accountsList = FXCollections.observableArrayList();
	
	@FXML
    public void initialize() {
       
        openingDateField.setValue(LocalDate.now());
    }
	
	 @FXML
	    public void handleAddAccount() {
		 if(validateFields()) {
			 
		 
	        String accountName = accountNameField.getText();
	        String accountType = accountTypeField.getText();
	        String openingDate = openingDateField.getValue().toString(); // Convert DatePicker to String
	        double openingBalance = Double.parseDouble(openingBalanceField.getText());

	        // Create a new BankAccount object
	        BankAccount newAccount = new BankAccount(accountName, accountType, openingDate, openingBalance);

	        // Add the new account to the list
	        accountsList.add(newAccount);

	        // Clear input fields after adding the account
	        accountNameField.clear();
	        accountTypeField.clear();
	        openingDateField.setValue(null);
	        openingBalanceField.clear();
		 }
	    }
	
	private CommonObjects commonObjects = CommonObjects.getInstance();
	
	@FXML public void AddAccount() {
		URL url = getClass().getClassLoader().getResource("view/Content2.fxml");
		
		try {
			AnchorPane pane2 = (AnchorPane) FXMLLoader.load(url);
			
			HBox mainBox = commonObjects.getMainBox();
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane2);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	private boolean validateFields() {
	    // Clear previous error messages
	    accountNameError.setText("");
	    accountTypeError.setText("");
	    openingDateError.setText("");
	    openingBalanceError.setText("");

	    boolean isValid = true;

	    // Account Name validation
	    if (accountNameField.getText().isEmpty()) {
	        accountNameError.setText("Account Name is required.");
	        isValid = false;
	    }

	    // Account Type validation
	    String accountType = accountTypeField.getText();
	    if (accountType.isEmpty() || (!accountType.equals("Savings") && !accountType.equals("Checking"))) {
	        accountTypeError.setText("Account Type must be 'Savings' or 'Checking'.");
	        isValid = false;
	    }

	    
	    // Opening Date validation
	    if (openingDateField.getValue().isAfter(LocalDate.now()) ) {
	        openingDateError.setText("Opening Date cannot be in the future.");
	        isValid = false;
	    }

	    // Opening Balance validation
	    try {
	        double openingBalance = Double.parseDouble(openingBalanceField.getText());
	        if (openingBalance < 0) {
	            openingBalanceError.setText("Opening Balance cannot be negative.");
	            isValid = false;
	        }
	    } catch (NumberFormatException e) {
	        openingBalanceError.setText("Opening Balance must be a valid number.");
	        isValid = false;
	    }

	    AddAccount();
	    return isValid; // If all fields are valid, return true; otherwise, return false
	}


	  public ObservableList<BankAccount> getAccountsList() {
	        return accountsList;
	    }
	
}

