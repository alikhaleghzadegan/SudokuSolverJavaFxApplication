package com.khaleghzadegan.logic.exception;

public class CanNotChangeFixedCellException extends RuntimeException {

    public CanNotChangeFixedCellException(String message) {
        super(message);
    }

    public CanNotChangeFixedCellException(String message, Throwable cause) {
        super(message, cause);
    }
}
