package com.example.controllers;

import com.example.helper.*;
import com.example.serializedclass.*;
import com.example.playerdatabase.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class HomepageController {
    Main application;
    String username;

    @FXML
    VBox box;
    @FXML
    ScrollPane scrollpane;
    @FXML
    Label teamname;
    @FXML
    Button myPlayers;
    @FXML
    Button searchPlayers;

    ArrayList<Player> list;

    public void setList(ArrayList<Player> list) {
        this.list = list;
    }

    public void setApplication(Main application) {
        this.application = application;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void init() throws IOException {
        VBox box = new VBox();
        box.setPrefWidth(575);
        box.setPrefHeight(480);

        scrollpane.setStyle("-fx-background: transparent; " +
                "-fx-background-color: rgba(0, 0, 0, 0.7); " + // Semi-transparent black
                "-fx-border-color: transparent;");
        teamname.setText(username);
//#1e1e1e
        box.setStyle("-fx-background-color: transparent;");
        box.setPadding(new Insets(10));
        box.setSpacing(5);

        Helper helper = new Helper();
        helper.setApplication(application);

        for (Player player : list) {
            box.getChildren().add(helper.createPlayerRow(player));
        }
        scrollpane.setContent(box);
    }



    public void myPlayersPressed(ActionEvent actionEvent) throws IOException {
        application.showHome();
    }

    public void searchPlayersPressed(ActionEvent actionEvent) throws IOException {
        application.showSearch();
    }

    public void marketplacePressed(ActionEvent actionEvent) throws IOException {
        application.showMarketplace();
    }

    public void logoutPressed(ActionEvent actionEvent) {
    }
}
