package com.financials.fincheck.validator;

import com.financials.fincheck.model.ExpenseReport;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ExpenseReportValidator {
    public List<String> validate(ExpenseReport expenseReport) {
        List<String> errors = new ArrayList<>();

        if (expenseReport.getReportId() == null || expenseReport.getReportId().isBlank()) {
            errors.add("Report ID must not be empty");
        }

        if (expenseReport.getEmployeeId() == null || expenseReport.getEmployeeId().isBlank()) {
            errors.add("Employee ID must not be empty");
        }

        if (expenseReport.getBusinessPurpose() == null || expenseReport.getBusinessPurpose().isBlank()) {
            errors.add("Business purpose must not be empty");
        }

        if (expenseReport.getCurrency() == null) {
            errors.add("Currency must not be null");
        }

        if (expenseReport.getSubmissionDate() == null) {
            errors.add("Submission date must not be null");
        } else if (expenseReport.getSubmissionDate().isAfter(LocalDate.now())) {
            errors.add("Submission date must not be in the future");
        }

        if (expenseReport.getItems() == null) {
            errors.add("Items must not be null");
        }

        return errors;
    }
}
