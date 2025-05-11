package core.expense;

import utils.GenericUtils;

import java.util.ArrayList;
import java.util.Date;

public class ExpenseManager {
    // TODO implement operations and list them in the API docs.
    ArrayList<Expense> expenses = new ArrayList<>();
    public ExpenseManager() {}

    public void addExpense(ArrayList<String> commands) {
        expenses.add(buildExpense(commands));
        System.out.println("Added expense from ExpenseManager.");
    }

    public void deleteExpense(int id) {
        System.out.println("Deleted expense from ExpenseManager.");
    }

    public Expense buildExpense(ArrayList<String> commands) {
        Expense expense = new Expense();

        // Set all fields.
        expense.setId(GenericUtils.getGlobalId());
        expense.setAmount(Double.valueOf(commands.get(2)));
        expense.setDateOfStatement(new Date());
        expense.setCategory(commands.get(3).length() > 0 ? commands.get(3) : "");
        //expense.setSubcategory(commands.get(4).length() > 0 ? commands.get(4) : "");
        //expense.setDescription(commands.get(5).length() > 0 ? commands.get(5) : "");

        return expense;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Expense e: expenses) {
            str.append(e.toString());
        }
        return str.toString();
    }
}
