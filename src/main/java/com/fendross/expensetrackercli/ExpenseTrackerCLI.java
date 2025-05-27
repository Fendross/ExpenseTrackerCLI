package com.fendross.expensetrackercli;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

import com.fendross.expensetrackercli.cashflowstatement.CashFlowStatement;
import com.fendross.expensetrackercli.cashflowstatement.CashFlowStatementDAO;

import com.fendross.expensetrackercli.db.DatabaseManager;

import com.fendross.expensetrackercli.utils.GenericUtils;
import com.fendross.expensetrackercli.utils.GenericUtils.TypeOfStatement;
import com.fendross.expensetrackercli.utils.ReplUtils;

public class ExpenseTrackerCLI {
    private final CashFlowStatementDAO cfsDAO;
    private final Scanner scanner;

    public ExpenseTrackerCLI() {
        this.cfsDAO = new CashFlowStatementDAO();
        this.scanner = new Scanner(System.in);

        DatabaseManager.initDb();
    }

    public void run() {
        boolean running = true;

        while (running) {
            ReplUtils.displayMenu();
            int choice = getIntValue("Enter your choice: ");

            switch (choice) {
                case 1:
                    viewAll();
                    break;
                case 2:
                    addItem();
                    break;
                case 3:
                    deleteItem();
                    break;
                case 0:
                    running = false;
                    ReplUtils.sayGoodbye();
                    break;
                default:
                    System.out.println("Unknown command. Refer to the menu list to make a suitable choice.");
                    break;
            }
            System.out.println();
        }

        System.exit(0);
    }

    private void viewAll() {
        System.out.println("== Listing all Cash Flow Statement ==\n");

        List<CashFlowStatement> cashFlowStatements = cfsDAO.getAllCashFlowStatements();
        if (cashFlowStatements.size() > 0) {
            for (CashFlowStatement cfs: cashFlowStatements) {
                System.out.println(cfs);
            }
        } else {
            System.out.println("No cash flow statements in the database.");
        }
    }

    private void addItem() {
        System.out.println("== Adding Cash Flow Statement ==");

        // cfType.
        System.out.println("Enter the type of statement (possible values: EXPENSE, INCOME): ");
        TypeOfStatement cfType = GenericUtils.getTypeOfStatement(scanner.nextLine());

        // amount.
        double amount = getDoubleValue("Enter the nominal amount: ");

        // cfDate.
        LocalDate cfDate = getDateValue("Enter the value date: ");

        // category.
        System.out.println("Enter the category: ");
        String category = scanner.nextLine();

        // description.
        System.out.println("Enter the description: ");
        String description = scanner.nextLine();

        CashFlowStatement cfs = new CashFlowStatement(cfType, amount, cfDate, category, description);
        int result = cfsDAO.addCashFlowStatement(cfs);
        if (result == 0) {
            System.out.println("Correctly added the provided cash flow statement.");
        }
    }

    public void deleteItem() {
        System.out.println("== Deleting Cash Flow Statement ==");
        boolean statementExists = true;

        int id = getIntValue("Please input the id of the statement you wish to delete: ");
        CashFlowStatement cfsToDelete = cfsDAO.getCashFlowStatementById(id);
        if (cfsToDelete == null) {
            System.out.println("Item with id: " + id + " does not exist.");
            statementExists = false;
        }

        if (statementExists) {
            System.out.println("Statement to be deleted: " + cfsToDelete + "\n");
            System.out.println("Are you sure you want to proceed with the item deletion? (y/n)");
            String validation = scanner.nextLine().trim();
            if (validation.toLowerCase().equals("y")) {
                int result = cfsDAO.deleteCashFlowStatement(id);
                if (result != 0) {
                    System.out.println("The item could not be deleted properly.");
                }
            }
            System.out.println("Correctly deleted item with id: " + id + ".");
        }
    }

    private int getIntValue(String input) {
        while (true) {
            System.out.println(input);
            try {
                String parsedInput = scanner.nextLine();
                return Integer.parseInt(parsedInput);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input: an integer is required. Please try again.");
            }
        }
    }

    private double getDoubleValue(String input) {
        while (true) {
            System.out.println(input);
            try {
                String parsedInput = scanner.nextLine();
                return Double.parseDouble(parsedInput);
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input: an integer is required. Please try again.");
            }
        }
    }

    private LocalDate getDateValue(String input) {
        while (true) {
            System.out.println(input);
            try {
                String parsedInput = scanner.nextLine();
                return LocalDate.parse(parsedInput, GenericUtils.getDtf());
            } catch (DateTimeParseException ex) {
                System.out.println("Invalid input: a date in the format 'yyyy-MM-dd' is required. Please try again.");
            }
        }
    }

    /**
     * The main entry point of the Expense Tracker CLI application.
     * It initializes the ExpenseTrackerCLI class and runs it.
     *
     * @param args command-line arguments (currently expects none).
     */
    public static void main(String[] args) {
        int expectedArgs = 0;
        if (args.length != expectedArgs) {
            System.out.println("Too many arguments. Expected arguments: " + expectedArgs);
            System.exit(1);
        }

        ExpenseTrackerCLI expenseTrackerCLI = new ExpenseTrackerCLI();
        expenseTrackerCLI.run();
    }
}