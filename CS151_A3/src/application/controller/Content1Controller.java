package application.controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

// HOME PAGE CONTROLLER
public class Content1Controller {
	
	private CommonObjects commonObjects = CommonObjects.getInstance();
	
	
	
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
	
}
