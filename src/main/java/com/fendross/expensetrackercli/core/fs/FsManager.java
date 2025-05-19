package com.fendross.expensetrackercli.core.fs;

import com.fendross.expensetrackercli.core.expense.ExpenseManager;
import com.fendross.expensetrackercli.core.income.IncomeManager;
import com.fendross.expensetrackercli.exceptions.AddException;
import com.fendross.expensetrackercli.exceptions.FsException;

import java.io.*;
import java.util.ArrayList;

public class FsManager {

    File file;
    private final String csvPath = "data/cash_flow_statements.csv";
    private final String backupPath = "data/archive/bkp_cash_flow_statements.csv";

    public FsManager() {}

    public boolean initFsManager() throws IOException {
        this.file = new File(csvPath);
        if (!this.file.exists()) {
            this.file.createNewFile();
            return true;
        }
        return false;
    }

    /**
     * Loads statements, if they exist already in file, in the appropriate data structure, so they can be worked on.
     *
     * @param expenseManager Expense manager from main.
     * @param incomeManager Income manager from main.
     * @throws FsException Error in reading from file or IO miscellaneous.
     */
    public void loadStatementsFromFile(ExpenseManager expenseManager, IncomeManager incomeManager) throws FsException {
        ArrayList<String> statements = getStatementsFromFile();
        // TODO make this if exclusive. Also, refactor the algorithm.
        if (statements.size() > 0) {
            for (String statement: statements) {
                ArrayList<String> cfs = new ArrayList<>();
                for (String item: statement.split(",")) {
                    cfs.add(item);
                }
                String typeOfStatement = cfs.get(1);

                if (typeOfStatement.equals("expense")) {
                    try {
                        expenseManager.addExpense(cfs);
                    } catch (AddException ex) {
                        throw new FsException("Error adding expense from file. Aborting.", ex);
                    }
                } else if (typeOfStatement.equals("income")) {
                    try {
                        incomeManager.addIncome(cfs);
                    } catch (AddException ex) {
                        throw new FsException("Error adding income from file. Aborting.", ex);
                    }
                } else {
                    throw new FsException("Unrecognized type of statement. Aborting.", new Throwable());
                }
            }
        }
    }

    public ArrayList<String> getStatementsFromFile() throws FsException {
        ArrayList<String> statements = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(getFile()))) {
            String line;
            while ((line = br.readLine()) != null) {
                statements.add(line);
            }
        } catch (FileNotFoundException ex) {
            throw new FsException("File was not found.", ex);
        } catch (IOException ex) {
            throw new FsException("File has some errors.", ex);
        }

        return statements;
    }

    /**
     * Loads statements in the appropriate data structures, so they can be worked on.
     *
     * @param statement A string containing a statement to be added.
     * @throws FsException Error in writing to file or IO miscellaneous.
     */
    public void writeStatement(File file, String statement) throws FsException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, true))) {
            bw.append(statement);
            bw.newLine();
        } catch (FileNotFoundException ex) {
            throw new FsException("File was not found.", ex);
        } catch (IOException ex) {
            throw new FsException("File could not be updated.", ex);
        }
    }

    public void backupFile() throws FsException, IOException {
        File backupFile = new File(backupPath);
        backupFile.delete();
        if (!backupFile.exists()) {
            backupFile.createNewFile();
        }
        for (String statement: getStatementsFromFile()) {
            writeStatement(backupFile, statement);
        }
    }

    public boolean clearFile() {
        return this.file.delete();
    }

    public File getFile() {
        return this.file;
    }

    public String getBackupPath() {
        return this.backupPath;
    }

    public String getCsvPath() {
        return csvPath;
    }

}
