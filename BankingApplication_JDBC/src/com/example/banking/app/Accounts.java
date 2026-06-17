package com.example.banking.app;

import java.sql.*;
import java.util.Scanner;

public class Accounts {

    private Connection connection;
    private Scanner scanner;

    public Accounts(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public long openNewAccount(String email) {
        if (!account_exist(email)) {
            String openNewAccountQuery = "INSERT INTO accounts (account_no, full_name, email, balance, security_pin) VALUES (?, ?, ?, ?, ?)";

            scanner.nextLine();

            System.out.print("Enter Full Name -: ");
            String fullName = scanner.nextLine();
            System.out.print("Enter Initial Amount -: ");
            double balance = scanner.nextDouble();
            scanner.nextLine();
            System.out.print("Enter Security PIN -: ");
            String pin_security = scanner.next();

            try {
                long accountNo = generateAccountNumber();
                PreparedStatement preparedStatement = connection.prepareStatement(openNewAccountQuery);
                preparedStatement.setLong(1, accountNo);
                preparedStatement.setString(2, fullName);
                preparedStatement.setString(3, email);
                preparedStatement.setDouble(4, balance);
                preparedStatement.setString(5, pin_security);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                    return accountNo;
                } else {
                    throw new RuntimeException("Account Creation Failed !");
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException("Account Already Exists");
    }


    protected long getAccountNo(String email) {
        String query = "SELECT account_no FROM accounts WHERE email = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getLong("account_no");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Account No Doesn't Exist!");
    }


    private long generateAccountNumber() {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT account_no FROM accounts order by account_no DESC LIMIT 1");
            if (resultSet.next()) {
                long last_account_no = resultSet.getLong("account_no");
                return last_account_no+1;
            } else {
                return 10000100;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 10000100;
    }


    protected boolean account_exist(String email) {
        String query = "SELECT account_no FROM accounts WHERE email = ?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}