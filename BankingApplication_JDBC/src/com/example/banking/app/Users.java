package com.example.banking.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Users {
    private Connection connection;
    private Scanner scanner;

    public Users(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void registerNewUser() {
        scanner.nextLine();
        System.out.print("Full Name -: ");
        String fullName = scanner.nextLine();
        System.out.print("Email -: ");
        String email = scanner.nextLine();
        System.out.print("Password -: ");
        String password = scanner.nextLine();

        if (userExist(email)) {
            System.out.println("User already exist for this email !");
            return;
        }

        String newUserAdditionQuery = "INSERT INTO users (full_name, email, password) VALUES (?, ?, ?)";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(newUserAdditionQuery);
            preparedStatement.setString(1, fullName);
            preparedStatement.setString(2, email);
            preparedStatement.setString(3, password);

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Registered Successfully");
            } else {
                System.out.println("Registration Failed!");
            }

        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    public String loginUser() {
        scanner.nextLine();
        System.out.print("Email -: ");
        String email = scanner.nextLine();
        System.out.print("Password -: ");
        String password = scanner.nextLine();

        String loginUserQuery = "SELECT * FROM users WHERE email = ? AND password = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(loginUserQuery);
            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return email;
            } else {
                return null;
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    private boolean userExist(String email) {
        String userExists =  "SELECT * FROM users WHERE email = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(userExists);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }

        } catch (SQLException e){
            e.printStackTrace();
        }
        return false;
    }
}