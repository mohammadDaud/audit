package com.ams.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class RiskProfileException extends RuntimeException {
   
	private static final long serialVersionUID = 1L;

    public RiskProfileException(String message, Throwable cause) {
        super(message, cause);
    }

    public RiskProfileException(String message) {
        super(message);
    }

    public RiskProfileException(Throwable cause) {
        super(cause);
    }
}