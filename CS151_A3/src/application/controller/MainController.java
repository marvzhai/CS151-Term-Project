package application.controller;

import java.io.IOException;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import java.net.URL;
import java.time.LocalDate;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class MainController {

	@FXML HBox mainBox;
	
	//loading content at launch
	@FXML public void initialize() {
		showContent1Op();
	}

	@FXML public void showContent1Op() {
		URL url = getClass().getClassLoader().getResource("view/Content1.fxml");
		
		try {
			AnchorPane pane1 = (AnchorPane) FXMLLoader.load(url);
			
			if (mainBox.getChildren().size() > 1) {
				mainBox.getChildren().remove(1);
			}
			
			mainBox.getChildren().add(pane1);
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	

	@FXML public void showContent2Op() {
		
		URL url = getClass().getClassLoader().getResource("view/Content2.fxml");
		
		try {
			AnchorPane pane2 = (AnchorPane) FXMLLoader.load(url);
			
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
