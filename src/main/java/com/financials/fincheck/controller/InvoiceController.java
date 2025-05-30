package com.financials.fincheck.controller;

import com.financials.fincheck.model.Invoice;
import com.financials.fincheck.dto.ValidationResponse;
import com.financials.fincheck.validator.InvoiceValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller that handles validation requests for {@code Invoice} objects.
 * <p>
 * Exposes a POST endpoint that accepts an {@code Invoice} in the request body,
 * validates it using the {@code InvoiceValidator}, and returns a JSON response indicating
 * whether the invoice passed validation or failed with specific errors.
 */
@RestController
@RequestMapping("/invoices")
public class InvoiceController {
    private final InvoiceValidator validator;

    /**
     * Constructor for injecting the {@code InvoiceValidator} dependency.
     *
     * @param validator A component responsible for validating Invoice fields.
     */
    public InvoiceController(InvoiceValidator validator) {
        this.validator = validator;
    }

    /**
     * POST endpoint that validates an invoice.
     * <p>
     * URL: {@code /invoices/validate}
     * <p>
     * This method takes a JSON representation of an {@code Invoice}, validates it,
     * and returns a {@code ValidationResponse} indicating success or listing validation errors.
     *
     * @param invoice The {@code Invoice} object to be validated, parsed from the request body.
     * @return A {@code ResponseEntity} containing the result of validation.
     */
    @PostMapping("/validate")
    public ResponseEntity<ValidationResponse> validateInvoice(@RequestBody Invoice invoice) {
        List<String> errors = validator.validate(invoice);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(new ValidationResponse(false, errors));
        }

        return ResponseEntity.ok(new ValidationResponse(true, List.of("Invoice is valid")));
    }
}
