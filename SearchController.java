package com.example.controllers;

import com.example.helper.*;
import com.example.serializedclass.*;
import com.example.playerdatabase.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class SearchController {
    public Button myPlayers;
    public Label teamname;
    public TextField nameBox;
    public TextField ageBox;
    public TextField heightBox;
    public TextField countryBox;
    public TextField jerseyBox;
    public Button resetButton;
    public ChoiceBox<String> positionBox;
    public Button searchButton;
    public TextField salaryupperBox;
    public TextField salarylowerBox;
    Main application;
    String username;

    ArrayList<Player> playerList;

    public void setPlayerList(ArrayList<Player> playerList) {
        this.playerList = playerList;
    }

    public void setApplication(Main application) {
        this.application = application;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public void myPlayersPressed(ActionEvent actionEvent) throws IOException {
        application.showHome();
    }

    public void setDropdown(){
        positionBox.getItems().addAll("Any","Batsman","Bowler","Allrounder");
        positionBox.setStyle("-fx-font-size: 18px;");
    }


    public void resetPressed(ActionEvent actionEvent) {
        nameBox.setText("");
        ageBox.setText("");
        salarylowerBox.setText("");
        salaryupperBox.setText("");
        heightBox.setText("");
        countryBox.setText("");
        jerseyBox.setText("");
        positionBox.setValue(null);
    }

    public void searchPressed(ActionEvent actionEvent) {

        int searchAge = 0,searchJersey = 0, searchSalaryUpper = 0, searchSalaryLower = 0;
        double searchHeight = 0;

        if(!ageBox.getText().isEmpty()){
            try{
                searchAge = Integer.parseInt(ageBox.getText());
            } catch (NumberFormatException e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Invalid Input!");
                error.setContentText("Please enter an integer in the age field");
                error.showAndWait();
                return;
            }
        }

        if(!jerseyBox.getText().isEmpty()){
            try{
                searchJersey = Integer.parseInt(jerseyBox.getText());
            } catch (NumberFormatException e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Invalid Input!");
                error.setContentText("Please enter an integer value in the jersey field");
                error.showAndWait();
                return;
            }
        }

        if(!heightBox.getText().isEmpty()){
            try{
                searchHeight = Double.parseDouble(heightBox.getText());
            } catch (NumberFormatException e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Invalid Input!");
                error.setContentText("Please enter a double value in the height field");
                error.showAndWait();
                return;
            }
        }

        if(!salaryupperBox.getText().isEmpty()){
            try{
                searchSalaryUpper = Integer.parseInt(salaryupperBox.getText());
            } catch (NumberFormatException e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Invalid Input!");
                error.setContentText("Please enter an integer value in the salary upper bound field");
                error.showAndWait();
                return;
            }
        }

        if(!salarylowerBox.getText().isEmpty()) {
            try {
                searchSalaryLower = Integer.parseInt(salarylowerBox.getText());
            } catch (NumberFormatException e) {
                Alert error = new Alert(Alert.AlertType.ERROR);
                error.setHeaderText("Invalid Input!");
                error.setContentText("Please enter an integer value in the salary lower bound field");
                error.showAndWait();
                return;
            }
        }


        ArrayList<Player> result = search(searchAge,searchJersey,searchHeight, searchSalaryLower,searchSalaryUpper);
        showSearchResult(result);
    }



    ArrayList<Player> search(int searchAge, int searchJersey, double searchHeight, int searchSalaryLower, int searchSalaryUpper){
        ArrayList<Player> result = new ArrayList<>();
        for(Player p:playerList){
            boolean flag = true;
            if (!countryBox.getText().isEmpty() && !p.getCountry().equalsIgnoreCase(countryBox.getText())) {
                flag = false;
            }
            else if(!nameBox.getText().isEmpty() && !p.getName().equalsIgnoreCase(nameBox.getText())){
                flag = false;
            }
            else if(positionBox.getValue() != null && !positionBox.getValue().equalsIgnoreCase("ANY") && !p.getPosition().equalsIgnoreCase(positionBox.getValue())){
                flag = false;
            }
            else if(!ageBox.getText().isEmpty() && p.getAgeInYears() != searchAge){
                flag = false;
            }
            else if(!heightBox.getText().isEmpty() && p.getHeight() != searchHeight){
                flag = false;
            }
            else if(!jerseyBox.getText().isEmpty() && p.getNumber() != searchJersey){
                flag = false;
            }
            else if(!salaryupperBox.getText().isEmpty() && p.getWeeklySalary() >= searchSalaryUpper){
                flag = false;
            }
            else if(!salarylowerBox.getText().isEmpty() && p.getWeeklySalary() <= searchSalaryLower){
                flag = false;
            }
            if(flag){
                result.add(p);
            }
        }
        return result;
    }

    public void showSearchResult(ArrayList<Player> list){
        if(list.isEmpty()){
            Alert error = new Alert(Alert.AlertType.ERROR);
            error.setHeaderText("No player Found!");
            error.setContentText("No player with your desired search values.");
            error.showAndWait();
            return;
        }

        ScrollPane scrollpane = new ScrollPane();

        VBox box = new VBox();
        box.setPrefWidth(495);
        box.setPrefHeight(600);

        scrollpane.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
        box.setStyle("-fx-background-color: #1e1e1e;");
        box.setPadding(new Insets(20));
        box.setSpacing(15);

        Helper helper = new Helper();
        helper.setApplication(application);

        for (Player player : list) {
            box.getChildren().add(helper.createPlayerRow(player));
        }
        scrollpane.setContent(box);

        Scene resultScene = new Scene(scrollpane, 500, 600);
        Stage detailsStage = new Stage();
        detailsStage.setTitle("Search Results");
        detailsStage.setScene(resultScene);
        detailsStage.show();
    }

    public void searchplayersPressed(ActionEvent actionEvent) throws IOException {
        application.showSearch();
    }

    public void marketplacePressed(ActionEvent actionEvent) throws IOException {
        application.showMarketplace();
    }

    public void logoutPressed(ActionEvent actionEvent) {
        
    }
}
