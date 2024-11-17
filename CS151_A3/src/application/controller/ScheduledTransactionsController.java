package application.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ScheduledTransactionsController {
	@FXML
    private TableView<ScheduledTransaction> scheduledTransactionsTable;

    @FXML
    private TableColumn<ScheduledTransaction, String> transactionName;
    
    @FXML
    private TableColumn<ScheduledTransaction, String> accountName;

    @FXML
    private TableColumn<ScheduledTransaction, String> transactionType;

    @FXML
    private TableColumn<ScheduledTransaction, String> frequencyType;
    
    @FXML
    private TableColumn<ScheduledTransaction, Double> dueDate;
    
    @FXML
    private TableColumn<ScheduledTransaction, Double> paymentAmount;
    
    private ObservableList<ScheduledTransaction> scheduledTransactionsList = FXCollections.observableArrayList();
  
    @FXML
    public void initialize() {
    	
        
        transactionName.setCellValueFactory(new PropertyValueFactory<>("name"));
        accountName.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        transactionType.setCellValueFactory(new PropertyValueFactory<>("type"));
        frequencyType.setCellValueFactory(new PropertyValueFactory<>("frequency"));
        dueDate.setCellValueFactory(new PropertyValueFactory<>("dueDate"));
        paymentAmount.setCellValueFactory(new PropertyValueFactory<>("payment"));       
       
        loadTransactionsFromFile();
        scheduledTransactionsTable.setItems(scheduledTransactionsList);
        refreshTable();
    }
    
    private void loadTransactionsFromFile() {
    	
    	File filePath = new File("data/scheduledTransactions.csv");

    	
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("test");
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    String transactionName = data[0];
                    String accountName = data[1];
                    String transactionType = data[2];
                    String frequencyType = data[3];
                    String dueDate = data[4];                                                                           
                    Double paymentAmount = Double.parseDouble(data[5]);                                                                        
                    ScheduledTransaction transaction = new ScheduledTransaction(transactionName, accountName,transactionType, frequencyType, dueDate, paymentAmount);
                    scheduledTransactionsList.add(transaction);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    private void sortTransactionsByDueDate() {
    	FXCollections.sort(scheduledTransactionsList, (t1, t2) -> {
            int dueDate1 = Integer.parseInt(t1.getDueDate());
            int dueDate2 = Integer.parseInt(t2.getDueDate());
            return Integer.compare(dueDate1, dueDate2); // Sort by integer value
        });
    }
    
    public void refreshTable() {
    	sortTransactionsByDueDate();
        scheduledTransactionsTable.setItems(scheduledTransactionsList);
    }
    
    
}
