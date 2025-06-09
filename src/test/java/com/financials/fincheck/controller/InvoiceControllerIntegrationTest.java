package com.financials.fincheck.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.financials.fincheck.model.Invoice;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InvoiceControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Valid invoice should return 200 OK with success message")
    public void validInvoiceShouldReturnSuccess() throws Exception {
        Invoice invoice = new Invoice("INV001", 2500.0, "EUR");

        mockMvc.perform(post("/invoices/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invoice)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success", is(true)))
                .andExpect(jsonPath("$.messages[0]", is("Invoice is valid")));
    }

    @Test
    @DisplayName("Invalid invoice should return 400 Bad Request with error messages")
    public void invalidInvoiceShouldReturnErrors() throws Exception {
        Invoice invoice = new Invoice("", -100.0, "ABC"); // all invalid

        mockMvc.perform(post("/invoices/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(invoice)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.messages", containsInAnyOrder(
                        "Amount must not be negative",
                        "Invalid currency: ABC",
                        "Invoice number must not be empty"
                )));
    }

    @Test
    @DisplayName("Missing fields should trigger validation errors")
    public void missingFieldsShouldReturnErrors() throws Exception {
        String emptyJson = "{}";

        mockMvc.perform(post("/invoices/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(emptyJson))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success", is(false)))
                .andExpect(jsonPath("$.messages", containsInAnyOrder(
                        "Amount must be provided",
                        "Invalid currency: null",
                        "Invoice number must not be empty"
                )));
    }
}
