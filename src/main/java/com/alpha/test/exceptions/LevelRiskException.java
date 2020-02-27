package com.alpha.test.exceptions;

public class LevelRiskException extends RuntimeException {
    public LevelRiskException() {
        super();
    }

    public LevelRiskException(String message) {
        super(message);
    }
}
