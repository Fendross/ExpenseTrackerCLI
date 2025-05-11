package core.expense;

import core.cashflowstatement.CashFlowStatement;
public class Expense implements CashFlowStatement {
    // TODO implement constructor and methods.
    public Expense() {}

    @Override
    public int addStatement(double amount) {
        return addExpense(amount);
    }

    public int addExpense (double amount) {
        return 0;
    }

    public String getTypeOfStatement() {
        return "Expense";
    }

    public void printExpense() {
        System.out.println(getTypeOfStatement());
    }
}
