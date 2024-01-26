package com.example.car_rental.Controller;

import com.example.car_rental.Model.Car;
import com.example.car_rental.Model.Customer;
import com.example.car_rental.Controller.PrimaryController;
import com.example.car_rental.Controller.PersistenceHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CustomerController implements Initializable {
    PersistenceHandler persistenceHandler = PersistenceHandler.getInstance();
    @FXML
    String currentUser = persistenceHandler.currentUser;
    @FXML
    private ListView<Car> customersCarRentalsListView;
    @FXML
    private ListView<Car> carsListView;
    @FXML
    private TextField totalCost;
    @FXML
    private TextField rentalDays;
    @FXML
    private TextField carID;
    @FXML
    private TextField currentTotalCost;
    @FXML
    private TextField startRentalDate;
    @FXML
    private TextField endRentalDate;

    private Stage stage;
    private Scene scene;
    private Parent root;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carsListView.getItems().addAll(persistenceHandler.getCars());
        customersCarRentalsListView.getItems().addAll(persistenceHandler.getCustomerRentalCars(currentUser));
    }

    @FXML
    protected String getCurrentUser(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        String u = stage.getTitle();
        return u;
    }


    @FXML
    protected void customerAddCarRental(ActionEvent event) throws IOException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        String u = stage.getTitle();
        System.out.println(u);
        int car = Integer.valueOf(carID.getText());
        int startDate = Integer.valueOf(startRentalDate.getText());
        int endDate = Integer.valueOf(endRentalDate.getText());
        int days = Integer.valueOf(rentalDays.getText());
        persistenceHandler.customerAddRentalCar(car, u, startDate, endDate, days);
        currentTotalCost.setText(String.valueOf(persistenceHandler.updateCustomerCharges(car, days)));
        updateUI();
    }




    public void customerReturnRental() {
        int car = Integer.valueOf(carID.getText());
        persistenceHandler.customerReturnRentalCar(car, currentUser);
        updateUI();
        currentTotalCost.clear();


    }


    public void calculateCost() {
        Double dailyCarCost = persistenceHandler.getCarWithID(Integer.valueOf(carID.getText())).getCost();
        Double total = Integer.valueOf(rentalDays.getText()) * dailyCarCost;
        totalCost.setText(String.valueOf(total));
    }

    public void updateUI() {
        rentalDays.clear();
        carID.clear();
        customersCarRentalsListView.getItems().clear();
        customersCarRentalsListView.getItems().addAll(persistenceHandler.getCustomerRentalCars(currentUser));
    }



    public void switchToSplash(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/splash.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
