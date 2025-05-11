

import core.expense.Expense;
import core.expense.ExpenseManager;
import core.income.Income;
import core.income.IncomeManager;

import java.util.Scanner;

public class ExpenseTrackerCLI {
    public static void main(String[] args) {
        int expectedArgs = 0;
        if (args.length != expectedArgs) {
            System.out.println("Too many arguments. Expected arguments: " + expectedArgs);
            System.exit(1);
        }

        while (true) {
            // TODO build the CLI workflow.
            System.out.println("Hello from ExpenseTrackerCLI.");

            Scanner myObj = new Scanner(System.in);  // Create a Scanner object
            System.out.println("Enter username:");

            String userName = myObj.nextLine();  // Read user input
            System.out.println("Username is: " + userName);  // Output user input

            if (userName.equals("exit")) {
                System.exit(0);
            }
        }
    }

    public void getUserInput() {

    }
}