package core.expense;

import core.cashflowstatement.CashFlowStatement;
public class Expense implements CashFlowStatement {
    // TODO implement constructor and methods.
    String typeOfStatement = "Expense";
    public Expense() {}

    public String getTypeOfStatement() {
        return typeOfStatement;
    }

    public void printExpense() {
        System.out.println(getTypeOfStatement());
    }
}
