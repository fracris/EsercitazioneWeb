package com.dipartimento.demowebapplications.exception;

public class RistoranteNotValidException extends RuntimeException {
    public RistoranteNotValidException(String errorMessage) {
        super(errorMessage);
    }
}
