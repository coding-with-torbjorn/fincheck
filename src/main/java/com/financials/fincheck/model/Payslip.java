package com.financials.fincheck.model;

import java.time.LocalDate;
import java.util.Map;

// TODO: Replace all primitive doubles to Double wrapper class (So that integration test suite can ensure that grossSalary and netSalary are not empty)
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
     */
    private String bankAccount;

    /**
     * Information about the employer who issued the payslip.
     */
    private Employer employer;

    /**
     * Number of hours worked during the pay period.
     */
    private int hoursWorked;

    /**
     * Constructs a new {@code Payslip} with the specified employee and payment details.
     *
     * @param employeeId     Unique identifier of the employee.
     * @param employeeName   Full name of the employee.
     * @param payPeriod      The pay period this payslip corresponds to (e.g., "2025-05").
     * @param paymentDate    The date on which the payment was issued.
     * @param grossSalary    Gross salary before any deductions.
     * @param deductions     Map of deduction names to their respective amounts.
     * @param netSalary      Net salary after all deductions have been applied.
     * @param paymentMethod  Method used to pay the employee (e.g., bank transfer, cheque).
     * @param bankAccount    Bank account (e.g., IBAN) where payment was made, if applicable.
     * @param employer       Information about the employer issuing the payslip.
     * @param hoursWorked    Total number of hours worked during the pay period.
     */
    public Payslip(String employeeId, String employeeName, String payPeriod, LocalDate paymentDate, double grossSalary, Map<String, Double> deductions, double netSalary, PaymentMethod paymentMethod, String bankAccount, Employer employer, int hoursWorked) {
        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.payPeriod = payPeriod;
        this.paymentDate = paymentDate;
        this.grossSalary = grossSalary;
        this.deductions = deductions;
        this.netSalary = netSalary;
        this.paymentMethod = paymentMethod;
        this.bankAccount = bankAccount;
        this.employer = employer;
        this.hoursWorked = hoursWorked;
    }

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