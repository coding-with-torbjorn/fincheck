package com.financials.fincheck.model;

import java.time.LocalDate;
import java.util.Map;

/**
 * Represents an employee's payslip, containing payment and employment details for a specific pay period.
 * <p>
 * This class is used in validation processes to ensure salary data, deductions, and payment methods
 * conform to expected business rules.
 */
public class Payslip {

    /**
     * Unique identifier for the employee.
     */
    private String employeeId;

    /**
     * Full name of the employee receiving the payslip.
     */
    private String employeeName;

    /**
     * The pay period the payslip refers to (e.g., "2025-05").
     */
    private String payPeriod;

    /**
     * Date the payment was issued (e.g., "2025-05-25").
     */
    private LocalDate paymentDate;

    /**
     * Gross salary before deductions.
     */
    private double grossSalary;

    /**
     * A map of deduction names (e.g., "Tax", "Pension") to deduction amounts (e.g., "incomeTax": 750.00).
     */
    private Map<String, Double> deductions;

    /**
     * Net salary after all deductions.
     */
    private double netSalary;

    /**
     * Method used to pay the employee (e.g., bank transfer, cheque).
     */
    private PaymentMethod paymentMethod;

    /**
     * Bank account to which the payment was made (used for direct transfers).
     * Example format: IBAN (e.g., "IE29AIBK93115212345678").
     * TODO: Consider validating format against country-specific IBAN rules.
     */
    private String bankAccount; // e.g. "IE29AIBK93115212345678".

    /**
     * Information about the employer who issued the payslip.
     */
    private Employer employer;

    /**
     * Number of hours worked during the pay period.
     */
    private int hoursWorked;

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getPayPeriod() {
        return payPeriod;
    }

    public void setPayPeriod(String payPeriod) {
        this.payPeriod = payPeriod;
    }

    public LocalDate getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDate paymentDate) {
        this.paymentDate = paymentDate;
    }

    public double getGrossSalary() {
        return grossSalary;
    }

    public void setGrossSalary(double grossSalary) {
        this.grossSalary = grossSalary;
    }

    public double getNetSalary() {
        return netSalary;
    }

    public void setNetSalary(double netSalary) {
        this.netSalary = netSalary;
    }

    public Map<String, Double> getDeductions() {
        return deductions;
    }

    public void setDeductions(Map<String, Double> deductions) {
        this.deductions = deductions;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public int getHoursWorked() {
        return hoursWorked;
    }

    public void setHoursWorked(int hoursWorked) {
        this.hoursWorked = hoursWorked;
    }
}