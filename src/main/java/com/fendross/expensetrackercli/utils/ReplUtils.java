package com.fendross.expensetrackercli.utils;

import com.fendross.expensetrackercli.core.expense.ExpenseManager;
import com.fendross.expensetrackercli.core.income.IncomeManager;

public class ReplUtils {

    /**
     * Prints a welcome message to the user upon starting the application.
     */
    public static void welcomeUser() {
        System.out.println("*** ExpenseTrackerCLI - Manage your cash flow statements ***");
        separateBlocks();
    }

    /**
     * Prompts the user for input and conditionally displays help instructions.
     *
     * @param needToPrintHelper Indicating if there's the need to print the helper string.
     * @return {@code true} if help text was shown, otherwise {@code false}.
     */
    public static boolean askForInput(boolean needToPrintHelper) {
        System.out.println("Please input a task down below.");
        if (!needToPrintHelper) {
            System.out.println("If unsure of what to do, simply type 'help' or 'exit' to leave the program.");
            return true;
        }
        return false;
    }

    /**
     * Asks user if he wants to proceed with the command he previously entered.
     * The string negativeStatements, from GenericUtils constants, represents the choices the user has.
     *
     * @param negativeStatements A string representing all possible ways to express dissent.
     */
    public static void askUserForConfirmation(String negativeStatements) {
        System.out.println("Are you sure you want to proceed with the previous command?");
        System.out.println("Type one of the following to escape it:\n" + negativeStatements);
    }

    /**
     * Displays all current expense and income statements using the provided managers.
     *
     * @param expenseManager The ExpenseManager instance managing expense records.
     * @param incomeManager The IncomeManager instance managing income records.
     */
    public static void printAllStatements(ExpenseManager expenseManager, IncomeManager incomeManager) {
        if (expenseManager.getExpenses().size() > 0) { printExpenses(expenseManager); }
        if (incomeManager.getIncomes().size() > 0) { printIncomes(incomeManager); }

        ReplUtils.separateBlocks();
    }

    /**
     * Displays the current list of expense statements.
     *
     * @param expenseManager The ExpenseManager instance.
     */
    public static void printExpenses(ExpenseManager expenseManager) {
        System.out.println("List of Expenses: \n" + expenseManager.toString());
    }

    /**
     * Displays the current list of income statements.
     *
     * @param incomeManager The IncomeManager instance.
     */
    public static void printIncomes(IncomeManager incomeManager) {
        System.out.println("List of Incomes: \n" + incomeManager.toString());
    }

    /**
     * Informs the user that the clear command finished successfully.
     *
     * @param backupPath backup file path.
     */
    public static void printClearSuccess(String backupPath) {
        System.out.println("The file was successfully cleared, after creating a backup copy: " + backupPath + ".");

        separateBlocks();
    }

    public static void printDenial() {
        System.out.println("Command was not executed.");

        separateBlocks();
    }

    /**
     * Informs the user that a csv was created.
     *
     * @param csvPath The relative path for the csv.
     */
    public static void handleFileCreation(String csvPath) {
        System.out.println("NOTICE: a new csv in " + csvPath + " was created.");

        separateBlocks();
    }

    /**
     * Prints usage instructions and available commands for the CLI application.
     */
    public static void handleHelpCommand() {
        System.out.println("You can add, delete or simply view all expenses and income statements.");

        System.out.println("To add one, use this input as an example:");
        System.out.println("add <income, expense (choose one)> <amount> <category> <subcategory> <description>");
        System.out.println("For now, the mandatory fields are: type of statement, amount and category.\n");

        System.out.println("To delete all, use this input as an example:");
        System.out.println("delete <id>\n");

        System.out.println("To view all (especially to retrieve an id), use this input as an example:");
        System.out.println("view <id (optional, if missing will print all)>");

        separateBlocks();
    }

    /**
     * Prints a minimal cash flow report with:
     *  - Total amount of incomes
     *  - Total amount of expenses
     *  - Net cash flow
     *
     * @param currency Currency of the balance
     * @param totalFromIncomes Total amount of all incomes
     * @param totalFromExpenses Total amount of all expenses
     * @param netCashFlow Net cash flow
     */
    public static void printCashFlowReport(String currency, double totalFromIncomes, double totalFromExpenses, double netCashFlow) {
        separateBlocks();
        System.out.println("*** Cash Flow Report ***\n");
        System.out.println("* Cash Flow from Incomes: " + currency + " " + totalFromIncomes);
        System.out.println("* Cash Flow from Expenses: " + currency + " " + totalFromExpenses);
        System.out.println("\n** Net Cash Flow: " + currency + " " + netCashFlow);
        separateBlocks();
    }

    /**
     * Notifies the user of an unrecognized type of statement and lists valid options.
     */
    public static void handleWrongTypeOfStatement() {
        System.out.println("Wrong type of cash flow statement.");
        System.out.println("Allowed values are: ");
        System.out.println("- expense");
        System.out.println("- income");

        separateBlocks();
    }

    /**
     * Notifies the user that required parameters for adding a statement are missing.
     */
    public static void handleNotEnoughMandatoryParams() {
        System.out.println("In order to insert a cashflow statement, at least the first 4 parameters must be passed to the application.");
        System.out.println("Type 'help' for more info.");

        separateBlocks();
    }

    /**
     * Notifies the user that there are too many params while adding a statement.
     */
    public static void handleTooManyParams() {
        System.out.println("Too many parameters. There can be a maximum of 6 (e.g. add expense 100 travel flight economy).");
        System.out.println("Type 'help' for more info.");

        separateBlocks();
    }

    /**
     * Informs the user that a statement was successfully added.
     *
     * @param typeOfStatement The type of statement that was added (expense or income).
     */
    public static void handleAddSuccess(GenericUtils.TypeOfStatement typeOfStatement) {
        System.out.println("Successfully added " + typeOfStatement.name().toLowerCase() + " statement with id: " + GenericUtils.getCurrentGlobalId() + ".");

        separateBlocks();
    }

    /**
     * Informs the user that a statement was successfully deleted.
     *
     * @param id The ID of the deleted statement.
     */
    public static void handleDeleteSuccess(int id) {
        System.out.println("Successfully deleted statement with id: " + id + ".");

        separateBlocks();
    }

    /**
     * Prints a visual separator for improving console readability.
     */
    public static void separateBlocks() {
        System.out.println("================================================================");
    }
}
