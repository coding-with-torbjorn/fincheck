package com.financials.fincheck.validator;

import com.financials.fincheck.model.Employer;
import com.financials.fincheck.model.PaymentMethod;
import com.financials.fincheck.model.Payslip;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Unit test class for {@code PayslipValidator}.
 * <p>
 * This test suite will verify the correctness of validation rules applied
 * to {@code Payslip} objects.
 */
class PayslipValidatorTest {
    private final PayslipValidator validator = new PayslipValidator();
    private Payslip basePayslip;

    @BeforeEach
    public void setUp() throws Exception {
        basePayslip = new Payslip(
                "EMP001",
                "John Doe",
                "2025-05",
                LocalDate.of(2025, 5, 25),
                3000.0,
                Map.of("Tax", 500.0, "Pension", 200.0),
                2300.0,
                PaymentMethod.BANK_TRANSFER,
                "IE29AIBK93115212345678",
                new Employer("ValidCorp Ltd", "1234567A"),
                160
        );
    }

    @Test
    public void validPayslipShouldReturnNoErrors() throws Exception {
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void nonPositiveGrossSalaryShouldReturnError() throws Exception {
        basePayslip.setGrossSalary(0.0);
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Gross salary must be positive"));
    }

    @Test
    public void nonPositiveNetSalaryShouldReturnError() throws Exception {
        basePayslip.setNetSalary(0.0);
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Net salary must be positive"));
    }

    @Test
    public void emptyGrossSalaryShouldReturnError() throws Exception {
        basePayslip.setGrossSalary(null);
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Gross salary must not be empty"));
    }

    @Test
    public void emptyNetSalaryShouldReturnError() throws Exception {
        basePayslip.setNetSalary(null);
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Net salary must not be empty"));
    }

    @Test
    public void emptyEmployeeIdShouldReturnError() throws Exception {
        basePayslip.setEmployeeId("");
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Employee ID must not be empty"));
    }

    @Test
    public void emptyEmployeeNameShouldReturnError() throws Exception {
        basePayslip.setEmployeeName("");
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Employee name must not be empty"));
    }

    @Test
    public void emptyPayPeriodShouldReturnError() throws Exception {
        basePayslip.setPayPeriod("");
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Pay period must not be empty"));
    }

    @Test
    public void invalidPayPeriodFormatShouldThrowException() throws Exception {
        basePayslip.setPayPeriod("dfsasd");
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Pay period must follow the format YYYY-MM"));
    }

    @Test
    public void emptyPaymentDateShouldReturnError() throws Exception {
        basePayslip.setPaymentDate(null);
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Payment date must not be empty"));
    }

    @Test
    public void futurePaymentDateShouldReturnError() throws Exception {
        basePayslip.setPaymentDate(LocalDate.of(2099, 8, 25));
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Payment date cannot be in the future"));
    }

    @Test
    public void emptyDeductionsShouldReturnError() throws Exception {
        basePayslip.setDeductions(Map.of());
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Deductions must not be empty"));
    }

    @Test
    public void incorrectNetSalaryShouldReturnError() throws Exception {
        basePayslip.setNetSalary(1000.0);
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Net salary does not match gross salary minus deductions"));
    }

    @Test
    public void emptyDeductionKeyShouldReturnError() throws Exception {
        basePayslip.setDeductions(Map.of("", 200.0));
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Deduction name must not be blank or null"));
    }

    @Test
    public void negativeDeductionValueShouldReturnError() throws Exception {
        basePayslip.setDeductions(Map.of("Tax", -1.0));
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Deduction amount for '" + "Tax" + "' must be non-negative and not null"));
    }

    @Test
    public void emptyPaymentMethodShouldReturnError() throws Exception {
        basePayslip.setPaymentMethod(null);
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Payment method must not be empty"));
    }

    @Test
    public void emptyBankAccountShouldReturnError() throws Exception {
        basePayslip.setBankAccount(null);
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Bank account (IBAN) must not be empty for bank transfers"));
    }

    @Test
    public void invalidBankAccountIbanShouldReturnError() throws Exception {
        basePayslip.setBankAccount("54IEAIBK93115215678");
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Bank account (IBAN) format is invalid"));
    }

    @Test
    public void emptyEmployerShouldReturnError() throws Exception {
        basePayslip.setEmployer(null);
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Employer must not be empty"));
    }

    @Test
    public void negativeHoursWorkedShouldReturnError() throws Exception {
        basePayslip.setHoursWorked(-1);
        List<String> errors = validator.validate(basePayslip);
        assertTrue(errors.contains("Hours worked must be non-negative"));
    }

    @Test
    public void multipleInvalidFieldsShouldReturnMultipleErrors() throws Exception {
        Payslip invalidPayslip = new Payslip(
                "",
                "",
                "invalid-date",
                LocalDate.of(2099, 1, 1),
                -1000.0,
                Map.of("", -100.0),
                -500.0,
                null,
                "",
                null,
                -10
        );
        List<String> errors = validator.validate(invalidPayslip);
        assertTrue(errors.contains("Gross salary must be positive"));
        assertTrue(errors.contains("Net salary must be positive"));
        assertTrue(errors.contains("Employee ID must not be empty"));
        assertTrue(errors.contains("Employee name must not be empty"));
        assertTrue(errors.contains("Pay period must follow the format YYYY-MM"));
        assertTrue(errors.contains("Payment date cannot be in the future"));
        assertTrue(errors.contains("Deduction name must not be blank or null"));
        assertTrue(errors.contains("Deduction amount for '' must be non-negative and not null"));
        assertTrue(errors.contains("Payment method must not be empty"));
        assertTrue(errors.contains("Employer must not be empty"));
        assertTrue(errors.contains("Hours worked must be non-negative"));
    }
}