package application.controller;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import javafx.fxml.FXML;


public class TransactionsController {
	
	@FXML
    private TableView<Transaction> transactionsTable;

    @FXML
    private TableColumn<Transaction, String> accountName;

    @FXML
    private TableColumn<Transaction, String> transactionType;

    @FXML
    private TableColumn<Transaction, String> transactionDate;

    @FXML
    private TableColumn<Transaction, String> transactionDescription;
    
    @FXML
    private TableColumn<Transaction, Double> paymentAmount;
    
    @FXML
    private TableColumn<Transaction, Double> depositAmount;
    
    private ObservableList<Transaction> transactionsList = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() {
    	
        
        accountName.setCellValueFactory(new PropertyValueFactory<>("name"));
        transactionType.setCellValueFactory(new PropertyValueFactory<>("type"));
        transactionDate.setCellValueFactory(new PropertyValueFactory<>("date"));
        transactionDescription.setCellValueFactory(new PropertyValueFactory<>("desc"));
        paymentAmount.setCellValueFactory(new PropertyValueFactory<>("payment"));
        depositAmount.setCellValueFactory(new PropertyValueFactory<>("deposit"));
        
        loadTransactionsFromFile();
        transactionsTable.setItems(transactionsList);
    }
    
    private void loadTransactionsFromFile() {
    	
    	File filePath = new File("data/transactions.csv");

    	
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("test");
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    String name = data[0];
                    System.out.println("Account name is " + name);
                    String transactionType = data[1];
                    System.out.println("Transaction type is " + transactionType);
                    LocalDate transactionDate = LocalDate.parse(data[2]);
                    System.out.print("Transaction date is " + transactionDate);
                    String transactionDescription = data[3];
                    System.out.println("Transaction description is " + transactionDescription);
                    Double paymentAmount = Double.parseDouble(data[4]);
                    System.out.println("Payment amount is " + paymentAmount);
                    Double depositAmount = Double.parseDouble(data[5]);
                    System.out.println("Deposit amount is " + depositAmount);
                    
                    Transaction transaction = new Transaction(name, transactionType, transactionDate, transactionDescription, paymentAmount, depositAmount);
                    transactionsList.add(transaction);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void refreshTable() {
    	loadTransactionsFromFile();
    }
    
  
	

}
