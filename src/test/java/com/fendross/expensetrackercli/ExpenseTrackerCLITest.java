package com.fendross.expensetrackercli;

import com.fendross.expensetrackercli.core.expense.Expense;
import com.fendross.expensetrackercli.core.expense.ExpenseManager;
import com.fendross.expensetrackercli.core.income.Income;
import com.fendross.expensetrackercli.core.income.IncomeManager;
import com.fendross.expensetrackercli.exceptions.AddException;
import com.fendross.expensetrackercli.exceptions.DeleteException;
import com.fendross.expensetrackercli.exceptions.ReportException;
import com.fendross.expensetrackercli.exceptions.ViewException;
import com.fendross.expensetrackercli.utils.ReplUtils;
import com.fendross.expensetrackercli.utils.GenericUtils;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.io.PrintStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

//@ExtendWith(MockitoExtension.class)
class ExpenseTrackerCLITest {

    @Mock
    private ExpenseManager mockExpenseManager;

    @Mock
    private IncomeManager mockIncomeManager;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void handleAddCommand_ExpenseWithValidParams_Success() throws AddException, IllegalAccessException, NoSuchFieldException {

        // Arrange
        ArrayList<String> commands = new ArrayList<>(Arrays.asList("add", "expense", "100", "Travel", "Flight", "Bruxelles"));

        // Create the mock
        ExpenseManager mockExpenseManager = mock(ExpenseManager.class);

        // Find and store the original ExpenseManager instance
        Field expenseManagerField = ExpenseTrackerCLI.class.getDeclaredField("expenseManager");
        expenseManagerField.setAccessible(true);
        Object originalExpenseManager = expenseManagerField.get(null); // null since it's static

        // Inject our mock
        expenseManagerField.set(null, mockExpenseManager);

        try (MockedStatic<GenericUtils> genericUtilsMock = Mockito.mockStatic(GenericUtils.class);
             MockedStatic<ReplUtils> replUtilsMock = Mockito.mockStatic(ReplUtils.class)) {

            // Mock static method
            genericUtilsMock.when(() -> GenericUtils.fetchTypeOfStatement(any()))
                    .thenReturn(GenericUtils.TypeOfStatement.EXPENSE);

            // For static fields, we need to mock the method that GETS these values
            // instead of trying to directly mock the fields
            genericUtilsMock.when(GenericUtils::getNumOfMandatoryAddParams).thenReturn(4);
            genericUtilsMock.when(GenericUtils::getNumOfMaximumAddParams).thenReturn(6);

            // Act
            ExpenseTrackerCLI.handleAddCommand(commands);

            // Assert
            verify(mockExpenseManager, times(1)).addExpense(commands);
            replUtilsMock.verify(() -> ReplUtils.handleAddSuccess(GenericUtils.TypeOfStatement.EXPENSE));
        } finally {
            // Restore original ExpenseManager
            expenseManagerField.set(null, originalExpenseManager);
        }

    }

}
