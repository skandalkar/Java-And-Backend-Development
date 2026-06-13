package com.example.java_backend_learning;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import static java.lang.System.exit;

public class Hotel_Reservation_System {

    protected void newReservationBooking(Statement statement, Scanner sc) {
        System.out.println("Book The New Room\n");
        System.out.print("Enter the Guest Name -: ");
        String name = sc.next();
        sc.nextLine();
        System.out.print("Enter Room No -: ");
        int room_no = sc.nextInt();
        System.out.print("Enter contact No +91 -: ");
        String contact = sc.next();

        String query = "INSERT INTO reservations (guest_name, room_no, contact_no) " +
                "VALUES ('" + name + "'," + room_no + ",'" + contact + "')";

        try {
            int rowsAffected = statement.executeUpdate(query);

            if (rowsAffected > 0) {
                System.out.println("Reservation successfully done.");
                System.out.println();
            } else {
                System.out.println("Reservation failed!");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void viewReservation(Statement statement) {

        String query = "SELECT reservation_id, guest_name, room_no, contact_no, reservation_date FROM reservations ";

        try (ResultSet resultSet = statement.executeQuery(query)) {

            System.out.println("Current Reservation Records ");
            System.out.println("+----------------+--------------------------+-----------+--------------+------------------------+");
            System.out.println("| Reservation ID | Guest Name               | Room No   | Contact No   | Reservation Date (IST) |");
            System.out.println("+----------------+--------------------------+-----------+--------------+------------------------+");

            while (resultSet.next()) {
                int reservation_id = resultSet.getInt("reservation_id");
                String guest_name = resultSet.getString("guest_name");
                int room_no = resultSet.getInt("room_no");
                String contact_no = resultSet.getString("contact_no");
                String reservation_date = resultSet.getTimestamp("reservation_date").toString();

                // Format and Display Records
                System.out.printf("| %-14d | %-23s  | %-8d  | %-11s  | %-22s |\n", reservation_id, guest_name, room_no, contact_no, reservation_date);
                System.out.println("+----------------+--------------------------+-----------+--------------+------------------------+");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void getRoomsBooked(Statement statement, Scanner sc) {
        try {
            System.out.print("Enter Reservation ID -: ");
            int reservation_id = sc.nextInt();
            System.out.print("Enter Guest Name -: ");
            String guest_name = sc.next();

            String query = "SELECT room_no FROM reservations " +
                    "WHERE  reservation_id = " + reservation_id +
                    " AND guest_name = '" + guest_name + "'";

            try (ResultSet resultSet = statement.executeQuery(query)) {
                if (resultSet.next()) {
                    int room_no = resultSet.getInt("room_no");
                    System.out.println();
                    System.out.println("Reservation ID -: " + reservation_id);
                    System.out.println("Guest Name -: " + guest_name);
                    System.out.println("Allotted Room No -: " + room_no);
                    System.out.println();
                } else {
                    System.out.println("Reservation ID " + reservation_id + " and name " + guest_name + " not found!");
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void updateReservation(Statement statement, Scanner sc) {
        try {
            System.out.print("Enter the Reservation ID for update -: ");
            int reservation_id = sc.nextInt();
            sc.nextLine();

            if (!reservationIdExists(statement, reservation_id)) {
                System.out.println("Reservations for Reservation ID " + reservation_id + " not found!");
                return;
            }

            System.out.print("Enter new name -: ");
            String newGuestName = sc.nextLine();
            System.out.print("Enter new room no -: ");
            int newRoomNo = sc.nextInt();
            System.out.print("Enter new contact no -: ");
            String newContactNo = sc.next();

            String updateQuery = "UPDATE reservations SET guest_name = '" + newGuestName + "'," +
                    "room_no = " + newRoomNo +
                    "," + " contact_no = '" + newContactNo + "'" +
                    "WHERE reservation_id = " + reservation_id;

            try {
                int rowsAffected = statement.executeUpdate(updateQuery);
                if (rowsAffected > 0) {
                    System.out.println("Reservation Updated Successfully.");
                    System.out.println();
                } else {
                    System.out.println("Reservation Failed to Update!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean reservationIdExists(Statement statement, int reservationId) {
        try {
            String query = "SELECT reservation_id from reservations where reservation_id = " + reservationId;
            try (ResultSet resultSet = statement.executeQuery(query)) {
                return resultSet.next(); // returns true if reservation_id exists
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected void deleteReservation(Statement statement, Scanner sc) {
        System.out.print("Enter the Reservation ID for delete -: ");
        int reservation_id = sc.nextInt();

        if (!reservationIdExists(statement, reservation_id)) {
            System.out.println("Reservations for Reservation ID " + reservation_id + " not found!");
            return;
        }

        String deleteQuery = "DELETE FROM reservations WHERE reservation_id = " + reservation_id;

        try {
            int rowsAffected = statement.executeUpdate(deleteQuery);

            if (rowsAffected > 0) {
                System.out.println("Reservation deleted successfully for ID " + reservation_id);
                System.out.println();
            } else {
                System.out.println("Deletion failed to perform.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    protected void exitFromApp() throws InterruptedException {
        System.out.print("Exiting from System");
        int i = 5;
        while (i != 0) {
            System.out.print(".");
            Thread.sleep(500);
            i--;
        }
        System.out.println();
        System.out.println("Thank You for Using Our System :)");
        exit(0);
    }
}