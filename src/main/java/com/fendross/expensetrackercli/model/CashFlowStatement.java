package com.fendross.expensetrackercli.model;

import com.fendross.expensetrackercli.utils.GenericUtils.TypeOfStatement;

import java.time.LocalDate;

public class CashFlowStatement {
    // Class Fields.
    private int id;
    private TypeOfStatement cfType;
    private double amount;
    private LocalDate cfDate;
    private String category;
    private String description;

    // New expenses.
    public CashFlowStatement(TypeOfStatement cfType, double amount, LocalDate cfDate, String category, String description) {
        this.cfType = cfType;
        this.amount = amount;
        this.cfDate = cfDate;
        this.category = category;
        this.description = description;
    }

    // To retrieve an expense from DB.
    public CashFlowStatement(int id, TypeOfStatement cfType, double amount, LocalDate cfDate, String category, String description) {
        this.id = id;
        this.cfType = cfType;
        this.amount = amount;
        this.cfDate = cfDate;
        this.category = category;
        this.description = description;
    }


    // ID.
    public int getId() {
        return this.id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // ID.
    public TypeOfStatement getCfType() {
        return this.cfType;
    }
    public void setCfType(TypeOfStatement cfType) {
        this.cfType = cfType;
    }

    // Amount.
    public double getAmount() {
        return this.amount;
    }
    public void setAmount(double amount) {
        this.amount = amount;
    }

    // CfDate.
    public LocalDate getCfDate() {
        return this.cfDate;
    }
    public void setCfDate(LocalDate dateOfStatement) {
        this.cfDate = dateOfStatement;
    }

    // Category.
    public String getCategory() {
        return this.category;
    }
    public void setCategory(String category) {
        this.category = category;
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
        return String.format("%s[id=%d, cfType='%s', amount=%.2f, date='%s', category='%s', description='%s']",
                cfType, id, cfType, amount, cfDate, category, description);
    }
}
