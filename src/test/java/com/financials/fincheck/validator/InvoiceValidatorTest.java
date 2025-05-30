package com.financials.fincheck.validator;

import com.financials.fincheck.model.Invoice;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

// TODO: Can these tests be refactored in any way?
/**
 * Unit tests for the {@code InvoiceValidator} class.
 * <p>
 * These tests verify that the validator correctly identifies valid and invalid
 * invoice inputs, including edge cases such as negative amounts, unknown currencies,
 * and missing invoice numbers.
 */
public class InvoiceValidatorTest {
    private final InvoiceValidator validator = new InvoiceValidator();

    /**
     * Tests that a properly formed invoice passes all validation checks.
     */
    @Test
    public void validInvoiceShouldReturnNoErrors() throws Exception {
        Invoice invoice = new Invoice("INV001", 2500.0, "EUR");
        List<String> errors = validator.validate(invoice);
        assertTrue(errors.isEmpty());
    }

    /**
     * Tests that a negative amount triggers the appropriate validation error.
     */
    @Test
    public void negativeAmountShouldReturnAmountValidationError() throws Exception {
        Invoice invoice = new Invoice("INV002", -75.0, "EUR");
        List<String> errors = validator.validate(invoice);
        assertEquals(1, errors.size());
        assertEquals("Amount must not be negative", errors.getFirst());
    }

    /**
     * Tests that an unsupported currency triggers a validation error.
     */
    @Test
    public void invalidCurrencyShouldReturnCurrencyValidationError() throws Exception {
        Invoice invoice = new Invoice("INV003", 164.0, "UFC");
        List<String> errors = validator.validate(invoice);
        assertEquals(1, errors.size());
        assertEquals("Invalid currency: UFC", errors.getFirst());
    }

    /**
     * Tests that an empty invoice number is caught by the validator.
     */
    @Test
    public void emptyInvoiceNumberShouldReturnInvoiceNumberValidationError() throws Exception {
        Invoice invoice = new Invoice("", 254, "USD");
        List<String> errors = validator.validate(invoice);
        assertEquals(1, errors.size());
        assertEquals("Invoice number must not be empty", errors.getFirst());
    }

    /**
     * Tests that multiple simultaneous errors (invalid number, amount, and currency)
     * are all caught and returned in the error list.
     */
    @Test
    public void multipleInvalidFieldsShouldReturnMultipleValidationErrors() throws Exception {
        Invoice invoice = new Invoice("", -10.0, "ABC");
        List<String> errors = validator.validate(invoice);
        assertEquals(3, errors.size());
        assertTrue(errors.contains("Amount must not be negative"));
        assertTrue(errors.contains("Invalid currency: ABC"));
        assertTrue(errors.contains("Invoice number must not be empty"));
    }
}
