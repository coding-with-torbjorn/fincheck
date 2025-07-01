package com.financials.fincheck.controller;

import com.financials.fincheck.model.ExpenseReport;
import com.financials.fincheck.model.Payslip;
import com.financials.fincheck.dto.ValidationResponse;
import com.financials.fincheck.validator.ExpenseReportValidator;
import com.financials.fincheck.validator.PayslipValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
public class ExpenseReportController {
    private final ExpenseReportValidator validator;

    public ExpenseReportController(ExpenseReportValidator validator) {
        this.validator = validator;
    }

    @PostMapping("/validate")
    public ResponseEntity<ValidationResponse> validateExpenseReport(@RequestBody ExpenseReport expenseReport) {
        List<String> errors = validator.validate(expenseReport);

        if (!errors.isEmpty()) {
            return ResponseEntity.badRequest().body(new ValidationResponse(false, errors));
        }

        return ResponseEntity.ok(new ValidationResponse(true, List.of("Expense report is valid")));
    }
}
