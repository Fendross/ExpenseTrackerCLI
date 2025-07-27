package com.fendross.expensetrackercli.dao;

import com.fendross.expensetrackercli.db.DatabaseManager;
import com.fendross.expensetrackercli.model.CashFlowStatement;
import com.fendross.expensetrackercli.utils.GenericUtils;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CashFlowStatementDAO {
    // SQL Statements.
    private static final String ADD_CASH_FLOW_STATEMENT =
            "INSERT INTO cash_flows (cf_type, amount, cf_date, category, description) VALUES(?, ?, ?, ?, ?);";
    private static final String DELETE_CASH_FLOW_STATEMENT =
            "DELETE FROM cash_flows WHERE id = ?;";
    private static final String SELECT_CASH_FLOW_STATEMENT_FROM_ID =
            "SELECT * FROM cash_flows WHERE id = ?;";
    private static final String SELECT_ALL_CASH_FLOW_STATEMENTS =
            "SELECT * FROM cash_flows ORDER BY cf_date DESC;";
    private static final String TRUNCATE_CASH_FLOWS_TABLE =
            "DELETE FROM cash_flows;";
    private static final String GET_TOTAL_AMOUNT_OF_CF_TYPE =
            "SELECT SUM(amount) FROM cash_flows WHERE cf_type = ?;";


    /**
     * Inserts a CashFlowStatement from the cash_flows table.
     *
     * @param cfs CashFlowStatement, the cash flow statement the user wishes to insert
     * @return int, 0 if insertion was successful, 1 otherwise.
     */
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

    public List<CashFlowStatement> getAllCashFlowStatements() {
        List<CashFlowStatement> cashFlowStatements = new ArrayList<>();
        try (Connection conn = DatabaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL_CASH_FLOW_STATEMENTS)) {

            while (rs.next()) {
                cashFlowStatements.add(getCashFlowStatementFromResultSet(rs));
            }


        } catch (SQLException ex) {
            System.err.println("Error while selecting all statements: " + ex.getMessage());
        }
        return cashFlowStatements;
    }

    /**
     * Deletes a CashFlowStatement from the cash_flows table.
     *
     * @param id int, the id of the cash flow statement the user wishes to delete.
     * @return int, 0 if deletion was successful, 1 otherwise.
     */
    public int deleteCashFlowStatement(int id) {
        int rowsAffected = 0;
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(DELETE_CASH_FLOW_STATEMENT)) {

            // Set query values.
            pstmt.setInt(1, id);

            // Execute query.
            rowsAffected = pstmt.executeUpdate();

            return (rowsAffected > 0) ? 0 : 1;
        } catch (SQLException ex) {
            System.err.println("Error while deleting: " + ex.getMessage());
        }
        return 1;
    }

    /**
     * Truncates the cash_flows table.
     *
     * @return int, 0 if truncation was successful or if it didn't delete any rows, 1 otherwise.
     */
    public int truncateCashFlowTable() {
        int rowsAffected = 0;
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(TRUNCATE_CASH_FLOWS_TABLE)) {

            // Execute query.
            rowsAffected = pstmt.executeUpdate();

            if (rowsAffected >= 0) {
                if (rowsAffected == 0) {
                    System.out.println("WARN: No rows have been deleted.");
                }
                return 0;
            }
        } catch (SQLException ex) {
            System.err.println("Error while deleting: " + ex.getMessage());
        }
        return 1;
    }

    /**
     * Retrieves the total amount, based on what Cash Flow type it has been passed.
     *
     * @param cfType String, the type of cash flow to be queried.
     * @return double, the sum of the amounts in the table.
     */
    public double getTotalAmountOfCfType(String cfType) {
        double totalAmount = 0;
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(GET_TOTAL_AMOUNT_OF_CF_TYPE)) {

            // Set query values.
            pstmt.setString(1, cfType);

            // Execute query.
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    totalAmount = rs.getDouble(1);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error while selecting: " + ex.getMessage());
        }
        return totalAmount;
    }

    /**
     * Gets a cash flow statement from cash_flows by id.
     *
     * @param id int, unique identifier of cash flow statement.
     * @return The extracted CashFlowStatement.
     */
    public CashFlowStatement getCashFlowStatementById(int id) {
        try (Connection conn = DatabaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(SELECT_CASH_FLOW_STATEMENT_FROM_ID)) {

            // Set query values.
            pstmt.setInt(1, id);

            // Execute query and extract the CashFlowStatement.
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    return getCashFlowStatementFromResultSet(rs);
                }
            }
        } catch (SQLException ex) {
            System.err.println("Error while inserting: " + ex.getMessage());
        }
        return null;
    }

    public CashFlowStatement getCashFlowStatementFromResultSet(ResultSet rs) throws SQLException {
        return new CashFlowStatement(
                rs.getInt("id"),
                GenericUtils.getTypeOfStatement(rs.getString("cf_type")),
                rs.getDouble("amount"),
                LocalDate.parse(rs.getString("cf_date")),
                rs.getString("category"),
                rs.getString("description")
        );
    }
}
