package core.income;

import core.cashflowstatement.CashFlowStatement;
public class Income implements CashFlowStatement {
    // TODO implement constructor and methods.
    String typeOfStatement = "Income";
    public Income() {}

    public String getTypeOfStatement() {
        return typeOfStatement;
    }

    public void printIncome() {
        System.out.println(getTypeOfStatement());
    }
}
