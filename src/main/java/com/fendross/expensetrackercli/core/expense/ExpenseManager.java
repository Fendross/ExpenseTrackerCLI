package com.fendross.expensetrackercli.core.expense;

import com.fendross.expensetrackercli.exceptions.AddException;
import com.fendross.expensetrackercli.utils.GenericUtils;

import java.util.ArrayList;
import java.util.Date;

public class ExpenseManager {
    public ArrayList<Expense> expenses = new ArrayList<>();
    public ExpenseManager() {}

    public ArrayList<Expense> getExpenses() {
        return this.expenses;
    }

    public int getExpensesSize() {
        return this.getExpenses().size();
    }

    public Expense getExpenseWithMatchingId(int id) {
        if (this.getExpensesSize() == 0) {
            return null;
        }

        Expense expenseFromSearch = new Expense();
        for (Expense expense: this.getExpenses()) {
            if (expense.getId() == id) {
                expenseFromSearch = expense;
            }
        }
        return expenseFromSearch == null ? null : expenseFromSearch;
    }

    public void addExpense(ArrayList<String> commands) throws AddException {
        expenses.add(buildExpense(commands));
    }

    public void deleteExpense(Expense expense) {
        expenses.remove(expense);
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

    public double getTotalExpensesAmount() {
        if (this.getExpensesSize() == 0) {
            return 0;
        }

        double totalExpensesAmount = 0;
        for (Expense expense: this.getExpenses()) {
            totalExpensesAmount += expense.getAmount();
        }
        return totalExpensesAmount;
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
