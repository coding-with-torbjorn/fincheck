package com.financials.fincheck.model;

/**
 * Enum representing the supported methods of salary payment for a payslip.
 * <p>
 * These values are used in {@code Payslip} to specify
 * how the employee was paid and may influence validation rules (e.g., requiring a bank account).
 */
public enum PaymentMethod {

    /**
     * Payment made via a bank-to-bank transfer.
     */
    BANK_TRANSFER,

    /**
     * Payment issued via a physical cheque.
     */
    CHEQUE,

    /**
     * Payment made in physical cash.
     */
    CASH,

    /**
     * Automated deposit directly into the employee’s account.
     */
    DIRECT_DEPOSIT
}