package com.financials.fincheck.dto;

import java.util.List;

/**
 * DTO representing the result of a validation request.
 * <p>
 * This record is returned by controllers (e.g., {@code InvoiceController}, {@code PayslipController})
 * to communicate whether the submitted data passed validation and any associated messages.
 *
 * @param success  Indicates whether the validation was successful.
 * @param messages A list of messages explaining the validation result.
 *                 For failed validations, these are error descriptions.
 *                 For successful validations, this includes a confirmation message.
 */
public record ValidationResponse(boolean success, List<String> messages) { }
