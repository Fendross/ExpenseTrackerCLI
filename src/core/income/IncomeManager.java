package core.income;

import utils.GenericUtils;

import java.util.ArrayList;
import java.util.Date;

public class IncomeManager {
    // TODO implement operations and list them in the API docs.
    ArrayList<Income> incomes = new ArrayList<>();
    public IncomeManager() {}

    public void addIncome(ArrayList<String> commands) {
        incomes.add(buildIncome(commands));
        System.out.println("Added income from ExpenseManager.");
    }

    public void deleteIncome(int id) {
        System.out.println("Deleted income from ExpenseManager.");
    }

    public Income buildIncome(ArrayList<String> commands) {
        Income income = new Income();

        // Set all fields.
        income.setId(GenericUtils.getGlobalId());
        income.setAmount(Double.valueOf(commands.get(2)));
        income.setDateOfStatement(new Date());
        income.setCategory(commands.get(3).length() > 0 ? commands.get(3) : "");
        //income.setSubcategory(commands.get(4).length() > 0 ? commands.get(4) : "");
        //income.setDescription(commands.get(5).length() > 0 ? commands.get(5) : "");

        return income;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for (Income e: incomes) {
            str.append(e.toString());
        }
        return str.toString();
    }
}
