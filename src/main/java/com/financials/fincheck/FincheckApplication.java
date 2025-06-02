package com.financials.fincheck;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// TODO: Decide upon test cases/suites, etc for FinCheck
// TODO: Create pipeline
// TODO: Once completed Invoice and Payslip validators/unit and integration tests, follow/research TDD
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
