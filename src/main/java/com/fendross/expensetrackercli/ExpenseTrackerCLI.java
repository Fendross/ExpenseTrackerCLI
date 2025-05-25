package com.fendross.expensetrackercli;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.fendross.expensetrackercli.core.expense.Expense;
import com.fendross.expensetrackercli.core.expense.ExpenseManager;
import com.fendross.expensetrackercli.core.income.Income;
import com.fendross.expensetrackercli.core.income.IncomeManager;
import com.fendross.expensetrackercli.core.fs.FsManager;

import com.fendross.expensetrackercli.db.DatabaseManager;
import com.fendross.expensetrackercli.exceptions.*;

import com.fendross.expensetrackercli.utils.GenericUtils;
import com.fendross.expensetrackercli.utils.GenericUtils.TypeOfStatement;
import com.fendross.expensetrackercli.utils.ReplUtils;

import static com.fendross.expensetrackercli.utils.GenericUtils.numOfMandatoryAddParams;
import static com.fendross.expensetrackercli.utils.GenericUtils.numOfMaximumAddParams;

public class ExpenseTrackerCLI {

    /** Manages all expense-related operations. */
    public static ExpenseManager expenseManager = new ExpenseManager();

    /** Manages all income-related operations. */
    public static IncomeManager incomeManager = new IncomeManager();

