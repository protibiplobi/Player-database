package com.example.controllers;

import com.example.helper.NetworkUtil;
import com.example.playerdatabase.Main;
import com.example.serializedclass.Player;
import com.example.serializedclass.SellRequest;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Helper {
    Main application;

    public void setApplication(Main application) {
        this.application = application;
    }

    public HBox createPlayerRow(Player p){
        HBox row = new HBox(10);
        row.setPadding(new Insets(15));
        row.setAlignment(Pos.CENTER_LEFT);  // Align content to the left
        row.setStyle("-fx-background-color: #1e1e1e; -fx-border-radius: 10px; "
                + "-fx-background-radius: 10px; -fx-border-color: #444; -fx-border-width: 1px;");

        VBox playerInfo = new VBox(5);
        Label playerName = new Label(p.getName());
        playerName.setStyle("-fx-text-fill: white; -fx-font-size: 16px; -fx-font-weight: bold;");

        Label playerPosition = new Label(p.getPosition());
        playerPosition.setStyle("-fx-text-fill: lightgray; -fx-font-size: 12px;");

        playerInfo.getChildren().addAll(playerName, playerPosition);
        playerInfo.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(playerInfo, Priority.ALWAYS);

        Button detailsButton = new Button("Details");
        detailsButton.setStyle("-fx-background-color: #444; -fx-text-fill: white; -fx-font-size: 12px;");

        detailsButton.setOnAction(event -> showPlayerDetails(p));

        row.getChildren().addAll(playerInfo, detailsButton);
        return row;
    }

    public void showPlayerDetails(Player p) {
        VBox detailsBox = new VBox(20);
        detailsBox.setPadding(new Insets(30, 30, 30, 30));
        detailsBox.setStyle("-fx-background-color: #1f1f1f; -fx-background-radius: 15px; -fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.3), 15, 0.5, 0, 0);");

        Label titleLabel = new Label(p.getName());
        titleLabel.setStyle("-fx-text-fill: #e6e6e6; -fx-font-size: 26px; -fx-font-weight: bold; -fx-padding: 15 0; -fx-font-family: 'Comic Sans MS', sans-serif;");

        Label positionLabel = new Label("Position: ");
        Label countryLabel = new Label("Country: ");
        Label clubLabel = new Label("Club: ");
        Label ageLabel = new Label("Age: ");
        Label heightLabel = new Label("Height: ");
        Label salaryLabel = new Label("Salary: $");
        Label jerseyLabel = new Label("Jersey: ");

        Label positionValue = new Label(p.getPosition());
        Label countryValue = new Label(p.getCountry());
        Label clubValue = new Label(p.getClub());
        Label ageValue = new Label(String.valueOf(p.getAgeInYears()));
        Label heightValue = new Label(String.valueOf(p.getHeight()));
        Label salaryValue = new Label(String.valueOf(p.getWeeklySalary()));
        Label jerseyValue;
        if(p.getNumber() == -1){
            jerseyValue = new Label("(Empty)");
        }
        else{
            jerseyValue = new Label(String.valueOf(p.getNumber()));
        }

        Button sellButton = new Button("Sell Player");
        Button backButton = new Button("Close");

        sellButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-font-family: 'Segoe UI', sans-serif; -fx-background-radius: 25px; -fx-transition: background-color 0.3s;");
        backButton.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-font-family: 'Segoe UI', sans-serif; -fx-background-radius: 25px; -fx-transition: background-color 0.3s;");

        // Button hover effect
        sellButton.setOnMouseEntered(event -> {
            sellButton.setStyle("-fx-background-color: #ff1a1a; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-font-family: 'Segoe UI', sans-serif; -fx-background-radius: 25px;");
        });
        sellButton.setOnMouseExited(event -> {
            sellButton.setStyle("-fx-background-color: #ff4d4d; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-font-family: 'Segoe UI', sans-serif; -fx-background-radius: 25px;");
        });

        backButton.setOnMouseEntered(event -> {
            backButton.setStyle("-fx-background-color: #0078d4; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-font-family: 'Segoe UI', sans-serif; -fx-background-radius: 25px;");
        });
        backButton.setOnMouseExited(event -> {
            backButton.setStyle("-fx-background-color: #4a90e2; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10px 20px; -fx-font-family: 'Segoe UI', sans-serif; -fx-background-radius: 25px;");
        });

        // Handle button actions
        sellButton.setOnAction(event -> {
            SellRequest request = new SellRequest(p);
            try {
                application.network.write(request);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Selling player: " + p.getName());
        });

        backButton.setOnAction(event -> {
            // Close the player details window
            Stage stage = (Stage) backButton.getScene().getWindow();
            stage.close();
        });

        // Add labels, values, and buttons to the VBox
        detailsBox.getChildren().addAll(
                titleLabel,
                createDetailRow(positionLabel, positionValue),
                createDetailRow(countryLabel, countryValue),
                createDetailRow(clubLabel, clubValue),
                createDetailRow(jerseyLabel, jerseyValue),
                createDetailRow(ageLabel, ageValue),
                createDetailRow(heightLabel, heightValue),
                createDetailRow(salaryLabel, salaryValue),
                sellButton, backButton
        );

        Scene detailsScene = new Scene(detailsBox, 450, 500);
        Stage detailsStage = new Stage();
        detailsStage.setTitle(p.getName() + " - Player Details");
        detailsStage.setScene(detailsScene);
        detailsStage.show();
    }

    // Helper method to create a row with label and value on the same line
    private HBox createDetailRow(Label label, Label value) {
        HBox row = new HBox(1);
        row.setAlignment(Pos.CENTER_LEFT);
        label.setStyle("-fx-text-fill: #ffffff; -fx-font-size: 16px; -fx-font-family: 'Georgia', sans-serif;");
        value.setStyle("-fx-text-fill: #e6e6e6; -fx-font-size: 16px; -fx-font-family: 'Trebuchet MS', sans-serif;");
        row.getChildren().addAll(label, value);
        return row;
    }
}
