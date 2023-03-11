package com.ams.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class TransactionConnectionException extends RuntimeException {
   
	private static final long serialVersionUID = 1L;

    public TransactionConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
    public TransactionConnectionException(String message) {
        super(message);
    }
    public TransactionConnectionException(Throwable cause) {
        super(cause);
    }
}