package com.financials.fincheck.model;

/**
 * Represents the employer associated with a {@code Payslip}.
 * <p>
 * This record holds basic metadata about the company that issued the salary payment.
 *
 * @param companyName The legal name of the employer or company.
 * @param employerId  A unique identifier for the employer (e.g., internal code or tax ID).
 */
public record Employer(String companyName, String employerId) { }