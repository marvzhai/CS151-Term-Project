package application.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class AddTransactionTypeController {

    @FXML private TextField transactionTypeField;
    @FXML private Label validationLabel;

    private static final String DATA_FOLDER = "data";
    private static final String FILE_NAME = "transactiontypes.csv";

    @FXML
    private void saveTransactionType() {
        String transactionType = transactionTypeField.getText();
        
        if (transactionType.isEmpty()) {
        	// display error for being left empty
            validationLabel.setText("Transaction type is required.");
            return;
        }

        // transaction type save
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(Paths.get(DATA_FOLDER, FILE_NAME).toString(), true))) {
            writer.write(transactionType);
            writer.newLine();
            validationLabel.setText("Transaction type saved.");
        } catch (IOException e) {
            validationLabel.setText("Error saving transaction type.");
            e.printStackTrace();
        }
    }
}
