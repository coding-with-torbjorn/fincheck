package com.financials.fincheck.validator;

import com.financials.fincheck.model.Invoice;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// TODO: refactor InvoiceControllerTest.
/**
 * Component responsible for validating {@code Invoice} objects.
 * <p>
 * Performs basic validation checks such as:
 * - Ensuring the amount is non-negative
 * - Validating the currency is supported
 * - Ensuring the invoice number is present
 * <p>
 * This logic is intentionally separated from the controller to keep it clean and testable.
 */
@Component
public class InvoiceValidator {
    private static final List<String> VALID_CURRENCIES = Arrays.asList("USD", "EUR", "GBP");

    /**
     * Validates the fields of a given {@code Invoice}.
     *
     * @param invoice The invoice to validate.
     * @return A list of validation error messages. Returns an empty list if the invoice is valid.
     */
    public List<String> validate(Invoice invoice) {
        List<String> errors = new ArrayList<>();

        if (invoice.getAmount() == null) {
            errors.add("Amount must be provided");
        } else if (invoice.getAmount() < 0) {
            errors.add("Amount must not be negative");
        }

        if (!VALID_CURRENCIES.contains(invoice.getCurrency())) {
            errors.add("Invalid currency: " + invoice.getCurrency());
        }

        // Invoice number must be present and not just whitespace
        if (invoice.getInvoiceNumber() == null || invoice.getInvoiceNumber().trim().isEmpty()) {
            errors.add("Invoice number must not be empty");
        }
        return errors;
    }
}
