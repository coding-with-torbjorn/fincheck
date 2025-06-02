package com.financials.fincheck.validator;

import com.financials.fincheck.model.Employer;
import com.financials.fincheck.model.PaymentMethod;
import com.financials.fincheck.model.Payslip;
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

    @Test
    public void validPayslipShouldReturnNoErrors() throws Exception {
        Payslip payslip = new Payslip(
                "EMP123",
                "Alice Smith",
                "2025-05",
                LocalDate.of(2025, 5, 25),
                1200.0, // grossSalary (non-positive)
                Map.of("Tax", 200.0),
                1000.0,
                PaymentMethod.BANK_TRANSFER,
                "IE29AIBK93115212345678",
                new Employer("ABC Corp", "1234567A"),
                160
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.isEmpty());
    }

    @Test
    public void nonPositiveGrossSalaryShouldReturnError() throws Exception {
        Payslip payslip = new Payslip(
  "EMP123",
            "Alice Smith",
            "2025-05",
            LocalDate.of(2025, 5, 25),
            0.0, // grossSalary (non-positive)
            Map.of("Tax", 200.0),
            1000.0,
            PaymentMethod.BANK_TRANSFER,
            "IE29AIBK93115212345678",
            new Employer("ABC Corp", "1234567A"),
            160
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Salary must be positive"));
    }

    @Test
    public void nonPositiveNetSalaryShouldReturnError() throws Exception {
        Payslip payslip = new Payslip(
                "EMP123",
                "Alice Smith",
                "2025-05",
                LocalDate.of(2025, 5, 25),
                1000.0,
                Map.of("Tax", 200.0),
                0,
                PaymentMethod.BANK_TRANSFER,
                "IE29AIBK93115212345678",
                new Employer("ABC Corp", "1234567A"),
                160
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Salary must be positive"));
    }

    @Test
    public void emptyEmployeeIdShouldReturnError() throws Exception {
        Payslip payslip = new Payslip(
                "",
                "Alice Smith",
                "2025-05",
                LocalDate.of(2025, 5, 25),
                1000.0,
                Map.of("Tax", 200.0),
                0,
                PaymentMethod.BANK_TRANSFER,
                "IE29AIBK93115212345678",
                new Employer("ABC Corp", "1234567A"),
                160
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Employee ID must not be empty"));
    }

    @Test
    public void emptyEmployeeNameShouldReturnError() throws Exception {
        Payslip payslip = new Payslip(
                "EMP123",
                "",
                "2025-05",
                LocalDate.of(2025, 5, 25),
                1000.0,
                Map.of("Tax", 200.0),
                0,
                PaymentMethod.BANK_TRANSFER,
                "IE29AIBK93115212345678",
                new Employer("ABC Corp", "1234567A"),
                160
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Employee name must not be empty"));
    }

    @Test
    public void emptyPayPeriodShouldReturnError() throws Exception {
        Payslip payslip = new Payslip(
                "EMP123",
                "Alice Smith",
                "",
                LocalDate.of(2025, 5, 25),
                1000.0,
                Map.of("Tax", 200.0),
                0,
                PaymentMethod.BANK_TRANSFER,
                "IE29AIBK93115212345678",
                new Employer("ABC Corp", "1234567A"),
                160
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Pay period must not be empty"));
    }

    @Test
    public void invalidPayPeriodFormatShouldThrowException() throws Exception {
        Payslip payslip = new Payslip(
                "EMP123",
                "Alice Smith",
                "dfsasd",
                LocalDate.of(2025, 5, 25),
                1000.0,
                Map.of("Tax", 200.0),
                0,
                PaymentMethod.BANK_TRANSFER,
                "IE29AIBK93115212345678",
                new Employer("ABC Corp", "1234567A"),
                160
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Pay period must follow the format YYYY-MM"));
    }

    @Test
    public void emptyPaymentDateShouldReturnError() throws Exception {
        Payslip payslip = new Payslip(
                "EMP123",
                "Alice Smith",
                "2025-05",
                null,
                2000.0,
                Map.of("Tax", 200.0),
                1000.0,
                PaymentMethod.BANK_TRANSFER,
                "IE29AIBK93115212345678",
                new Employer("ABC Corp", "1234567A"),
                160
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Payment date must not be empty"));
    }

    @Test
    public void futurePaymentDateShouldReturnError() throws Exception {
        Payslip payslip = new Payslip(
                "EMP123",
                "Alice Smith",
                "2025-05",
                LocalDate.of(2099, 8, 25),
                2000.0,
                Map.of("Tax", 200.0),
                1000.0,
                PaymentMethod.BANK_TRANSFER,
                "IE29AIBK93115212345678",
                new Employer("ABC Corp", "1234567A"),
                160
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Payment date cannot be in the future"));
    }

    @Test
    public void emptyDeductionsShouldReturnError() throws Exception {
        Payslip payslip = new Payslip(
                "EMP123",
                "Alice Smith",
                "2025-05",
                LocalDate.of(2025, 5, 25),
                2000.0,
                Map.of(),
                1000.0,
                PaymentMethod.BANK_TRANSFER,
                "IE29AIBK93115212345678",
                new Employer("ABC Corp", "1234567A"),
                160
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Deductions must not be empty"));
    }

    @Test
    public void incorrectNetSalaryShouldReturnError() throws Exception {
        Payslip payslip = new Payslip(
                "EMP123",
                "Alice Smith",
                "2025-05",
                LocalDate.of(2025, 5, 25),
                2000.0,
                Map.of("Tax", 200.0),
                1000.0,
                PaymentMethod.BANK_TRANSFER,
                "IE29AIBK93115212345678",
                new Employer("ABC Corp", "1234567A"),
                160
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Net salary does not match gross salary minus deductions"));
    }

    // TODO: Add test cases for invalid key and values in deductions
    @Test
    public void emptyDeductionKeyShouldReturnError() throws Exception {
        Payslip payslip = new Payslip(
                "EMP123",
                "Alice Smith",
                "2025-05",
                LocalDate.of(2025, 5, 25),
                2000.0,
                Map.of("", 200.0),
                1800.0,
                PaymentMethod.BANK_TRANSFER,
                "IE29AIBK93115212345678",
                new Employer("ABC Corp", "1234567A"),
                160
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Deduction name must not be blank or null"));
    }

    @Test
    public void negativeDeductionValueShouldReturnError() throws Exception {
        Payslip payslip = new Payslip(
                "EMP123",
                "Alice Smith",
                "2025-05",
                LocalDate.of(2025, 5, 25),
                2000.0,
                Map.of("Tax", -1.0),
                1800.0,
                PaymentMethod.BANK_TRANSFER,
                "IE29AIBK93115212345678",
                new Employer("ABC Corp", "1234567A"),
                160
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Deduction amount for '" + "Tax" + "' must be non-negative and not null"));
    }

    @Test
    public void emptyPaymentMethodShouldReturnError() throws Exception {
        Payslip payslip = new Payslip(
                "EMP123",
                "Alice Smith",
                "2025-05",
                LocalDate.of(2025, 5, 25),
                2000.0,
                Map.of("Tax", 200.0),
                1800.0,
                null,
                "IE29AIBK93115212345678",
                new Employer("ABC Corp", "1234567A"),
                160
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Payment method must not be empty"));
    }

    @Test
    public void emptyBankAccountShouldReturnError() throws Exception {
        Payslip payslip = new Payslip(
                "EMP123",
                "Alice Smith",
                "2025-05",
                LocalDate.of(2025, 5, 25),
                2000.0,
                Map.of("Tax", 200.0),
                1800.0,
                PaymentMethod.BANK_TRANSFER,
                null,
                new Employer("ABC Corp", "1234567A"),
                160
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Bank account (IBAN) must not be empty for bank transfers"));
    }

    @Test
    public void invalidBankAccountIbanShouldReturnError() throws Exception {
        Payslip payslip = new Payslip(
                "EMP123",
                "Alice Smith",
                "2025-05",
                LocalDate.of(2025, 5, 25),
                2000.0,
                Map.of("Tax", 200.0),
                1800.0,
                PaymentMethod.BANK_TRANSFER,
                "54IEAIBK93115215678",
                new Employer("ABC Corp", "1234567A"),
                160
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Bank account (IBAN) format is invalid"));
    }

    @Test
    public void emptyEmployerShouldReturnError() throws Exception {
        Payslip payslip = new Payslip(
                "EMP123",
                "Alice Smith",
                "2025-05",
                LocalDate.of(2025, 5, 25),
                2000.0,
                Map.of("Tax", 200.0),
                1800.0,
                PaymentMethod.BANK_TRANSFER,
                "IE29AIBK93115212345678",
                null,
                160
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Employer must not be empty"));
    }

    @Test
    public void negativeHoursWorkedShouldReturnError() throws Exception {
        Payslip payslip = new Payslip(
                "EMP123",
                "Alice Smith",
                "2025-05",
                LocalDate.of(2025, 5, 25),
                2000.0,
                Map.of("Tax", 200.0),
                1800.0,
                PaymentMethod.BANK_TRANSFER,
                "IE29AIBK93115212345678",
                new Employer("ABC Corp", "1234567A"),
                -1
        );
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Hours worked must be non-negative"));
    }

    @Test
    public void multipleInvalidFieldsShouldReturnMultipleErrors() throws Exception {
        Payslip payslip = new Payslip(
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
        List<String> errors = validator.validate(payslip);
        assertTrue(errors.contains("Salary must be positive"));
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