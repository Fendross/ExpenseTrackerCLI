package core.expense;

import core.cashflowstatement.CashFlowStatement;

import utils.GenericUtils.TypeOfStatement;
import static utils.GenericUtils.df;
import static utils.GenericUtils.sdf;

import java.util.Date;

public class Expense implements CashFlowStatement {
    // Class Fields.
    int id;
    double amount;
    Date dateOfStatement;
    String category;
    String subcategory;
    String description;

    public Expense() {}

    public TypeOfStatement getTypeOfStatement() {
        return TypeOfStatement.EXPENSE;
    }

    // ID.
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // Amount.
    public double getAmount() {
        return this.amount;
    }
    public void setAmount(double amount) {
        this.amount = -amount;
    }

    // DateOfStatement.
    public Date getDateOfStatement() {
        return dateOfStatement;
    }
    public void setDateOfStatement(Date dateOfStatement) {
        this.dateOfStatement = dateOfStatement;
    }

    // Category.
    public String getCategory() {
        return this.category;
    }
    public void setCategory(String category) {
        this.category = category;
    }

    // Subcategory.
    public String getSubcategory() {
        return this.subcategory;
    }
    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    // Description.
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "{ id: " + this.getId() + "; amount: " + df.format(this.getAmount()) + "; dateOfStatement: " + sdf.format(this.getDateOfStatement()) + "; category: " + this.getCategory() + "; subcategory: " + this.getSubcategory() + "; description: " + this.getDescription() + " }";
    }
}
