package com.financials.fincheck.model;

import java.time.LocalDate;
import java.util.Currency;
import java.util.List;

public class ExpenseReport {
    private String reportId;
    private String employeeId;
    private String businessPurpose;
    private Currency currency;
    private LocalDate submissionDate;
    private List<ExpenseItem> items;

    public ExpenseReport(String reportId, String employeeId, String businessPurpose, Currency currency, LocalDate submissionDate, List<ExpenseItem> items) {
        this.reportId = reportId;
        this.employeeId = employeeId;
        this.businessPurpose = businessPurpose;
        this.currency = currency;
        this.submissionDate = submissionDate;
        this.items = items;
    }

    public String getReportId() {
        return reportId;
    }

    public void setReportId(String reportId) {
        this.reportId = reportId;
    }

    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getBusinessPurpose() {
        return businessPurpose;
    }

    public void setBusinessPurpose(String businessPurpose) {
        this.businessPurpose = businessPurpose;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public LocalDate getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(LocalDate submissionDate) {
        this.submissionDate = submissionDate;
    }

    public List<ExpenseItem> getItems() {
        return items;
    }

    public void setItems(List<ExpenseItem> items) {
        this.items = items;
    }
}
