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
import utils.ReplUtils;

import javax.swing.text.View;

public class ExpenseTrackerCLI {
    public static void main(String[] args) {
        // Handle wrong num of arguments.
        int expectedArgs = 0;
        if (args.length != expectedArgs) {
            System.out.println("Too many arguments. Expected arguments: " + expectedArgs);
            System.exit(1);
        }

        boolean wasLastCommandHelp = false;

        ReplUtils.welcomeUser();
        ReplUtils.separateBlocks();
        while (true) {
            // TODO build the CLI workflow.
            Scanner scanner = new Scanner(System.in);
            wasLastCommandHelp = ReplUtils.askForInput(wasLastCommandHelp);

            String task = scanner.nextLine();
            ArrayList<String> commands = new ArrayList<>(List.of(task.trim().split(" ")));

            String leadCommand = commands.get(0);
            switch (leadCommand) {
                case "exit":
                    scanner.close();
                    System.exit(0);
                case "help":
                    ReplUtils.handleHelpCommand();
                    wasLastCommandHelp = true;
                    continue;
                case "add":
                    try {
                        handleAddCommand();
                        wasLastCommandHelp = false;
                    } catch (AddException ex) {
                        System.out.println("Error while inserting: " + ex.getMessage());
                    }
                    break;
                case "delete":
                    try {
                        handleDeleteCommand();
                        wasLastCommandHelp = false;
                    } catch (DeleteException ex) {
                        System.out.println("Error while inserting: " + ex.getMessage());
                    }
                    break;
                case "view":
                    try {
                        handleViewCommand();
                        wasLastCommandHelp = false;
                    } catch (ViewException ex) {
                        System.out.println("Error while inserting: " + ex.getMessage());
                    }
                    break;
            }
        }
    }

    public static void handleAddCommand() throws AddException {
        System.out.println("Handled add command.");
        ReplUtils.separateBlocks();
    }

    public static void handleDeleteCommand() throws DeleteException {
        System.out.println("Handled delete command.");
        ReplUtils.separateBlocks();
    }

    public static void handleViewCommand() throws ViewException {
        System.out.println("Handled view command.");
        ReplUtils.separateBlocks();
    }
}