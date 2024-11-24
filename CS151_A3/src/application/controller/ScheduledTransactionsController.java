package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

import application.CommonObjects;

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
    private TableColumn<ScheduledTransaction, String> dueDate;

    @FXML
    private TableColumn<ScheduledTransaction, Double> paymentAmount;

    @FXML
    private TextField searchField; 

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

        // Add listener for search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchScheduledTransactions());
        refreshTable();
        
        scheduledTransactionsTable.setRowFactory(tv -> {
            TableRow<ScheduledTransaction> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    ScheduledTransaction selectedTransaction = row.getItem();
                    openEditScheduledTransactionsPage(selectedTransaction);
                }
            });
            return row;
        });
    }

    private void loadTransactionsFromFile() {
        File filePath = new File("data/scheduledTransactions.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    String transactionName = data[0];
                    String accountName = data[1];
                    String transactionType = data[2];
                    String frequencyType = data[3];
                    String dueDate = data[4];
                    Double paymentAmount = Double.parseDouble(data[5]);
                    ScheduledTransaction transaction = new ScheduledTransaction(transactionName, accountName, transactionType, frequencyType, dueDate, paymentAmount);
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

    @FXML
    private void searchScheduledTransactions() {
        String searchText = searchField.getText();
        System.out.println("Searching scheduled transactions with text: " + searchText); // Debug statement for under the hood
        FilteredList<ScheduledTransaction> filteredData = new FilteredList<>(scheduledTransactionsList, scheduledTransaction -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return scheduledTransaction.getName().toLowerCase().contains(searchText.toLowerCase());
        });
        scheduledTransactionsTable.setItems(filteredData);
    }
    
    private CommonObjects commonObjects = CommonObjects.getInstance();
    
    @FXML 
    public void openEditScheduledTransactionsPage(ScheduledTransaction transaction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/Content10.fxml"));
            AnchorPane pane6 = loader.load();

            EditScheduledTransactionController controller = loader.getController();
            controller.setScheduledTransaction(transaction);
            
            HBox mainBox = commonObjects.getMainBox();

            if (mainBox.getChildren().size() > 1) {
                mainBox.getChildren().remove(1);
            }
            mainBox.getChildren().add(pane6);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
