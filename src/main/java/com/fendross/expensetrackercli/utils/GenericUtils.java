package com.fendross.expensetrackercli.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class GenericUtils {

    // Shared variables.
    public static int id = 0;
    public static boolean hasBeenInitialized = false;
    public static final String negativeResponses = "n,no,false,quit,del,delete";
    public static String currency = "EUR";
    public static final String CURRENCY_CODES = "AED;AFN;ALL;AMD;ANG;AOA;ARS;AUD;AWG;AZN;BAM;BBD;BDT;BGN;BHD;BIF;BMD;BND;BOB;BOV;BRL;BSD;BTN;BWP;BYN;BZD;CAD;CDF;CHE;CHF;CHW;CLF;CLP;CNY;COP;COU;CRC;CUC;CUP;CVE;CZK;DJF;DKK;DOP;DZD;EGP;ERN;ETB;EUR;FJD;FKP;GBP;GEL;GHS;GIP;GMD;GNF;GTQ;GYD;HKD;HNL;HRK;HTG;HUF;IDR;ILS;INR;IQD;IRR;ISK;JMD;JOD;JPY;KES;KGS;KHR;KMF;KPW;KRW;KWD;KYD;KZT;LAK;LBP;LKR;LRD;LSL;LYD;MAD;MDL;MGA;MKD;MMK;MNT;MOP;MRU;MUR;MVR;MWK;MXN;MXV;MYR;MZN;NAD;NGN;NIO;NOK;NPR;NZD;OMR;PAB;PEN;PGK;PHP;PKR;PLN;PYG;QAR;RON;RSD;RUB;RWF;SAR;SBD;SCR;SDG;SEK;SGD;SHP;SLE;SOS;SRD;SSP;STN;SVC;SYP;SZL;THB;TJS;TMT;TND;TOP;TRY;TTD;TVD;TWD;TZS;UAH;UGX;USD;USN;UYI;UYU;UZS;VED;VEF;VND;VUV;WST;XAF;XCD;XDR;XOF;XPF;XSU;XUA;YER;ZAR;ZMW;ZWL";

    // Format utils.
    public static DecimalFormat df = new DecimalFormat("#0.00");
    private static final DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    /**
     * Gets the currency that cashflow statements refer to.
     *
     * @return "EUR" Euro currency.
     */
    public static String getCurrency() {
        return currency;
    }

    public static void setCurrency(String _currency) { currency = _currency; }

    public static boolean currencyValidator(String currencyToCheck) {
        boolean validationRes = false;
        for (String cur: CURRENCY_CODES.split(";")) {
            if (cur.equals(currencyToCheck)) {
                validationRes = true;
                break;
            }
        }
        return validationRes;
    }

    public enum TypeOfStatement {
        EXPENSE,
        INCOME,
        UNRECOGNIZED
    }

    public static String getNegativeResponses() {
        return negativeResponses;
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
     * Determines the type of statement (income or expense) based on the string extracted from DB.
     *
     * @param extractedCfType A string from cash_flows representing a cfType.
     * @return A TypeOfStatement value based on the input.
     * @throws ArrayIndexOutOfBoundsException if the command list does not contain enough elements.
     */
    public static TypeOfStatement getTypeOfStatement(String extractedCfType) throws ArrayIndexOutOfBoundsException {
        String strTypeOfStatement = extractedCfType.toUpperCase();
        if (strTypeOfStatement.equals(TypeOfStatement.EXPENSE.name())) {
            return TypeOfStatement.EXPENSE;
        } else if (strTypeOfStatement.equals(TypeOfStatement.INCOME.name())) {
            return TypeOfStatement.INCOME;
        } else {
            return TypeOfStatement.UNRECOGNIZED;
        }
    }

    /**
     * @param check Input string from user.
     * @return true if user wants to quit, false otherwise.
     */
    public static boolean isClearingDenied(String check) {
        for (String s: negativeResponses.split(",")) {
            if (check.toLowerCase().equals(s)) {
                return true;
            }
        }
        return false;
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

    public static DateTimeFormatter getDtf() {
        return dtf;
    }
}
