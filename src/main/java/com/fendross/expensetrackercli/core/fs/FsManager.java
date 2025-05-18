package com.fendross.expensetrackercli.core.fs;

import java.io.*;

public class FsManager {

    File file;
    FileReader fr;
    FileWriter fw;
    private final String csvPath = "data/cash_flow_statements.csv";

    public FsManager() {}

    public boolean initFsManager() throws IOException {
        this.file = new File(csvPath);
        if (!this.file.exists()) {
            this.file.createNewFile();
            return true;
        }
        fr = new FileReader(this.file);
        fw = new FileWriter(this.file);
        return false;
    }

    public File getFile() {
        return this.file;
    }

    public String getCsvPath() {
        return csvPath;
    }

}
