import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import core.expense.Expense;
import core.expense.ExpenseManager;
import core.income.Income;
import core.income.IncomeManager;

import exceptions.AddException;
import exceptions.DeleteException;
import exceptions.ViewException;
import exceptions.ReportException;

import utils.GenericUtils;
import utils.GenericUtils.TypeOfStatement;
import utils.ReplUtils;

public class ExpenseTrackerCLI {

    public static ExpenseManager expenseManager = new ExpenseManager();
    public static IncomeManager incomeManager = new IncomeManager();

    public static void main(String[] args) {
        // Handle wrong num of arguments.
        int expectedArgs = 0;
        if (args.length != expectedArgs) {
            System.out.println("Too many arguments. Expected arguments: " + expectedArgs);
            System.exit(1);
        }

        // Init variables.
        TypeOfStatement typeOfStatement;
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

    public static void handleAddCommand(ArrayList<String> commands) throws AddException {
        TypeOfStatement typeOfStatement = GenericUtils.fetchTypeOfStatement(commands);
        if (typeOfStatement == TypeOfStatement.UNRECOGNIZED) {
            ReplUtils.handleWrongTypeOfStatement();
        } else if (commands.size() < 4) {
            ReplUtils.handleNotEnoughMandatoryParams();
        } else {
            if (typeOfStatement == TypeOfStatement.EXPENSE) {
                expenseManager.addExpense(commands);
            } else {
                incomeManager.addIncome(commands);
            }
            ReplUtils.handleAddSuccess(typeOfStatement);
        }
    }

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

    public static void handleViewCommand() throws ViewException {
        ArrayList<Expense> expenses = expenseManager.getExpenses();
        ArrayList<Income> incomes = incomeManager.getIncomes();
        if (expenses.size() == 0 && incomes.size() == 0) {
            throw new ViewException("Nothing to be visualized since there is no statement that has been added.", new Throwable());
        }
        ReplUtils.printAllStatements(expenseManager, incomeManager);
    }

    public static void handleReportCommand() throws ReportException {
        // TODO feature #7.
    }
}