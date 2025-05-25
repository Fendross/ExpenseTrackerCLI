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
                    "CREATE TABLE IF NOT EXISTS tb_cash_flows (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "cf_type TEXT, " +
                    "amount REAL NOT NULL, " +
                    "cf_date TEXT NOT NULL, " +
                    "category TEXT, " +
                    "description TEXT" +
                    ");";
            stmt.execute(createTbCashFlow);

            // TO BE REMOVED.
            stmt.execute("INSERT INTO tb_cash_flows VALUES(1, 'EXPENSE', 300, '2025-05-24', 'Miscellaneous', 'Night out')");
        } catch (SQLException ex) {
            System.err.println("Error during DB initialization: " + ex.getMessage());
        }
    }
}
