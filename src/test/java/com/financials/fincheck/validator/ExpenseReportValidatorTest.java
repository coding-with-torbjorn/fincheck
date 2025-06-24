package com.financials.fincheck.validator;

import com.financials.fincheck.model.ExpenseItem;
import com.financials.fincheck.model.ExpenseReport;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/*
* TODO: Test cases
*  - valid ExpenseReport should not return any errors (✓)
*  - reportId:
*       - should not be null (✓) (maybe ensuring specific format using regex in future?)
*  - employeeId:
*       - should not be null (✓) (maybe ensuring specific format using regex in future?)
*  - businessPurpose:
*       - should not be null (✓)
*  - currency:
*       - should not be null (✓)
*       - should be one of the valid currencies, e.g. "USD", "EUR", etc (NOTE: THIS SHOULD BE HANDLED IN ANOTHER LAYER SINCE CURRENCY CLASS HANDLES INCORRECT CURRENCY FORMAT ITSELF)
*  - submissionDate:
*       - should not be null (✓)
*       - can not be in the future (✓)
*  - items:
*       - should not be null (✓)
*       - TODO: more checks for ExpenseItem?
* */
class ExpenseReportValidatorTest {
    private final ExpenseReportValidator validator = new ExpenseReportValidator();
    private ExpenseReport baseExpenseReport;

    @BeforeEach
    public void setUp() throws Exception {
        baseExpenseReport = new ExpenseReport(
                "ER2025-0001",
                "EMP12345",
                "Client meeting in Stockholm",
                Currency.getInstance("EUR"),
                LocalDate.of(2025, 6, 16),
                List.of(
                        new ExpenseItem("Travel", 75.50, LocalDate.of(2025, 6, 14), "Train to meeting"),
                        new ExpenseItem("Meals", 40.00, LocalDate.of(2025, 6, 14), "Lunch with client")
                )
        );
    }

    @Test
    public void validExpenseReportShouldReturnNoErrors() throws Exception {
        List<String> errors = validator.validate(baseExpenseReport);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void reportIdShouldNotBeEmpty() throws Exception {
        baseExpenseReport.setReportId("");
        List<String> errors = validator.validate(baseExpenseReport);
        assertTrue(errors.contains("Report ID must not be empty"));
    }

    @Test
    public void employeeIdShouldNotBeEmpty() throws Exception {
        baseExpenseReport.setEmployeeId(null);
        List<String> errors = validator.validate(baseExpenseReport);
        assertTrue(errors.contains("Employee ID must not be empty"));
    }

    @Test
    public void businessPurposeShouldNotBeEmpty() throws Exception {
        baseExpenseReport.setBusinessPurpose("  ");
        List<String> errors = validator.validate(baseExpenseReport);
        assertTrue(errors.contains("Business purpose must not be empty"));
    }

    @Test
    public void currencyShouldNotBeNull() throws Exception {
        baseExpenseReport.setCurrency(null);
        List<String> errors = validator.validate(baseExpenseReport);
        assertTrue(errors.contains("Currency must not be null"));
    }

    @Test
    public void submissionDateShouldNotBeNull() throws Exception {
        baseExpenseReport.setSubmissionDate(null);
        List<String> errors = validator.validate(baseExpenseReport);
        assertTrue(errors.contains("Submission date must not be null"));
    }

    @Test
    public void submissionDateShouldNotBeInFuture() throws Exception {
        baseExpenseReport.setSubmissionDate(LocalDate.of(4000, 12, 26));
        List<String> errors = validator.validate(baseExpenseReport);
        assertTrue(errors.contains("Submission date must not be in the future"));
    }

    @Test
    public void itemsShouldNotBeNull() throws Exception {
        baseExpenseReport.setItems(null);
        List<String> errors = validator.validate(baseExpenseReport);
        assertTrue(errors.contains("Items must not be null"));
    }
}