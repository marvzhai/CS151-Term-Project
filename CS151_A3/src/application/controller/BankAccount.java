package application.controller;
import java.time.LocalDate;

public class BankAccount {

    private String name;
    private double balance;
    private LocalDate openingDate;

    public BankAccount(String name, LocalDate openingDate, double balance ) {
        this.name = name;
     
        this.balance = balance;
        this.openingDate = openingDate;
    }

    // Getters and setters for all fields
    public String getName() {
        return name;
    } 

    public void setName(String name) {
        this.name = name;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public String getOpeningDate() {
        return openingDate.toString();
    }

    public void setOpeningDate(LocalDate openingDate) {
        this.openingDate = openingDate;
    }
}
