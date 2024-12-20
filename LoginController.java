package com.example.controllers;

import com.example.helper.NetworkUtil;
import com.example.playerdatabase.*;
import com.example.serializedclass.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;
import java.util.ArrayList;

public class LoginController {
    private Main application;
    private NetworkUtil network;

    public void setNetwork(NetworkUtil network) {
        this.network = network;
    }

    @FXML
    private TextField username;
    @FXML
    private PasswordField password;
    @FXML
    private Button login;
    @FXML
    private Button reset;


    public void setApplication(Main application){
        this.application = application;
    }

    public void loginPressed(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        LoginRequest loginrequest = new LoginRequest(username.getText(),password.getText());
        network.write(loginrequest);
    }

    public static void loginFailure(){
        Alert error = new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Login Window");
        error.setContentText("Login Failed!");
        error.showAndWait();

    }

    public void resetPressed(ActionEvent actionEvent) {
        username.setText("");
        password.setText("");
    }
}