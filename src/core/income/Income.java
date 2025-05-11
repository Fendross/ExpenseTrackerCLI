package core.income;

import core.cashflowstatement.CashFlowStatement;

import java.util.Date;

public class Income implements CashFlowStatement {
    // TODO implement constructor and methods.

    // Class Fields.
    double amount;
    Date dateOfStatement;
    String category;
    String subcategory;
    String description;

    public Income() {}

    @Override
    public String getTypeOfStatement() {
        return "Income";
    }

    // Amount.
    public double getAmount() {
        return this.amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
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

    // View all items.
    public void viewAllItems() {
        // TODO
    }
}
