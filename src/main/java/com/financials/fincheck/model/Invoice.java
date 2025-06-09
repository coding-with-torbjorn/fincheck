package com.financials.fincheck.model;

/**
 * Represents a financial invoice submitted for validation.
 * <p>
 * This model contains minimal fields necessary to assess invoice correctness
 * such as its identifier, monetary value, and associated currency.
 */
public class Invoice {

    /**
     * Unique identifier for the invoice (e.g., "INV-001").
     */
    private String invoiceNumber;

    /**
     * Total amount stated on the invoice.
     * Must be non-negative and expressed in the given currency.
     */
    private Double amount;

    /**
     * ISO 4217 currency code (e.g., "EUR", "USD", "GBP") used in the invoice.
     */
    private String currency;

    /**
     * Constructs an {@code Invoice} object with the specified invoice number, amount, and currency.
     *
     * @param invoiceNumber A unique string identifying the invoice.
     * @param amount        The monetary value of the invoice.
     * @param currency      The currency code associated with the amount.
     */
    public Invoice(String invoiceNumber, Double amount, String currency) {
        this.invoiceNumber = invoiceNumber;
        this.amount = amount;
        this.currency = currency;
    }

    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
