package com.example.car_rental.Model;

public class Customer {
    private int id;
    private String first_name;
    private String last_name;
    private int dob;
    private String username;
    private String password;
    private int[] current_rentals;

    public Customer(int id, String first_name, String last_name, int  dob,
                    String username, String password) {
        this.id = id;
        this.first_name = first_name;
        this.last_name = last_name;
        this.dob = dob;
        this.username = username;
        this.password = password;
    }

}
