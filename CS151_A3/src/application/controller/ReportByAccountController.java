package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import application.CommonObjects;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class ReportByAccountController {

    @FXML private ComboBox<String> accountComboBox;
    @FXML private TableView<Transaction> transactionTable;
    @FXML private TableColumn<Transaction, LocalDate> dateColumn;
    @FXML private TableColumn<Transaction, String> typeColumn;
    @FXML private TableColumn<Transaction, String> descColumn;
    @FXML private TableColumn<Transaction, Double> paymentColumn;
    @FXML private TableColumn<Transaction, Double> depositColumn;

    private ObservableList<Transaction> allTransactions = FXCollections.observableArrayList();
    private ObservableList<String> accounts = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        descColumn.setCellValueFactory(new PropertyValueFactory<>("desc"));
        paymentColumn.setCellValueFactory(new PropertyValueFactory<>("payment"));
        depositColumn.setCellValueFactory(new PropertyValueFactory<>("deposit"));

        loadData();
        accountComboBox.setItems(accounts);
        accountComboBox.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                filterByAccount(newVal);
            }
        });

        transactionTable.setRowFactory(tv -> {
            TableRow<Transaction> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && !row.isEmpty()) {
                    Transaction selected = row.getItem();
                    openDetail(selected);
                }
            });
            return row;
        });
    }

    private void loadData() {
        File filePath = new File("data/transactions.csv");
        Set<String> accountSet = new HashSet<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 6) {
                    String name = data[0];
                    String type = data[1];
                    LocalDate date = LocalDate.parse(data[2]);
                    String description = data[3];
                    double payment = Double.parseDouble(data[4]);
                    double deposit = Double.parseDouble(data[5]);
                    Transaction tr = new Transaction(name, type, date, description, payment, deposit);
                    allTransactions.add(tr);
                    accountSet.add(name);
                }
            }
            accounts.addAll(accountSet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void filterByAccount(String account) {
        ObservableList<Transaction> filtered = FXCollections.observableArrayList();
        for (Transaction t : allTransactions) {
            if (t.getName().equals(account)) {
                filtered.add(t);
            }
        }
        filtered.sort((t1, t2) -> t2.getDate().compareTo(t1.getDate()));
        transactionTable.setItems(filtered);
    }

    private void openDetail(Transaction transaction) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/TransactionDetail.fxml"));
            AnchorPane detailPane = loader.load();
            TransactionDetailController controller = loader.getController();
            controller.setTransaction(transaction);

            HBox mainBox = CommonObjects.getInstance().getMainBox();
            Node oldContent = null;
            if (mainBox.getChildren().size() > 1) {
                oldContent = mainBox.getChildren().get(1);
                mainBox.getChildren().remove(1);
            }
            controller.setPreviousContent(oldContent);
            mainBox.getChildren().add(detailPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
