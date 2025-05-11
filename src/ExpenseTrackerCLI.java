import core.expense.Expense;
import core.income.Income;

public class ExpenseTrackerCLI {
    public static void main(String[] args) {
        int expectedArgs = 0;
        if (args.length != expectedArgs) {
            System.out.println("Too many arguments. Expected arguments: " + expectedArgs);
            System.exit(1);
        }

        Expense expense = new Expense();
        Income income = new Income();

        expense.printExpense();
        income.printIncome();
        // TODO build the CLI workflow.
    }
}