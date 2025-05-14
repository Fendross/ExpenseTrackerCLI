package main.java;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import main.java.core.expense.Expense;
import main.java.core.expense.ExpenseManager;
import main.java.core.income.Income;
import main.java.core.income.IncomeManager;

import main.java.exceptions.AddException;
import main.java.exceptions.DeleteException;
import main.java.exceptions.ViewException;
import main.java.exceptions.ReportException;

import main.java.utils.GenericUtils;
import main.java.utils.GenericUtils.TypeOfStatement;
import main.java.utils.ReplUtils;

import static main.java.utils.GenericUtils.numOfMandatoryAddParams;
import static main.java.utils.GenericUtils.numOfMaximumAddParams;

public class ExpenseTrackerCLI {

    /** Manages all expense-related operations. */
    public static ExpenseManager expenseManager = new ExpenseManager();

    /** Manages all income-related operations. */
    public static IncomeManager incomeManager = new IncomeManager();

    /**
     * The main entry point of the Expense Tracker CLI application.
     * It initializes the REPL loop and handles user commands.
     *
     * @param args command-line arguments (currently expects none)
     */
    public static void main(String[] args) {
        int expectedArgs = 0;
        if (args.length != expectedArgs) {
            System.out.println("Too many arguments. Expected arguments: " + expectedArgs);
            System.exit(1);
        }

        boolean wasLastCommandHelp = false;

        ReplUtils.welcomeUser();
        while (true) {
            Scanner scanner = new Scanner(System.in);
            wasLastCommandHelp = ReplUtils.askForInput(wasLastCommandHelp);

            String task = scanner.nextLine();
            ArrayList<String> commands = new ArrayList<>(List.of(task.trim().split(" ")));

            String leadCommand = commands.get(0);
            switch (leadCommand.toLowerCase()) {
                case "exit":
                    scanner.close();
                    System.exit(0);
                case "help":
                    ReplUtils.handleHelpCommand();
                    wasLastCommandHelp = true;
                    continue;
                case "add":
                    try {
                        handleAddCommand(commands);
                        wasLastCommandHelp = false;
                    } catch (AddException ex) {
                        System.out.println("Error while inserting: " + ex.getMessage());
                    }
                    break;
                case "delete":
                    try {
                        handleDeleteCommand(commands);
                        wasLastCommandHelp = false;
                    } catch (DeleteException ex) {
                        System.out.println("Error while deleting: " + ex.getMessage());
                    }
                    break;
                case "view":
                    try {
                        handleViewCommand();
                        wasLastCommandHelp = false;
                    } catch (ViewException ex) {
                        System.out.println("Error while visualizing sets: " + ex.getMessage());
                    }
                    break;
                case "report":
                    try {
                        handleReportCommand();
                        wasLastCommandHelp = false;
                    } catch (ReportException ex) {
                        System.out.println("Error while visualizing sets: " + ex.getMessage());
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
     * @throws ReportException Placeholder exception for future implementation.
     */
    public static void handleReportCommand() throws ReportException {
        if (expenseManager.getExpensesSize() == 0 && incomeManager.getIncomesSize() == 0) {
            throw new ReportException("No cash flow statements to account for.", new Throwable());
        }

        String currency = GenericUtils.getCurrency();
        double totalFromIncomes = incomeManager.getTotalIncomesAmount();
        double totalFromExpenses = -(expenseManager.getTotalExpensesAmount());
        double netBalance = totalFromIncomes - totalFromExpenses;

        ReplUtils.printCashFlowReport(currency, totalFromIncomes, totalFromExpenses, netBalance);
    }
}