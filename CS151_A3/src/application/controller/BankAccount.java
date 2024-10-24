package application.controller;

public class BankAccount {
    private String accountName;
    private String accountType;
    private String openingDate;
    private double openingBalance;

    public BankAccount(String accountName, String accountType, String openingDate, double openingBalance) {
        this.accountName = accountName;
        this.accountType = accountType;
        this.openingDate = openingDate;
        this.openingBalance = openingBalance;
    }

    public String getAccountName() {
        return accountName;
    }

    public String getAccountType() {
        return accountType;
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }
}