    /**
     * The main entry point of the Expense Tracker CLI application.
     * It initializes the REPL loop and handles user commands.
     *
     * @param args command-line arguments (currently expects none).
     */
    public static void main(String[] args) {
        int expectedArgs = 0;
        if (args.length != expectedArgs) {
            System.out.println("Too many arguments. Expected arguments: " + expectedArgs);
            System.exit(1);
        }

        // TODO switch file system logic with DB logic.
        // Testing DB connection.
        Connection conn;
        DatabaseManager.initDb();
        try {
            conn = DatabaseManager.getConnection();

            Statement stmt = conn.createStatement();
            stmt.execute("select * from tb_cash_flows");
            
            ResultSet rs = stmt.getResultSet();
            System.out.println(rs.getInt("id"));
            System.out.println(rs.getString("cf_type"));
        } catch (SQLException ex) {
            System.err.println("While opening DB connection: " + ex.getMessage());
        }
        DatabaseManager.closeConnection();
        System.exit(0);

        boolean wasFileCreated;
        boolean needToPrintHelper = false;

        // File creation.
        FsManager fsManager = new FsManager();
        try {
            wasFileCreated = fsManager.initFsManager();
            if (wasFileCreated) {
                ReplUtils.handleFileCreation(fsManager.getCsvPath());
            }
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }

        // Load file statements into DS.
        try {
            fsManager.loadStatementsFromFile(expenseManager, incomeManager);
        } catch (FsException ex) {
            System.out.println(ex.getMessage());
            System.exit(1);
        }

        // Main loop.
        ReplUtils.welcomeUser();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            needToPrintHelper = ReplUtils.askForInput(needToPrintHelper);

            String task = scanner.nextLine();
            ArrayList<String> commands = new ArrayList<>(List.of(task.trim().split(" ")));

            String leadCommand = commands.get(0);
            switch (leadCommand.toLowerCase()) {
                case "exit":
                    scanner.close();
                    System.exit(0);
                case "help":
                    ReplUtils.handleHelpCommand();
                    needToPrintHelper = true;
                    continue;
                case "add":
                    try {
                        handleAddCommand(commands);
                        needToPrintHelper = false;
                    } catch (AddException ex) {
                        System.out.println("Error while inserting: " + ex.getMessage());
                    }
                    break;
                case "delete":
                    try {
                        handleDeleteCommand(commands);
                        needToPrintHelper = false;
                    } catch (DeleteException ex) {
                        System.out.println("Error while deleting: " + ex.getMessage());
                    }
                    break;
                case "view":
                    try {
                        handleViewCommand();
                        needToPrintHelper = false;
                    } catch (ViewException ex) {
                        System.out.println("Error while visualizing sets: " + ex.getMessage());
                    }
                    break;
                case "report":
                    try {
                        handleReportCommand();
                        needToPrintHelper = false;
                    } catch (ReportException ex) {
                        System.out.println("Error while visualizing sets: " + ex.getMessage());
                    }
                    break;
                case "clear":
                    // Ask user for further confirmation.
                    ReplUtils.askUserForConfirmation(GenericUtils.getNegativeResponses());
                    String wishesToClear = scanner.nextLine();
                    if (GenericUtils.isClearingDenied(wishesToClear)) {
                        ReplUtils.printDenial();
                        continue;
                    }

                    try {
                        handleClearCommand(fsManager);
                        needToPrintHelper = false;
                    } catch (FsException ex) {
                        System.out.println("Error while clearing csv file: " + ex.getMessage());
                    }
                    break;
                default:
                    System.out.println("Unrecognized command. Please try again.");
            }
        }
    }

    /**
     * Handles the "add" command by determining the type (income or expense)
     * and delegating to the appropriate manager.
     *
     * @param commands A list of parsed command-line arguments.
     * @throws AddException If the command is malformed or the addition fails.
     */
    public static void handleAddCommand(ArrayList<String> commands) throws AddException {
        // TODO also add in file.
        TypeOfStatement typeOfStatement = GenericUtils.fetchTypeOfStatement(commands);
        if (typeOfStatement == TypeOfStatement.UNRECOGNIZED) {
            ReplUtils.handleWrongTypeOfStatement();
        } else if (commands.size() < numOfMandatoryAddParams) {
            ReplUtils.handleNotEnoughMandatoryParams();
        } else if (commands.size() > numOfMaximumAddParams) {
            ReplUtils.handleTooManyParams();
        } else {
            if (typeOfStatement == TypeOfStatement.EXPENSE) {
                expenseManager.addExpense(commands);
            } else {
                incomeManager.addIncome(commands);
            }
            ReplUtils.handleAddSuccess(typeOfStatement);
        }
    }

    /**
     * Handles the "delete" command by locating an entry (expense or income)
     * by its ID and removing it.
     *
     * @param commands A list of parsed command-line arguments.
     * @throws DeleteException If the ID is missing, invalid, or not found.
     */
    public static void handleDeleteCommand(ArrayList<String> commands) throws DeleteException {
        // TODO delete also from file.
        int idFromCommands;
        try {
            idFromCommands = Integer.parseInt(commands.get(1));
        } catch (IndexOutOfBoundsException ex) {
            throw new DeleteException("ID not present, please specify it to delete cashflow statement.", new Throwable());
        } catch (NumberFormatException ex) {
            throw new DeleteException("ID cannot be parsed correctly to a number.", new Throwable());
        }

        Expense expenseToBeDeleted = expenseManager.getExpenseWithMatchingId(idFromCommands);
        Income incomeToBeDeleted = incomeManager.getIncomeWithMatchingId(idFromCommands);

        if (expenseToBeDeleted != null) {
            expenseManager.deleteExpense(expenseToBeDeleted);
        } else if (incomeToBeDeleted != null) {
            incomeManager.deleteIncome(incomeToBeDeleted);
        } else {
            throw new DeleteException("ID " + idFromCommands + " does not exist.", new Throwable());
        }
        ReplUtils.handleDeleteSuccess(idFromCommands);
    }

    /**
     * Handles the "view" command by displaying all recorded income and expense statements.
     *
     * @throws ViewException If there are no statements to display.
     */
    public static void handleViewCommand() throws ViewException {
        if (expenseManager.getExpensesSize() == 0 && incomeManager.getIncomesSize() == 0) {
            throw new ViewException("Nothing to be visualized since there is no statement that has been added.", new Throwable());
        }
        ReplUtils.printAllStatements(expenseManager, incomeManager);
    }

    /**
     * Handles the "report" command by retrieving/calculating all needed values and calling
     * printCashFlowReport from ReplUtils class.
     *
     * @throws ReportException If there are no statements to report.
     */
    public static void handleReportCommand() throws ReportException {
        if (expenseManager.getExpensesSize() == 0 && incomeManager.getIncomesSize() == 0) {
            throw new ReportException("No cash flow statements to account for.", new Throwable());
        }

        String currency = GenericUtils.getCurrency();
        double totalFromIncomes = incomeManager.getTotalIncomesAmount();
        double totalFromExpenses = -(expenseManager.getTotalExpensesAmount());
        double netCashFlow = totalFromIncomes - totalFromExpenses;

        ReplUtils.printCashFlowReport(currency, totalFromIncomes, totalFromExpenses, netCashFlow);
    }

    /**
     * Handles the "clear" command by deleting data/cash_flow_statements.csv file and creating a fresh version.
     * Before doing so, it creates a backup in data/archive.
     *
     * @throws FsException If there have been issues in clearing file.
     */
    public static void handleClearCommand(FsManager fsManager) throws FsException {
        // Backup management.
        try {
            fsManager.backupFile();
        } catch (IOException ex) {
            throw new FsException("A backup file could not be created.", ex);
        }

        // Clearing and recreating file if successful.
        if (fsManager.clearFile()) {
            try {
                fsManager.initFsManager();
            } catch (IOException ex) {
                throw new FsException("File could not be created again.", ex);
            }
        } else {
            throw new FsException("There have been issues clearing the file.", new Throwable());
        }

        ReplUtils.printClearSuccess(fsManager.getBackupPath());
    }
}