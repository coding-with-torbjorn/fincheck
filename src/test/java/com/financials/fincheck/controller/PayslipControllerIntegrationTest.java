package com.financials.fincheck.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financials.fincheck.model.Employer;
import com.financials.fincheck.model.PaymentMethod;
import com.financials.fincheck.model.Payslip;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Map;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PayslipControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Valid payslip should return 200 OK with success message")
    public void validPayslipShouldReturnSuccess() throws Exception {
        Payslip payslip = new Payslip(
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

        mockMvc.perform(post("/payslips/validate")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(payslip)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.messages[0]", is("Payslip is valid")));
    }

    @Test
    @DisplayName("Invalid payslip should return 400 Bad Request with error messages")
    public void invalidPayslipShouldReturnErrors() throws Exception {
        Payslip invalidPayslip = new Payslip(
                "",
                "",
                "invalid-date",
                LocalDate.of(2099, 1, 1),
                -1000.0,
                Map.of("", -200.0),
                -500.0,
                null,
                "",
                null,
                -20
        );

        mockMvc.perform(post("/payslips/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invalidPayslip)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.messages", containsInAnyOrder(
                        "Gross salary must be positive",
                        "Net salary must be positive",
                        "Employee ID must not be empty",
                        "Employee name must not be empty",
                        "Pay period must follow the format YYYY-MM",
                        "Payment date cannot be in the future",
                        "Deduction name must not be blank or null",
                        "Deduction amount for '' must be non-negative and not null",
                        "Payment method must not be empty",
                        "Employer must not be empty",
                        "Hours worked must be non-negative"
                )));
    }

    @Test
    @DisplayName("Missing payslip fields should trigger validation errors")
    public void missingPayslipFieldsShouldReturnErrors() throws Exception {
        String emptyJson = "{}";

        mockMvc.perform(post("/payslips/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.messages", containsInAnyOrder(
                        "Gross salary must not be empty",
                        "Net salary must not be empty",
                        "Employee ID must not be empty",
                        "Employee name must not be empty",
                        "Pay period must not be empty",
                        "Payment date must not be empty",
                        "Deductions must not be empty",
                        "Payment method must not be empty",
                        "Employer must not be empty"
                )));
    }
}