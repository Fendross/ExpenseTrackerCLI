package core.cashflowstatement;

import java.util.Date;

public interface CashFlowStatement {
    /**
     * A CashFlowStatement consists of:
     *  - Type of statement (expense or income)
     *  - Amount
     *  - Date of statement
     *  - Category of statement
     *  - Subcategory of statement
     *  - Description of statement
     */

    // Getters and Setters
    String getTypeOfStatement(); // Fixed type.

    // Amount
    double getAmount();
    void setAmount(double amount);

    // DateOfStatement
    Date getDateOfStatement();
    void setDateOfStatement(Date dateOfStatement);

    // Category
    String getCategory();
    void setCategory(String category);

    // Subcategory
    String getSubcategory();
    void setSubcategory(String subcategory);

    // Description
    String getDescription();
    void setDescription(String description);

    // View all items
    void viewAllItems();
}
