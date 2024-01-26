package com.example.car_rental.Model;

import java.util.Date;

public class Car {
    private Integer id;
    private String make;
    private String model;
    private int year;
    private int vin;
    private Boolean available;
    private double daily_cost;
    private Date date_available;

    public Car(int id, String make, String model, int year, int vin,
               boolean available, Double daily_cost, Date date_available) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.vin = vin;
        this.available = available;
        this.daily_cost = daily_cost;
        this.date_available = date_available;
    }

    public Car(String make, String model, int year, int vin) {
        this.make = make;
        this.model = model;
        this.year = year;
        this.vin = vin;
        this.daily_cost = daily_cost;
    }

    public Car(String make, String model, int year, int vin, Double daily_cost) {
        this.id = id;
        this.make = make;
        this.model = model;
        this.year = year;
        this.vin = vin;
        this.daily_cost = daily_cost;
    }

    public String getMake() {
        return make;
    }

    public String getModel() {
        return model;
    }

    public int getYear() {
        return year;
    }

    public int getVin() {
        return vin;
    }
    public double getCost() {
        return daily_cost;
    }

    public int getID() { return this.id; }

    public void setAvailable() {
        this.available = true;
    }

    public void setUnvailable() {
        this.available = false;
    }

    @Override
    public String toString() {
        return "ID #" + id + ", " + year + " "  + make + " " + model + ", VIN: " + vin
                + ", Available: " + available + ", Daily Cost: $"
                + daily_cost + ", Date Available: " + date_available;
    }
}
