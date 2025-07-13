package com.fendross.expensetrackercli;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;

import com.fendross.expensetrackercli.model.CashFlowStatement;
import com.fendross.expensetrackercli.dao.CashFlowStatementDAO;

import com.fendross.expensetrackercli.db.DatabaseManager;

import com.fendross.expensetrackercli.utils.GenericUtils;
import com.fendross.expensetrackercli.utils.GenericUtils.TypeOfStatement;
import com.fendross.expensetrackercli.utils.ReplUtils;

// TODO Implementation ideas
// - add currency conversion, if someone wants to print the cash flow report in another currency
// - do not use the deciamlformat class to convert number, it needs to be native in the db. Possible with a couple of triggers, else keep using application-level formatting

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
                case 4:
                    viewCashFlowReport();
                    break;
                case 5:
                    clearAllCashFlows();
                    break;
                case 6:
                    updateSystemCurrency();
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
        LocalDate cfDate = getDateValue("Enter the value date ('yyyy-MM-dd', e.g. 2025-01-13): ");

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
            if (validation.equalsIgnoreCase("y")) {
                int result = cfsDAO.deleteCashFlowStatement(id);
                if (result != 0) {
                    System.out.println("The item could not be deleted properly.");
                }
            }
            System.out.println("Correctly deleted item with id: " + id + ".");
        }
    }

    public void viewCashFlowReport() {
        double totalAmountExpenses = cfsDAO.getTotalAmountOfCfType("EXPENSE");
        double totalAmountIncomes = cfsDAO.getTotalAmountOfCfType("INCOME");
        double netCashFlow = totalAmountIncomes - totalAmountExpenses;
        String reportCurrency = GenericUtils.getCurrency();

        System.out.println("\n== Cash Flow Report ==");
        System.out.println("= Cash Flow from Incomes: " + GenericUtils.df.format(totalAmountIncomes) + " " + reportCurrency + " =");
        System.out.println("= Cash Flow from Expenses: (" + GenericUtils.df.format(totalAmountExpenses) + " " + reportCurrency + ") =\n");
        System.out.println("= Net Cash Flow: " + GenericUtils.df.format(netCashFlow) + " " + reportCurrency + " =\n");
    }

    public void clearAllCashFlows() {
        System.out.println("Are you sure you want to clear all cash flow statements? (y/n)");
        String answer = scanner.nextLine().trim();
        if (answer.equalsIgnoreCase("y")) {
            int result = cfsDAO.truncateCashFlowTable();
            if (result != 0) {
                System.out.println("Items could not be cleared properly.");
            } else {
                System.out.println("Correctly cleared database tables.");
            }
        }
        System.out.println("Chose not to proceed.");
    }

    public void updateSystemCurrency() {
        System.out.println("Please note that currencies are valid only if you insert their 3-letter representation (e.g. 'EUR').");
        System.out.println("The default currency is currently set to EUR.\n");
        System.out.println("Now, please enter the currency you want to switch to: ");
        String userInputCurrency = scanner.nextLine();

        String upperCaseCurrency = userInputCurrency.toUpperCase();
        if (upperCaseCurrency.length() == 3 && GenericUtils.currencyValidator(upperCaseCurrency)) {
            GenericUtils.setCurrency(upperCaseCurrency.toUpperCase());
            System.out.println("Correctly updated system currency to " + upperCaseCurrency + ".");
        } else {
            System.out.println("Currency was not set correctly, please check your input.");
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