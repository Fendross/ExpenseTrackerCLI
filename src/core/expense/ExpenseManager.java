package core.expense;

import exceptions.AddException;
import utils.GenericUtils;

import java.util.ArrayList;
import java.util.Date;

public class ExpenseManager {
    // TODO implement operations and list them in the API docs.
    ArrayList<Expense> expenses = new ArrayList<>();
    public ExpenseManager() {}

    public void addExpense(ArrayList<String> commands) throws AddException {
        expenses.add(buildExpense(commands));
    }

    public void deleteExpense(int id) {
        // TODO implement deletion.
        System.out.println("Deleted expense from ExpenseManager.");
    }

    public Expense buildExpense(ArrayList<String> commands) throws AddException {
        Expense expense = new Expense();

        // Set all fields.
        try {
            expense.setId(GenericUtils.nextGlobalId());
        } catch (Exception ex) {
            throw new AddException(ex.getMessage(), new Throwable());
        }

        try {
            expense.setAmount(Double.valueOf(commands.get(2)));
        } catch (IndexOutOfBoundsException ex) {
            throw new AddException("Amount must be present in request.", new Throwable());
        } catch (NumberFormatException ex) {
            throw new AddException("Amount must be a number.", new Throwable());
        }

        try {
            expense.setDateOfStatement(new Date());
        } catch (Exception ex) {
            throw new AddException(ex.getMessage(), new Throwable());
        }

        try {
            expense.setCategory(commands.get(3).length() > 0 ? commands.get(3) : "");
        } catch (IndexOutOfBoundsException ex) {
            throw new AddException("Category must be present.", new Throwable());
        }

        try {
            expense.setSubcategory(commands.get(4).length() > 0 ? commands.get(4) : "");
        } catch (IndexOutOfBoundsException ex) {
            expense.setSubcategory("");
        }

        try {
            expense.setDescription(commands.get(5).length() > 0 ? commands.get(5) : "");
        } catch (IndexOutOfBoundsException ex) {
            expense.setDescription("");
        }

        return expense;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Expense e: expenses) {
            str.append(e.toString() + "\n");
        }
        return str.toString();
    }
}
