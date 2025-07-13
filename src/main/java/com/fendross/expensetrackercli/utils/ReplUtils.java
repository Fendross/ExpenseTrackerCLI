package com.fendross.expensetrackercli.utils;

public class ReplUtils {

    /**
     * Displays a list of possible inputs to the user.
     */
    public static void displayMenu() {
        System.out.println("======= ExpenseTrackerCLI - Manage your financial situation =======");
        System.out.println("1 - View All Cash Flow Statements.");
        System.out.println("2 - Add a New Cash Flow Statement.");
        System.out.println("3 - Delete a Cash Flow Statement.");
        System.out.println("4 - Visualize a Cash Flow Report.");
        System.out.println("5 - Clear all Cash Flow Statements.");
        System.out.println("6 - Change System Currency.");
        System.out.println("0 - Exit the program.");
        System.out.println("===================================================================");
    }

    /**
     * Says goodbye to user upon selecting to exit the program.
     */
    public static void sayGoodbye() {
        System.out.println("See you next time!");
    }
}
