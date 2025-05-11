package utils;

public class ReplUtils {
    public static void welcomeUser() {
        System.out.println("** ExpenseTrackerCLI - Manage your cashflow statements **");
    }

    public static boolean askForInput(boolean wasLastCommandHelp) {
        System.out.println("Please input a task down below.");
        if (!wasLastCommandHelp) {
            System.out.println("If unsure of what to do, simply type help.");
            return true;
        }
        return false;
    }

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

    public static void handleWrongTypeOfStatement() {
        System.out.println("Wrong type of cashflow statement.");
        System.out.println("Allowed values are: ");
        System.out.println("- expense");
        System.out.println("- income");

        separateBlocks();
    }

    public static void separateBlocks() {
        System.out.println("======================");
    }
}
