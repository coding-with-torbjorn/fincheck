package com.financials.fincheck.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.util.Currency;

public class CurrencyDeserializer extends JsonDeserializer<Currency> {
    @Override
    public Currency deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
        String code = p.getText().toUpperCase();
        try {
            return Currency.getInstance(code);
        } catch (IllegalArgumentException e) {
            throw new IOException("Invalid currency code: " + code);
        }
    }
}
