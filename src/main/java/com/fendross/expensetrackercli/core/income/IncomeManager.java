package com.fendross.expensetrackercli.core.income;


import com.fendross.expensetrackercli.exceptions.AddException;
import com.fendross.expensetrackercli.utils.GenericUtils;

import java.util.ArrayList;
import java.util.Date;

public class IncomeManager {
    public ArrayList<Income> incomes = new ArrayList<>();
    public IncomeManager() {}

    public ArrayList<Income> getIncomes() {
        return this.incomes;
    }

    public int getIncomesSize() {
        return this.getIncomes().size();
    }

    public Income getIncomeWithMatchingId(int id) {
        if (this.getIncomesSize() == 0) {
            return null;
        }

        for (Income income: this.getIncomes()) {
            if (income.getId() == id) {
                return income;
            }
        }
        return null;
    }

    public void addIncome(ArrayList<String> commands) throws AddException {
        incomes.add(buildIncome(commands));
    }

    public void deleteIncome(Income income) {
        incomes.remove(income);
    }

    public Income buildIncome(ArrayList<String> commands) throws AddException {
        Income income = new Income();

        // Set all fields.
        try {
            income.setId(GenericUtils.nextGlobalId());
        } catch (Exception ex) {
            throw new AddException(ex.getMessage(), new Throwable());
        }

        try {
            income.setAmount(Double.valueOf(commands.get(2)));
        } catch (IndexOutOfBoundsException ex) {
            throw new AddException("Amount must be present in request.", new Throwable());
        } catch (NumberFormatException ex) {
            throw new AddException("Amount must be a number.", new Throwable());
        }

        try {
            income.setDateOfStatement(new Date());
        } catch (Exception ex) {
            throw new AddException(ex.getMessage(), new Throwable());
        }

        try {
            income.setCategory(commands.get(3).length() > 0 ? commands.get(3) : "");
        } catch (IndexOutOfBoundsException ex) {
            throw new AddException("Category must be present.", new Throwable());
        }

        try {
            income.setSubcategory(commands.get(4).length() > 0 ? commands.get(4) : "");
        } catch (IndexOutOfBoundsException ex) {
            income.setSubcategory("");
        }

        try {
            income.setDescription(commands.get(5).length() > 0 ? commands.get(5) : "");
        } catch (IndexOutOfBoundsException ex) {
            income.setDescription("");
        }

        return income;
    }

    public double getTotalIncomesAmount() {
        if (this.getIncomesSize() == 0) {
            return 0;
        }

        double totalIncomesAmount = 0;
        for (Income income: this.getIncomes()) {
            totalIncomesAmount += income.getAmount();
        }
        return totalIncomesAmount;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Income e: incomes) {
            str.append(e.toString() + "\n");
        }
        return str.toString();
    }
}
