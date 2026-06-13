package com.example.java_backend_learning;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class Main {

    private static final String databaseUrl = "jdbc:mysql://localhost:3306/hotel_db";
    private static final String username = "root";
    private static final String password = "Santosh_1508";

    public static void main(String[] args) {

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }

        Hotel_Reservation_System h = new Hotel_Reservation_System();
        System.out.println();

        Scanner sc = new Scanner(System.in);

        try {
            Connection connection = DriverManager.getConnection(databaseUrl, username, password);
            Statement statement = connection.createStatement();

            while (true) {
                System.out.println("Hotel Reservation System");
                System.out.println("\n\t\t Menu");
                System.out.println("1. Book New Room");
                System.out.println("2. View Reservations");
                System.out.println("3. Get Room No");
                System.out.println("4. Update Reservation");
                System.out.println("5. Delete Reservation");
                System.out.println("0. Exit");
                System.out.print("\nEnter option -: ");
                byte option = sc.nextByte();

                switch (option) {
                    case 0:
                        h.exitFromApp();
                        sc.close();
                        break;

                    case 1:
                        h.newReservationBooking(statement, sc);
                        break;

                    case 2:
                        h.viewReservation(statement);
                        break;

                    case 3:
                        h.getRoomsBooked(statement, sc);
                        break;

                    case 4:
                        h.updateReservation(statement, sc);
                        break;

                    case 5:
                        h.deleteReservation(statement, sc);
                        break;

                    default:
                        System.out.println("Invalid input detected! Please select valid option.");
                        System.out.println();
                }
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}