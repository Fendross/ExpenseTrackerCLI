package utils;

import java.util.ArrayList;

public class GenericUtils {

    public static int id = 0;
    public static boolean hasBeenInitialized = false;
    public enum TypeOfStatement {
        EXPENSE,
        INCOME,
        UNRECOGNIZED
    }

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

    public static int getCurrentGlobalId() {
        return id;
    }

    public static void incrementGlobalId() {
        id += 1;
    }

    public static int nextGlobalId() {
        if (!hasBeenInitialized) {
            hasBeenInitialized = true;
            return id;
        }
        incrementGlobalId();
        return id;
    }
}
