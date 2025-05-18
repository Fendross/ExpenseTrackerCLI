package com.fendross.expensetrackercli.core.fs;

import com.fendross.expensetrackercli.core.expense.ExpenseManager;
import com.fendross.expensetrackercli.core.income.IncomeManager;
import com.fendross.expensetrackercli.exceptions.FsException;

import java.io.*;
import java.util.ArrayList;

public class FsManager {

    File file;
    private final String csvPath = "data/cash_flow_statements.csv";

    public FsManager() {}

    public boolean initFsManager() throws IOException {
        this.file = new File(csvPath);
        if (!this.file.exists()) {
            this.file.createNewFile();
            return true;
        }
        return false;
    }

    public void loadStatementsFromFile(ExpenseManager expenseManager, IncomeManager incomeManager) throws FsException {
        ArrayList<String> statements = getStatementsFromFile();
        for (String s: statements) {
            System.out.println(s);
        }
    }

    public ArrayList<String> getStatementsFromFile() throws FsException {
        ArrayList<String> statements = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(this.file))) {
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

    public void writeStatement(String statement) throws FsException {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.file))) {
            bw.append(statement);
        } catch (FileNotFoundException ex) {
            throw new FsException("File was not found.", ex);
        } catch (IOException ex) {
            throw new FsException("File could not be updated.", ex);
        }
    }

    public File getFile() {
        return this.file;
    }

    public String getCsvPath() {
        return csvPath;
    }

}
