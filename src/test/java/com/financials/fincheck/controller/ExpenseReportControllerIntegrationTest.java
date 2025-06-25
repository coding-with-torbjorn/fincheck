package com.financials.fincheck.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financials.fincheck.model.ExpenseItem;
import com.financials.fincheck.model.ExpenseReport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ExpenseReportControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Valid expense report should return 200 OK with success message")
    public void validExpenseReportShouldReturnSuccess() throws Exception {
        ExpenseItem item = new ExpenseItem("Travel", 75.50, LocalDate.now().minusDays(1), "Train to meeting");
        ExpenseReport report = new ExpenseReport(
                "ER2025-0001",
                "EMP12345",
                "Client meeting in Stockholm",
                Currency.getInstance("EUR"),
                LocalDate.now(),
                List.of(item)
        );

        mockMvc.perform(post("/reports/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(report)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.messages[0]", is("Expense report is valid")));
    }

    @Test
    @DisplayName("Invalid expense report should return 400 Bad Request with error messages")
    public void invalidExpenseReportShouldReturnErrors() throws Exception {
        ExpenseItem item = new ExpenseItem("", -50.0, LocalDate.now().plusDays(1), "");
        ExpenseReport report = new ExpenseReport(
                "",
                "",
                "",
                Currency.getInstance("EUR"), // TODO: Resolve invalid currency
                LocalDate.now().plusDays(2),
                List.of(item)
        );

        mockMvc.perform(post("/reports/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(report)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.messages", containsInAnyOrder(
                        "Report ID must not be empty",
                        "Employee ID must not be empty",
                        "Business purpose must not be empty",
                        "Submission date must not be in the future"
                )));
    }
    // TODO: Add below conditionals to ExpenseReportValidator:
    // "Invalid currency: ZZZ",
    // "Expense item category must not be empty",
    // "Expense item amount must be positive",
    // "Expense date cannot be in the future",
    // "Expense item description must not be empty"

    @Test
    @DisplayName("Missing expense report fields should trigger validation errors")
    public void missingExpenseReportFieldsShouldReturnErrors() throws Exception {
        String emptyJson = "{}";

        mockMvc.perform(post("/reports/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.messages", containsInAnyOrder(
                        "Report ID must not be empty",
                        "Employee ID must not be empty",
                        "Business purpose must not be empty",
                        "Currency must not be null",
                        "Submission date must not be null",
                        "Items must not be null"
                )));
    }
}
