package com.fendross.expensetrackercli.core.cashflowstatement;

import com.fendross.expensetrackercli.db.DatabaseManager;

import java.sql.*;

public class CashFlowStatementDAO {
    // SQL Statements.
    private static final String ADD_CASH_FLOW_STATEMENT =
            "INSERT INTO cash_flows (cf_type, amount, cf_date, category, description) VALUES(?, ?, ?, ?, ?);";
    private static final String DELETE_CASH_FLOW_STATEMENT =
            "DELETE FROM cash_flows WHERE id = ?;";

    // TODO add manipulation methods (insert, delete, update).
    // TODO also add a way to extract a CashFlowExpense from the DB.
    public int addCashFlowStatement(CashFlowStatement cfs) {
        int exitCode = 1;
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(ADD_CASH_FLOW_STATEMENT, Statement.RETURN_GENERATED_KEYS)) {

            // Set query values.
            pstmt.setString(1, cfs.getCfType().toString());
            pstmt.setDouble(2, cfs.getAmount());
            pstmt.setString(3, cfs.getCfDate().toString());
            pstmt.setString(4, cfs.getCategory());
            pstmt.setString(5, cfs.getDescription());

            // Execute query.
            int rowsAffected = pstmt.executeUpdate();

            // Retrieve inserted ID, if any, in order to finish setting up the CashFlowStatement.
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        cfs.setId(generatedKeys.getInt(1));
                    }
                }
                exitCode = 0;
            }
            return exitCode;
        } catch (SQLException ex) {
            System.err.println("Error while inserting: " + ex.getMessage());
        }
        return exitCode;
    }
}
