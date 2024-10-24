package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class AccountsController {

    @FXML
    private TableView<BankAccount> accountsTable;

    @FXML
    private TableColumn<BankAccount, String> accountNameColumn;

    @FXML
    private TableColumn<BankAccount, String> accountTypeColumn;

    @FXML
    private TableColumn<BankAccount, String> openingDateColumn;

    @FXML
    private TableColumn<BankAccount, Double> openingBalanceColumn;

    private AddAccountController accountController;

    public AccountsController(AddAccountController accountController) {
        this.accountController = accountController;
    }

    @FXML
    public void initialize() {
        // Set up the columns in the table
        accountNameColumn.setCellValueFactory(new PropertyValueFactory<>("accountName"));
        accountTypeColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        openingDateColumn.setCellValueFactory(new PropertyValueFactory<>("openingDate"));
        openingBalanceColumn.setCellValueFactory(new PropertyValueFactory<>("openingBalance"));

        // Load data into the table
        accountsTable.setItems(accountController.getAccountsList());
    }
}
