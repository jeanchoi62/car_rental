package com.example.car_rental.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import com.example.car_rental.Model.Car;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.stage.Stage;



public class ViewCarsController implements Initializable {
    PersistenceHandler persistenceHandler = PersistenceHandler.getInstance();
    @FXML
    private ListView<Car> carsListView;
    private Stage stage;
    private Scene scene;
    private Parent root;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        carsListView.getItems().addAll(persistenceHandler.getCars());
    }

    @FXML
    public void switchToSplash(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/splash.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
