package com.example.banking.app;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Scanner;

public class Banking_App_Main {
    private static final String databaseUrl = "jdbc:mysql://localhost:3306/banking_system";
    private static final String username = "root";
    private static final String password = "Santosh_1508";

    public static void main(String[] args) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        try {
            Connection connection = DriverManager.getConnection(databaseUrl, username, password);
            Scanner scanner = new Scanner(System.in);

            Users users = new Users(connection, scanner);
            Accounts accounts = new Accounts(connection, scanner);
            AccountManager accountManager = new AccountManager(connection, scanner);

            String email;
            long account_no;

            while (true) {
                System.out.println("*** Welcome to Banking System ***");
                System.out.println();
                System.out.println("\t----- Menu -----");
                System.out.println("1. Register \n2. Login \n3. Exit");
                System.out.print("\nEnter Option -: ");
                byte option = scanner.nextByte();
                switch (option) {
                    case 1:
                        users.registerNewUser();
                        break;

                    case 2:
                        email = users.loginUser();
                        if (email != null) {
                            System.out.println();
                            System.out.println("User logged in successfully");

                            if (!accounts.account_exist(email)) {
                                System.out.println();
                                System.out.println("1. Open New Bank Account \n2. Exit");
                                System.out.print("\nEnter Option -: ");
                                byte option2 = scanner.nextByte();

                                if (option2 == 1) {
                                    account_no = accounts.openNewAccount(email);
                                    System.out.println("Account Created Successfully");
                                    System.out.println("Account No. -: " + account_no);
                                } else {
                                    break;
                                }
                            }
                            account_no = accounts.getAccountNo(email);
                            int option2 = 0;
                            while (option2 != 5) {
                                System.out.println();
                                System.out.println("1. Deposit Money \n2. Withdraw Money \n3. Transfer Funds \n4. Check Balance \n5. Log Out");
                                System.out.print("\nEnter Option -: ");
                                option2 = scanner.nextByte();

                                switch (option2) {
                                    case 1:
                                        accountManager.creditAmount(account_no);
                                        break;

                                    case 2:
                                        accountManager.debitAmount(account_no);
                                        break;

                                    case 3:
                                        accountManager.transferFunds(account_no);
                                        break;

                                    case 4:
                                        accountManager.checkBalance(account_no);
                                        break;

                                    case 5:
                                        break;

                                    default:
                                        System.out.println("Invalid Option");
                                        break;
                                }
                            }
                        } else {
                            System.out.println("Incorrect Email or Password");
                        }

                    case 3:
                        System.out.println("Thank you for using our system");
                        System.out.println("Exiting System");
                        return;

                    default:
                        System.out.println("Invalid Option!");
                        break;
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}