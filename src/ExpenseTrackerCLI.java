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
import utils.GenericUtils;
import utils.GenericUtils.TypeOfStatement;
import utils.ReplUtils;

public class ExpenseTrackerCLI {

    static ExpenseManager expenseManager = new ExpenseManager();
    static IncomeManager incomeManager = new IncomeManager();
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
        ReplUtils.separateBlocks();
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
                        typeOfStatement = GenericUtils.fetchTypeOfStatement(commands);
                        if (typeOfStatement == TypeOfStatement.UNRECOGNIZED) {
                            ReplUtils.handleWrongTypeOfStatement();
                            continue;
                        }
                        handleAddCommand(commands, typeOfStatement);
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
                default:
                    System.out.println("Unrecognized command. Please try again.");
            }
        }
    }

    public static void handleAddCommand(ArrayList<String> commands, TypeOfStatement typeOfStatement) throws AddException {
        if (typeOfStatement == TypeOfStatement.EXPENSE) {
            expenseManager.addExpense(commands);
        } else {
            incomeManager.addIncome(commands);
        }
        System.out.println("Handled add command.");
        ReplUtils.separateBlocks();
    }

    public static void handleDeleteCommand(ArrayList<String> commands) throws DeleteException {
//        int idFromCommands = Integer.parseInt(commands.get(1));
//        if (expenseManager.checkIdExistence(idFromCommands)) {
//            expenseManager.deleteExpense(idFromCommands);
//        } else if (incomeManager.checkIdExistence(idFromCommands)) {
//            incomeManager.deleteIncome(idFromCommands);
//        } else {
//            throw new DeleteException("ID " + idFromCommands + " does not exist.", new Throwable());
//        }
        System.out.println("Handled delete command.");
        ReplUtils.separateBlocks();
    }

    public static void handleViewCommand() throws ViewException {
        System.out.println("List of Expenses: " + expenseManager.toString() + "\n" + "List of Incomes: " + incomeManager.toString());
        System.out.println("Handled view command.");
        ReplUtils.separateBlocks();
    }
}