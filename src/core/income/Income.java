package core.income;

import core.cashflowstatement.CashFlowStatement;
public class Income implements CashFlowStatement {
    // TODO implement constructor and methods.
    public Income() {}

    @Override
    public int addStatement(double amount) {
        return addIncome(amount);
    }

    public int addIncome (double amount) {
        return 0;
    }

    public String getTypeOfStatement() {
        return "Income";
    }

    public void printIncome() {
        System.out.println(getTypeOfStatement());
    }
}
