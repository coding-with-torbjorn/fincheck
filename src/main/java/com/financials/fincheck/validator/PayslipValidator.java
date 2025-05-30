package com.financials.fincheck.validator;

import com.financials.fincheck.model.Payslip;

import java.util.ArrayList;
import java.util.List;

/**
 * Validates {@code Payslip} objects to ensure they adhere to expected financial and business rules.
 * <p>
 * This class is intended to encapsulate all validation logic for payslips.
 */
public class PayslipValidator {

    /**
     * Validates the fields of a given {@code Payslip}.
     * <p>
     * Currently performs basic checks on salary values.
     *
     * @param payslip The payslip to validate.
     * @return A list of validation error messages. If valid, the list will be empty.
     */
    public List<String> validate(Payslip payslip) {
        List<String> errors = new ArrayList<>();

        if (payslip.getGrossSalary() <= 0 || payslip.getNetSalary() <= 0) {
            errors.add("Salary must be positive");
        }

        // TODO: Add more business logic checks

        return errors;
    }
}