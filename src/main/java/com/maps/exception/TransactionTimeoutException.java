package com.maps.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class TransactionTimeoutException extends RuntimeException {
   
	private static final long serialVersionUID = 1L;

    public TransactionTimeoutException(String message, Throwable cause) {
        super(message, cause);
    }

    public TransactionTimeoutException(String message) {
        super(message);
    }

    public TransactionTimeoutException(Throwable cause) {
        super(cause);
    }
}