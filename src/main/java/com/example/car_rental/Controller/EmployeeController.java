package com.example.car_rental.Controller;

import com.example.car_rental.Model.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.ResourceBundle;

public class EmployeeController implements Initializable {
    PersistenceHandler persistenceHandler = PersistenceHandler.getInstance();
    @FXML
    private ListView<Car> carsListView;
    @FXML
    private TextField carID;
    @FXML
    private TextField carMake;
    @FXML
    private TextField carModel;
    @FXML
    private TextField carYear;
    @FXML
    private TextField carVIN;
    @FXML
    private TextField carAvailability;
    @FXML
    private TextField carDailyCost;
    @FXML
    private TextField carDateAvailability;
    private Stage stage;
    private Scene scene;
    private Parent root;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carsListView.getItems().addAll(persistenceHandler.getCars());
    }

    public void employeeModifyCar() {
        int i = Integer.valueOf(carID.getText());
        Car car = new Car(carMake.getText(), carModel.getText(), Integer.valueOf(carYear.getText()),
                Integer.valueOf(carVIN.getText()), Double.valueOf(carDailyCost.getText()));
        persistenceHandler.modifyCar(i, car);
        updateUI();
    }

    public void employeeAddCar() {
        Car car = new Car(carMake.getText(), carModel.getText(), Integer.valueOf(carYear.getText()), Integer.valueOf(carVIN.getText()));
        System.out.println(car);
        persistenceHandler.addCar(car);
        updateUI();
    }

    public void employeeRemoveCar() {
        int i = Integer.valueOf(carID.getText());
        persistenceHandler.removeCar(i);
        updateUI();
    }

    private void updateUI(){
        carID.clear();
        carMake.clear();
        carModel.clear();
        carYear.clear();
        carVIN.clear();
        carDailyCost.clear();
        carsListView.getItems().clear();
        carsListView.getItems().addAll(persistenceHandler.getCars());
    }

    public void switchToSplash(ActionEvent event) throws IOException {
        System.out.println("switchinggggg");
        Parent root = FXMLLoader.load(getClass().getResource("/view/splash.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
