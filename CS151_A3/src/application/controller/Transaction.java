package application.controller;

import java.time.LocalDate;

public class Transaction {
	private String name;
    private String type;
    private LocalDate date;
    private String desc;
    private Double payment;
    private Double deposit;

    public Transaction(String name, String type, LocalDate date, String desc, Double payment, Double deposit) {
        this.name = name;
        this.type = type;
        this.date = date;
        this.desc = desc;
        this.payment = payment;
        this.deposit = deposit;
    }
    
    //getters and setters for all fields

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double payment) {
        this.payment = payment;
    }

    public Double getDeposit() {
        return deposit;
    }

    public void setDeposit(Double deposit) {
        this.deposit = deposit;
    }
    

}
