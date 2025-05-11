package utils;

import java.util.ArrayList;

public class GenericUtils {
    public static int id = initGlobalId();
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

    public static int initGlobalId() {
        return 0;
    }

    public static int getGlobalId() {
        return id;
    }

    public static int updateGlobalId() {
        return id++;
    }
}
