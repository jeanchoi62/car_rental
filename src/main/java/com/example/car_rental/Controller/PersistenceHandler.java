package com.example.car_rental.Controller;

import com.example.car_rental.Model.Car;
import com.example.car_rental.Model.Customer;

import java.lang.reflect.Array;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class PersistenceHandler {
    private static PersistenceHandler instance;
    private String url = "localhost";
    private int port = 5432;
    private String databaseName = "daisys_car_rental";
    private String username = "jeanmac";
    private String password = "";
    private Connection connection = null;
    public String currentUser;


    private PersistenceHandler() {
        initializePostgresqlDatabase();
    }

    public static PersistenceHandler getInstance() {
        if (instance == null) {
            instance = new PersistenceHandler();
        }
        return instance;
    }

    private void initializePostgresqlDatabase() {
        try {
            DriverManager.registerDriver(new org.postgresql.Driver());
            connection = DriverManager.getConnection("jdbc:postgresql://" + url + ":" + port + "/" + databaseName, username, password);
            System.out.println("DB connected");
        } catch (SQLException | IllegalArgumentException ex) {
            ex.printStackTrace(System.err);
        } finally {
            if (connection == null) {
                System.exit(-1);
            }
        }
    }

    public List<Car> getCars() {
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM cars");
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<Car> returnValues = new ArrayList<>();

            while (sqlReturnValues.next()) {
                returnValues.add(new Car(sqlReturnValues.getInt(1),
                        sqlReturnValues.getString(2), sqlReturnValues.getString(3)
                        , sqlReturnValues.getInt(4), sqlReturnValues.getInt(5)
                        , sqlReturnValues.getBoolean(6), sqlReturnValues.getDouble(7)
                        , sqlReturnValues.getDate(8)));

            }
            return returnValues;


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public List<Car> getCustomerRentalCars(String username) {
        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT * FROM cars WHERE current_reservation = '" + username + "';");
            ResultSet sqlReturnValues = stmt.executeQuery();

            List<Car> returnValues = new ArrayList<>();

            while (sqlReturnValues.next()) {
                returnValues.add(new Car(sqlReturnValues.getInt(1),
                        sqlReturnValues.getString(2), sqlReturnValues.getString(3)
                        , sqlReturnValues.getInt(4), sqlReturnValues.getInt(5)
                        , sqlReturnValues.getBoolean(6), sqlReturnValues.getDouble(7)
                        , sqlReturnValues.getDate(8)));

            }
            return returnValues;


        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return null;
    }

    public String customerLogIn(String enteredUserName) {
        String username = enteredUserName;
        String password = null;

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT username, password from CUSTOMERS where username = '" + username + "'");
            ResultSet sqlReturnValues = statement.executeQuery();

            while (sqlReturnValues.next()) {
                username = sqlReturnValues.getString(1);
                password = sqlReturnValues.getString(2);
                currentUser = sqlReturnValues.getString(1);

            }
        } catch (Exception e) {

        }
        return password;
    }

    public String employeeLogIn(String enteredUserName) {
        System.out.println("customerLogIn() was called");
        String username = enteredUserName;
        String password = null;

        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT username, password from EMPLOYEES where username = '" + username + "'");
            ResultSet sqlReturnValues = statement.executeQuery();

            while (sqlReturnValues.next()) {
                username = sqlReturnValues.getString(1);
                password = sqlReturnValues.getString(2);

            }
            System.out.println(username + ", " + password);
        } catch (Exception e) {

        }
        return password;
    }

    public void addCar(Car car) {
        try {
            PreparedStatement insertStatement = connection.prepareStatement(
                    "INSERT INTO cars (make, model, year, vin) VALUES (?,?,?,?);");
            insertStatement.setString(1, car.getMake());
            insertStatement.setString(2, car.getModel());
            insertStatement.setInt(3, car.getYear());
            insertStatement.setInt(4, car.getVin());

            insertStatement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void removeCar(int i) {
        try {
            PreparedStatement removeStatement = connection.prepareStatement(
                    "DELETE FROM cars WHERE id = " + i + ";");

            removeStatement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public void modifyCar(int id, Car car) {
        String s = "UPDATE cars SET make = '" + car.getMake() + "'"
                + ", model = '" + car.getModel() + "'"
                + ", year = " + Integer.valueOf(car.getYear())
                + ", vin = " + Integer.valueOf(car.getVin())
                + ", daily_cost = " + Double.valueOf(car.getCost())
                + " WHERE id = " + id + ";";
        try {
            PreparedStatement modifyStatement = connection.prepareStatement(s);
            modifyStatement.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public Car getCarWithID(int id) {
        Car car = null;
        try {
            PreparedStatement stmt = connection.prepareStatement("SELECT * FROM cars");
            ResultSet sqlReturnValues = stmt.executeQuery();

            while (sqlReturnValues.next()) {
                if (Integer.valueOf(sqlReturnValues.getString(1)) == id) {
                    car = new Car(sqlReturnValues.getInt(1),
                            sqlReturnValues.getString(2), sqlReturnValues.getString(3)
                            , sqlReturnValues.getInt(4), sqlReturnValues.getInt(5)
                            , sqlReturnValues.getBoolean(6), sqlReturnValues.getDouble(7)
                            , sqlReturnValues.getDate(8));
                }
            }
            return car;
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return car;
    }

    public void customerAddRentalCar(int carid, String username, int start, int end, int days) {
        Double totalCharge = 77.0;
        String s = "UPDATE customers SET current_rentals = array_append(current_rentals, "
                + carid + ") WHERE username = '" + username + "' AND " + carid + " <> ALL(current_rentals);";
        String s2 = "UPDATE cars SET current_reservation = '" + username + "' WHERE id = " + carid + ";";
        String s3 = "SELECT available, rental_dates FROM cars WHERE ID = " + carid + ";";
        String s4 =  "UPDATE cars SET available = 't', rental_dates = array_append(rental_dates, '"
                + username + "-" + start + end +"') WHERE id = " + carid + ";";
        String s5 = "SELECT daily_cost FROM cars WHERE id = " + carid + ";";
        String availability = "";
        if (getCustomerRentalSize(username).size() == 0) {
            s = "UPDATE customers SET current_rentals = '{"
                    + carid + "}' WHERE username = '" + username + "';";
        }
        try {
            PreparedStatement checkCarAvailability = connection.prepareStatement(s3);
            ResultSet rs = checkCarAvailability.executeQuery();
            while (rs.next() ) {
                availability = rs.getString(1);
            }
            if (availability.equals("t")) {
                PreparedStatement getCost = connection.prepareStatement(s5);
                ResultSet r = getCost.executeQuery();
                int cost = 0;
                while (r.next() ) {
                    cost = r.getInt(1);
                }
                totalCharge = days * Double.valueOf(cost);


                PreparedStatement addCarRental = connection.prepareStatement(s);
                PreparedStatement addNameOfRenter = connection.prepareStatement(s2);
                addCarRental.execute();
                addNameOfRenter.execute();
                PreparedStatement updateAvailability = connection.prepareStatement(s4);
                updateAvailability.execute();
                System.out.println("current rental days: " + days + " daily price: " + cost + "TOTAL + " + totalCharge);
            } else {
                System.out.println("CAR ID " + carid + " is not available");
            }
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    public double updateCustomerCharges(int carID, int days) {
        Double totalCharge = 77.0;
        String s5 = "SELECT daily_cost FROM cars WHERE id = " + carID + ";";

        try {
            PreparedStatement getCost = connection.prepareStatement(s5);
            ResultSet r = getCost.executeQuery();
            int cost = 0;
            while (r.next()) {
                cost = r.getInt(1);
            }
            totalCharge = days * Double.valueOf(cost);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return totalCharge;

    }

    public void customerReturnRentalCar(int carID, String username) {

        String removeFromCustomerList = "UPDATE customers SET current_rentals = ARRAY_REMOVE(current_rentals, "
                + carID + ") WHERE username = '" + username + "';";
        String updateCarsReservationList = "UPDATE cars SET current_reservation = null WHERE id = " + carID + ";";
        String setCarAvailBool = "UPDATE cars SET available = 't' WHERE id = " + carID + ";";
        try {
            PreparedStatement a = connection.prepareStatement(removeFromCustomerList);
            PreparedStatement b = connection.prepareStatement(updateCarsReservationList);
            PreparedStatement c = connection.prepareStatement(setCarAvailBool);
            a.execute();
            b.execute();
            c.execute();
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }


    public ArrayList<Integer> getCustomerRentalSize(String username) {
        String s = "";
        ArrayList<Integer> carList = new ArrayList();

        try {
            PreparedStatement stmt = connection.prepareStatement(
                    "SELECT current_rentals FROM customers WHERE username = '" + username + "';");
            ResultSet sqlReturnValues = stmt.executeQuery();

            if (sqlReturnValues.wasNull()) {
                return carList;
            }

                while (sqlReturnValues.next()) {
                    s += sqlReturnValues.getArray("current_rentals");
                }
            s = s.replace("{", "");
            s = s.replace("}", "");
            String[] l = s.split(",");
            for (String d: l) {
                if (d.equals("")) {
                    return carList;
                } else {
                    carList.add(Integer.valueOf(d));
                }
            }

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
            return carList;
    }
}
