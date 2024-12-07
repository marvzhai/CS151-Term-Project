package application.controller;

import javafx.scene.control.Label;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.time.DateTimeException;
import java.time.LocalDate;

import application.CommonObjects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

// HOME PAGE CONTROLLER
public class Content1Controller {
	
	 @FXML VBox homePage;
	
	private CommonObjects commonObjects = CommonObjects.getInstance();
	
	@FXML public void initialize() {
		checkScheduledTransactionsDueToday();
       
    }

	
	@FXML public void gotToContent3Op() {
		URL url = getClass().getClassLoader().getResource("view/Content3.fxml");
		
		try {
			AnchorPane pane3 = (AnchorPane) FXMLLoader.load(url);
			
			HBox mainBox = commonObjects.getMainBox();
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane3);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	  @FXML public void goToEnterTransactions() {
	        URL url = getClass().getClassLoader().getResource("view/Content5.fxml");
	        try {
				AnchorPane pane5 = (AnchorPane) FXMLLoader.load(url);
				
				HBox mainBox = commonObjects.getMainBox();
				
				if (mainBox.getChildren().size() > 1) {
					mainBox.getChildren().remove(1);
				}
				
				mainBox.getChildren().add(pane5);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	  
	  @FXML public void goToEnterScheduledTransactions() {
	    	URL url = getClass().getClassLoader().getResource("view/Content7.fxml");
	    	try {
				AnchorPane pane7 = (AnchorPane) FXMLLoader.load(url);
				
				HBox mainBox = commonObjects.getMainBox();
				
				if (mainBox.getChildren().size() > 1) {
					mainBox.getChildren().remove(1);
				}
				
				mainBox.getChildren().add(pane7);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	  

	  private void addNotificationLabel(String label) {
	      Label notificationLabel = new Label(label);
	      notificationLabel.setStyle(
	    		"-fx-background-color: #d9dde2;" +   
	    		"-fx-text-fill: #2e2e2e;" +
	    		"-fx-padding: 15px;" +
	    		"-fx-font-size: 14px;" +
	    		"-fx-font-family: 'Segoe UI', sans-serif;" +
	    		"-fx-border-radius: 10px;" +
	    		"-fx-background-radius: 10px;" +
	    		"-fx-border-color: #c3c6cb;" +
	    		"-fx-border-width: 1px;" +
	    		"-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 5, 0, 0, 2);"
	      );
	      
	      notificationLabel.setMaxWidth(Double.MAX_VALUE); 
	      notificationLabel.setAlignment(javafx.geometry.Pos.CENTER); 
	      VBox.setMargin(notificationLabel, new Insets(20, 20, 20, 20));
	  
	      homePage.getChildren().add(0, notificationLabel);
	  }

	
	  private void checkScheduledTransactionsDueToday() {
		    try (BufferedReader reader = new BufferedReader(new FileReader("data/scheduledTransactions.csv"))) {
		        LocalDate today = LocalDate.now();
		        boolean scheduledTransactionDueToday = false;
		        String line;

		        while ((line = reader.readLine()) != null) {
		            String[] fields = line.split(",");
		            if (fields.length > 5) {		                    

		                try {
		                   
		                    int dayOfMonth = Integer.parseInt(fields[4]);		              
		                    LocalDate dueDate = LocalDate.of(today.getYear(), today.getMonth(), dayOfMonth);

		                    // check if the the date from csv file is today
		                    if (dueDate.equals(today)) {
		                        scheduledTransactionDueToday = true;
		                        break;
		                    }
		                    
		                } catch (NumberFormatException e) {
		                    System.err.println("Invalid day of month format: " + fields[4]);
		                } catch (DateTimeException e) {
		                    System.err.println("Error constructing LocalDate: " + e.getMessage());
		                }
		            }
		           
		        }
		        
		        if (scheduledTransactionDueToday) {
		        addNotificationLabel("You have scheduled transactions due today!");
		        }
		        else {
		        addNotificationLabel("No scheduled transactions due today!");
		        }
		        
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
	


}
