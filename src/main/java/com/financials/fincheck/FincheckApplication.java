package com.financials.fincheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO: Create integration test suite for ExpenseReportController
// TODO: Focus on improving current tests/adding new ones (integration, etc). Decide upon test cases/suites, etc for FinCheck
/**
 * Entry point of the FinCheck application.
 * The FinCheck application is designed as a backend microservice for validating
 * financial documents such as invoices and payslips.
 */
@SpringBootApplication
public class FincheckApplication {

	public static void main(String[] args) {
		SpringApplication.run(FincheckApplication.class, args);
	}

}
