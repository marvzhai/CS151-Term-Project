package application.controller;

import java.io.IOException;
import java.net.URL;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.net.URL;
import application.CommonObjects;

public class MainController {

    @FXML HBox mainBox;

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
            e.printStackTrace();
        }
    }

    @FXML public void goToAddTransactionTypes() {
        URL url = getClass().getClassLoader().getResource("view/Content4.fxml");
        try {
            AnchorPane pane3 = (AnchorPane) FXMLLoader.load(url);
            if (mainBox.getChildren().size() > 1) {
                mainBox.getChildren().remove(1);
            }
            mainBox.getChildren().add(pane3);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML public void goToEnterTransactions() {
        URL url = getClass().getClassLoader().getResource("view/Content5.fxml");
        try {
            AnchorPane pane4 = (AnchorPane) FXMLLoader.load(url);
            if (mainBox.getChildren().size() > 1) {
                mainBox.getChildren().remove(1);
            }
            mainBox.getChildren().add(pane4);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML public void goToTransactions() {
        URL url = getClass().getClassLoader().getResource("view/Content6.fxml");
        try {
            AnchorPane pane5 = (AnchorPane) FXMLLoader.load(url);
            if (mainBox.getChildren().size() > 1) {
                mainBox.getChildren().remove(1);
            }
            mainBox.getChildren().add(pane5);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @FXML public void goToEnterScheduledTransactions() {
    	URL url = getClass().getClassLoader().getResource("view/Content7.fxml");
    	try {
    		AnchorPane pane6 = (AnchorPane) FXMLLoader.load(url);
    		if (mainBox.getChildren().size() > 1) {
    			mainBox.getChildren().remove(1);
    		}
    		mainBox.getChildren().add(pane6);
    	} catch (IOException e) {
    		e.printStackTrace();
    		
    	}
    }
    
    @FXML public void goToScheduledTransactions() {
    	URL url = getClass().getClassLoader().getResource("view/Content8.fxml");
    	try {
    		AnchorPane pane6 = (AnchorPane) FXMLLoader.load(url);
    		if (mainBox.getChildren().size() > 1) {
    			mainBox.getChildren().remove(1);
    		}
    		mainBox.getChildren().add(pane6);
    	} catch (IOException e) {
    		e.printStackTrace();
    		
    	}
    }
    @FXML
    private void goToReportByAccount() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/ReportByAccount.fxml"));
            AnchorPane pane = loader.load();

            HBox mainBox = CommonObjects.getInstance().getMainBox();
            if (mainBox.getChildren().size() > 1) {
                mainBox.getChildren().remove(1);
            }
            mainBox.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void goToReportByTransactionType() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("view/ReportByTransactionType.fxml"));
            AnchorPane pane = loader.load();

            HBox mainBox = CommonObjects.getInstance().getMainBox();
            if (mainBox.getChildren().size() > 1) {
                mainBox.getChildren().remove(1);
            }
            mainBox.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public void goToTransactionDetail() {
        try {
            URL url = getClass().getClassLoader().getResource("view/TransactionDetail.fxml");
            AnchorPane pane = FXMLLoader.load(url);
            if (mainBox.getChildren().size() > 1) {
                mainBox.getChildren().remove(1);
            }
            mainBox.getChildren().add(pane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    
}
