package com.fendross.expensetrackercli.core.cashflowstatement;

import com.fendross.expensetrackercli.utils.GenericUtils.TypeOfStatement;

import java.util.Date;

public interface CashFlow {

    /**
     * A CashFlow consists of:
     *  - Unique identifier (an integer)
     *  - Type of statement (expense or income)
     *  - Amount
     *  - Date of statement
     *  - Category of statement
     *  - Subcategory of statement
     *  - Description of statement
     */

    // Getters and Setters
    TypeOfStatement getTypeOfStatement(); // Fixed type.

    // ID.
    int getId();
    void setId(int id);

    // Amount.
    double getAmount();
    void setAmount(double amount);

    // DateOfStatement.
    Date getDateOfStatement();
    void setDateOfStatement(Date dateOfStatement);

    // Category.
    String getCategory();
    void setCategory(String category);

    // Subcategory.
    String getSubcategory();
    void setSubcategory(String subcategory);

    // Description.
    String getDescription();
    void setDescription(String description);

}
