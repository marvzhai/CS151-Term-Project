package application.controller;


import javafx.collections.ObservableList;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;

import javafx.scene.control.cell.PropertyValueFactory;
import java.time.LocalDate;

public class AccountsController {

    @FXML
    private TableView<BankAccount> accountsTable;

    @FXML
    private TableColumn<BankAccount, String> accountName;

    //@FXML
    //private TableColumn<BankAccount, String> accountType;

    @FXML
    private TableColumn<BankAccount, String> openingDate;

    @FXML
    private TableColumn<BankAccount, Double> openingBalance;
    

    private AddAccountController accountController = new AddAccountController();
   
    private ObservableList<BankAccount> accountsList = FXCollections.observableArrayList();
    
    @FXML
    public void initialize() {
    	
        
        accountName.setCellValueFactory(new PropertyValueFactory<>("name"));
        //accountTypeColumn.setCellValueFactory(new PropertyValueFactory<>("accountType"));
        openingDate.setCellValueFactory(new PropertyValueFactory<>("openingDate"));
        openingBalance.setCellValueFactory(new PropertyValueFactory<>("balance"));

        loadAccountsFromFile();
        accountsTable.setItems(accountsList);
    }
    
    private void loadAccountsFromFile() {
    	
    	//URL resource = getClass().getResource("/data/accounts.csv");
    //	String filePath = resource.getPath(); 
    	
    	File filePath = new File("data/accounts.csv");

    	
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            System.out.println("test");
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 3) {
                    String name = data[0];
                    System.out.println("Account name is " + name);
                    LocalDate openingDate = LocalDate.parse(data[1]);
                    System.out.print("opening date is " + openingDate);
                    double balance = Double.parseDouble(data[2]);
                    System.out.println("balance is " + balance);
                    
                    BankAccount account = new BankAccount(name, openingDate, balance);
                    accountsList.add(account);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void refreshTable() {
        loadAccountsFromFile();  
    }
    
    public void setAccountController(AddAccountController accountController) {
        this.accountController = accountController;
        if (accountsTable != null) {
            accountsTable.setItems(accountController.getAccountsList());
        }
    }
}
