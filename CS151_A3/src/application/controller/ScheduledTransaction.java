package application.controller;

public class ScheduledTransaction {
	private String name;
	private String accountName;
    private String type;
    private String frequency;
    private String dueDate;
    private Double payment;
  

    public ScheduledTransaction(String name, String accountName, String type, String frequency, String dueDate, Double payment) {
        this.name = name;
        this.accountName = accountName;   
        this.type = type;
        this.frequency = frequency;
        this.dueDate = dueDate;
        this.payment = payment;

    }
    
    //getters and setters for all fields

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public String getFrequency() {
        return frequency;
    }

    public void setFrequiency(String frequency) {
        this.frequency = frequency;
    }

    public Double getPayment() {
        return payment;
    }

    public void setPayment(Double paymentAmount) {
        this.payment = payment;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }
}
