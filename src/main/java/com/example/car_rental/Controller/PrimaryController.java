package com.example.car_rental.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class PrimaryController{
    PersistenceHandler persistenceHandler = PersistenceHandler.getInstance();
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private Label welcomeText;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private TextField carId;

    public String currentUser;


    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");

    }

    @FXML
    protected void viewCars(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/viewCars.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();

    }

    @FXML
    protected void loginScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/login.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    protected void viewAllCars(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/viewCars.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToSplash(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/splash.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    @FXML
    public void validateCustomers(ActionEvent event) throws IOException {
        String pas = persistenceHandler.customerLogIn(username.getText());
        String u = username.getText();
        String p = password.getText();
        if (p.equals(pas)) {
            currentUser = username.getText();
            Parent root = FXMLLoader.load(getClass().getResource("/view/customerView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle(username.getText());
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    @FXML
    public void validateEmployee(ActionEvent event) throws IOException {
        String pas = persistenceHandler.employeeLogIn(username.getText());
        String u = username.getText();
        String p = password.getText();
        if (p.equals(pas)) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/employeeView.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setTitle("EMPLOYEE VIEW");
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}