package com.financials.fincheck.validator;

import com.financials.fincheck.model.PaymentMethod;
import com.financials.fincheck.model.Payslip;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Validates {@code Payslip} objects to ensure they adhere to expected financial and business rules.
 * <p>
 * This class is intended to encapsulate all validation logic for payslips.
 */
@Component
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

        if (payslip.getEmployeeId() == null || payslip.getEmployeeId().trim().isEmpty()) {
            errors.add("Employee ID must not be empty");
        }

        if (payslip.getEmployeeName() == null || payslip.getEmployeeName().trim().isEmpty()) {
            errors.add("Employee name must not be empty");
        }

        String payPeriod = payslip.getPayPeriod();
        if (payPeriod == null || payPeriod.isBlank()) {
            errors.add("Pay period must not be empty");
        } else {
            try {
                YearMonth.parse(payPeriod);
            } catch (DateTimeParseException e) {
                errors.add("Pay period must follow the format YYYY-MM");
            }
        }

        if (payslip.getPaymentDate() == null) {
            errors.add("Payment date must not be empty");
        } else if (payslip.getPaymentDate().isAfter(LocalDate.now())) {
            errors.add("Payment date cannot be in the future");
        }

        if (payslip.getDeductions() == null || payslip.getDeductions().isEmpty()) {
            errors.add("Deductions must not be empty");
        } else {
            boolean hasInvalidDeduction = false;

            for (Map.Entry<String, Double> entry : payslip.getDeductions().entrySet()) {
                String key = entry.getKey();
                Double value = entry.getValue();

                if (key == null || key.isBlank()) {
                    errors.add("Deduction name must not be blank or null");
                    hasInvalidDeduction = true;
                }

                if (value == null || value < 0) {
                    errors.add("Deduction amount for '" + key + "' must be non-negative and not null");
                    hasInvalidDeduction = true;
                }
            }

            if (!hasInvalidDeduction) {
                double sumDeductions = payslip.getDeductions().values().stream().mapToDouble(Double::doubleValue).sum();
                double expectedNetSalary = payslip.getGrossSalary() - sumDeductions;

                if (Math.abs(payslip.getNetSalary() - expectedNetSalary) > 0.01) {
                    errors.add("Net salary does not match gross salary minus deductions");
                }
            }
        }

        if (payslip.getPaymentMethod() == null) {
            errors.add("Payment method must not be empty");
        }

        if (payslip.getPaymentMethod() == PaymentMethod.BANK_TRANSFER) {
            String bankAccount = payslip.getBankAccount();

            if (bankAccount == null || bankAccount.isBlank()) {
                errors.add("Bank account (IBAN) must not be empty for bank transfers");
            } else {
                String ibanPattern = "^[A-Z]{2}\\d{2}[A-Z0-9]{11,30}$";
                if (!bankAccount.matches(ibanPattern)) {
                    errors.add("Bank account (IBAN) format is invalid");
                }
            }
        }

        if (payslip.getEmployer() == null) {
            errors.add("Employer must not be empty");
        }

        if (payslip.getHoursWorked() < 0) {
            errors.add("Hours worked must be non-negative");
        }

        return errors;
    }
}