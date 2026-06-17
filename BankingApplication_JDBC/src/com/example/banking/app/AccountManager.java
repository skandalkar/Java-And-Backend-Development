package com.example.banking.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class AccountManager {
    private Connection connection;
    private Scanner scanner;

    public AccountManager(Connection connection, Scanner scanner) {
        this.connection = connection;
        this.scanner = scanner;
    }

    public void creditAmount(long account_no) throws SQLException{
        scanner.nextLine();
        System.out.print("Enter Amount -: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security PIN -: ");
        String security_pin = scanner.next();

        try {
            connection.setAutoCommit(false);

            if (account_no != 0) {
                String query = "SELECT * FROM accounts WHERE account_no = ? AND security_pin = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1, account_no);
                preparedStatement.setString(2, security_pin);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    String queryCredit = "UPDATE accounts SET balance = balance + ? WHERE account_no = ?";
                    PreparedStatement preparedStatementCredit = connection.prepareStatement(queryCredit);
                    preparedStatementCredit.setDouble(1, amount);
                    preparedStatementCredit.setLong(2, account_no);

                    int rowsAffectedCredit = preparedStatementCredit.executeUpdate();

                    if (rowsAffectedCredit > 0) {
                        System.out.println("Rs. "+amount+" credited to "+account_no+" successfully.");
                        connection.commit();
                        connection.setAutoCommit(true);
                    } else {
                        System.out.println("Transaction Failed !");
                        connection.rollback();
                        connection.setAutoCommit(true);
                    }
                } else {
                    System.out.println("Invalid Security PIN !");
                }
            } else {
                System.out.println("Invalid Account no !");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public void debitAmount(long account_no) throws SQLException {
        scanner.nextLine();
        System.out.print("Enter Amount -: ");
        double amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security PIN -: ");
        String security_pin = scanner.next();

        try {
            connection.setAutoCommit(false);
            String query = "SELECT * FROM accounts WHERE account_no = ? AND security_pin = ?";
            if (account_no != 0) {
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setLong(1,account_no);
                preparedStatement.setString(2, security_pin);
                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    double currentBalance = resultSet.getDouble("balance");

                    if (amount <= currentBalance) {
                        String debitQuery = "UPDATE accounts SET balance = balance - ? WHERE account_no = ?";

                        PreparedStatement preparedStatementDebit = connection.prepareStatement(debitQuery);
                        preparedStatementDebit.setDouble(1, amount);
                        preparedStatementDebit.setLong(2, account_no);

                        int rowsAffectedDebit = preparedStatementDebit.executeUpdate();

                        if (rowsAffectedDebit > 0) {
                            System.out.println("Rs. "+amount+" debited.");
                            connection.commit();
                            connection.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("Transaction Failed!");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    } else {
                        System.out.println("Insufficient Balance !");
                    }
                } else {
                    System.out.println("invalid PIN !");
                }
            } else {
                System.out.println("Invalid Account No !");
            }

        }catch (SQLException e) {
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public void transferFunds (long sender_account_no) throws SQLException {
        scanner.nextLine();
        System.out.print("Enter Receiver Account No -: ");
        long receiver_account_no = scanner.nextLong();
        System.out.print("Enter Amount -: ");
        double amountTransfer = scanner.nextDouble();
        scanner.nextLine();
        System.out.print("Enter Security PIN -: ");
        String security_pin = scanner.next();

        try {
            connection.setAutoCommit(false);

            if (sender_account_no != 0 && receiver_account_no != 0) {
                String query1 = "SELECT * FROM accounts WHERE account_no = ? AND security_pin = ?";
                PreparedStatement preparedStatement = connection.prepareStatement(query1);
                preparedStatement.setLong(1, sender_account_no);
                preparedStatement.setString(2, security_pin);

                ResultSet resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    double currentBalance = resultSet.getDouble("balance");

                    if (amountTransfer <= currentBalance) {
                        // Debit and Credit Query

                        String debitQuery = "UPDATE accounts SET balance = balance - ? WHERE account_no = ?";
                        String creditQuery = "UPDATE accounts SET balance = balance + ? WHERE account_no = ?";

                        // Debit and Credit PrepareStatements
                        PreparedStatement preparedStatementDebit = connection.prepareStatement(debitQuery);
                        PreparedStatement preparedStatementCredit = connection.prepareStatement(creditQuery);

                        // Setting values for debit and credit
                        preparedStatementDebit.setDouble(1, amountTransfer);
                        preparedStatementDebit.setLong(2, sender_account_no);

                        preparedStatementCredit.setDouble(1, amountTransfer);
                        preparedStatementCredit.setLong(2, receiver_account_no);

                        int rowsAffectedDebit = preparedStatementDebit.executeUpdate();
                        int rowsAffectedCredit = preparedStatementCredit.executeUpdate();

                        if (rowsAffectedDebit > 0 && rowsAffectedCredit > 0) {
                            connection.commit();
                            System.out.println("Transaction Successful.");
                            System.out.println("Rs. "+amountTransfer+" transferred successfully.");
                            connection.setAutoCommit(true);
                            return;
                        } else {
                            System.out.println("Transaction Failed");
                            connection.rollback();
                            connection.setAutoCommit(true);
                        }
                    } else {
                        System.out.println("Insufficient Balance !");
                    }
                } else {
                    System.out.println("Invalid Security PIN !");
                }
            }else {
                System.out.println("Invalid Account No !");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        connection.setAutoCommit(true);
    }

    public void checkBalance(long account_no) {
        scanner.nextLine();
        System.out.print("Enter Security PIN -: ");
        String security_pin = scanner.next();

        try {
            String checkBalance = "SELECT balance FROM accounts WHERE account_no = ? AND security_pin = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(checkBalance);
            preparedStatement.setLong(1, account_no);
            preparedStatement.setString(2, security_pin);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                double balance = resultSet.getDouble("balance");
                System.out.println("Balance -: "+balance);
            }else {
                System.out.println("Invalid PIN!");
            }
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }
}