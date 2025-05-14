package com.fendross.expensetrackercli.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class GenericUtils {

    // Shared variables.
    public static int id = 0;
    public static boolean hasBeenInitialized = false;
    public static int numOfMandatoryAddParams = 4;
    public static int numOfMaximumAddParams = 6;

    // Format main.java.com.fendross.expensetrackercli.utils.
    public static DecimalFormat df = new DecimalFormat("#0.00");
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Gets the currency that cashflow statements refer to.
     *
     * @return "EUR" Euro currency.
     */
    public static String getCurrency() {
        return "EUR";
    }

    public static int getNumOfMandatoryAddParams() {
        return numOfMandatoryAddParams;
    }

    public static int getNumOfMaximumAddParams() {
        return numOfMaximumAddParams;
    }

    public enum TypeOfStatement {
        EXPENSE,
        INCOME,
        UNRECOGNIZED
    }

    /**
     * Determines the type of statement (income or expense) based on the user command.
     *
     * @param commands A list of command-line arguments where the second element specifies the type.
     * @return A TypeOfStatement value based on the input.
     * @throws ArrayIndexOutOfBoundsException if the command list does not contain enough elements.
     */
    public static TypeOfStatement fetchTypeOfStatement(ArrayList<String> commands) throws ArrayIndexOutOfBoundsException {
        String strTypeOfStatement = commands.get(1).toUpperCase();
        if (strTypeOfStatement.equals(TypeOfStatement.EXPENSE.name())) {
            return TypeOfStatement.EXPENSE;
        } else if (strTypeOfStatement.equals(TypeOfStatement.INCOME.name())) {
            return TypeOfStatement.INCOME;
        } else {
            return TypeOfStatement.UNRECOGNIZED;
        }
    }

    /**
     * Retrieves the current global ID without incrementing it.
     *
     * @return The current global identifier.
     */
    public static int getCurrentGlobalId() {
        return id;
    }

    /**
     * Increments the global ID counter by one.
     */
    public static void incrementGlobalId() {
        id += 1;
    }

    /**
     * Returns the next available global ID and increments the counter.
     * On first call, returns the current ID without incrementing.
     *
     * @return The next global identifier.
     */
    public static int nextGlobalId() {
        if (!hasBeenInitialized) {
            hasBeenInitialized = true;
            return id;
        }
        incrementGlobalId();
        return id;
    }
}
