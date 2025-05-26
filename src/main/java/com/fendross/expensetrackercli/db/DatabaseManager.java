package com.fendross.expensetrackercli.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseManager {
    private static final String DB_URL = "jdbc:sqlite:expense_tracker.db";
    private static Connection connection;

    public static Connection getConnection() throws SQLException {
        if (connection == null || connection.isClosed()) {
            connection = DriverManager.getConnection(DB_URL);
        }
        connection.setAutoCommit(true);
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null || !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException ex) {
            System.out.println("Error closing the DB connection: " + ex.getMessage());
        }
    }

    public static void initDb() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String createTbCashFlow =
                    "CREATE TABLE IF NOT EXISTS cash_flows (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "cf_type TEXT, " +
                    "amount REAL NOT NULL, " +
                    "cf_date TEXT NOT NULL, " +
                    "category TEXT, " +
                    "description TEXT" +
                    ");";
            stmt.execute(createTbCashFlow);
        } catch (SQLException ex) {
            System.err.println("Error during DB initialization: " + ex.getMessage());
        }
    }

    public static void dropDb() {
        try (Connection conn = getConnection(); Statement stmt = conn.createStatement()) {
            String dropTbCashFlow = "DROP TABLE IF EXISTS cash_flows";
            stmt.execute(dropTbCashFlow);
        } catch (SQLException ex) {
            System.err.println("Error during DB drop: " + ex.getMessage());
        }
    }
}
