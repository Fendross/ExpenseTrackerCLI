import utils.GenericUtils;
import utils.ReplUtils;

import core.expense.Expense;
import core.expense.ExpenseManager;
import core.income.Income;
import core.income.IncomeManager;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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
            ArrayList<String> commands = new ArrayList<String>(List.of(task.trim().split(" ")));

            String leadCommand = commands.get(0);
            if (leadCommand.equals("help")) {
                ReplUtils.handleHelpCommand();
                wasLastCommandHelp = true;
                ReplUtils.separateBlocks();
                continue;
            }

            if (leadCommand.equals("exit")) {
                scanner.close();
                System.exit(0);
            }
        }
    }
}