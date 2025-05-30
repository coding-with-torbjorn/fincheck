package com.financials.fincheck.controller;

import com.financials.fincheck.model.Payslip;
import com.financials.fincheck.dto.ValidationResponse;
import com.financials.fincheck.validator.PayslipValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller responsible for handling validation requests related to {@code Payslip} objects.
 * <p>
 * Exposes a POST endpoint that accepts a payslip in JSON format, delegates validation
 * to the {@code PayslipValidator}, and returns a JSON response indicating
 * whether the payslip passed validation or failed with specific errors.
 */
@RestController
@RequestMapping("/payslips")
public class PayslipController {
    private final PayslipValidator validator;

    /**
     * Constructor that injects the {@code PayslipValidator} dependency.
     *
     * @param validator The component responsible for validating payslip fields and business rules.
     */
    public PayslipController(PayslipValidator validator) {
        this.validator = validator;
    }

    /**
     * POST endpoint that validates a given payslip.
     * <p>
     * URL: {@code /payslips/validate}
     * <p>
     * Accepts a {@code Payslip} object from the request body, performs validation,
     * and responds with either a list of validation errors or a success message.
     *
     * @param payslip The {@code Payslip} object to validate.
     * @return A {@code ResponseEntity} containing the result of the validation operation.
     */
    @PostMapping("/validate")
    public ResponseEntity<ValidationResponse> validatePayslip(@RequestBody Payslip payslip) {
        List<String> errors = validator.validate(payslip);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(new ValidationResponse(false, errors));
        }

        return ResponseEntity.ok(new ValidationResponse(true, List.of("Payslip is valid")));
    }
}
