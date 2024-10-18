package application.controller;

import java.io.IOException;
import java.net.URL;

import application.CommonObjects;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class AddAccountController {
	
	private CommonObjects commonObjects = CommonObjects.getInstance();
	
	@FXML public void AddAccount() {
		URL url = getClass().getClassLoader().getResource("view/Content2.fxml");
		
		try {
			AnchorPane pane2 = (AnchorPane) FXMLLoader.load(url);
			
			HBox mainBox = commonObjects.getMainBox();
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane2);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}

