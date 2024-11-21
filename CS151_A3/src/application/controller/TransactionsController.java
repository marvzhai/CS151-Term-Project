package application.controller;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;

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

    @FXML
    private TextField searchField;

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

        // Add listener for search field
        searchField.textProperty().addListener((observable, oldValue, newValue) -> searchTransactions());
    }

    private void loadTransactionsFromFile() {
        File filePath = new File("data/transactions.csv");

        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    String name = data[0];
                    String transactionType = data[1];
                    LocalDate transactionDate = LocalDate.parse(data[2]);
                    String transactionDescription = data[3];
                    Double paymentAmount = Double.parseDouble(data[4]);
                    Double depositAmount = Double.parseDouble(data[5]);

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

    @FXML
    private void searchTransactions() {
        String searchText = searchField.getText();
        System.out.println("Searching transactions with text: " + searchText); // Debug statement for under the hood
        FilteredList<Transaction> filteredData = new FilteredList<>(transactionsList, transaction -> {
            if (searchText == null || searchText.isEmpty()) {
                return true;
            }
            return transaction.getDesc().toLowerCase().contains(searchText.toLowerCase());
        });
        transactionsTable.setItems(filteredData);
    }

}
