package com.maps.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class InvalidCaptchaException extends RuntimeException {
    
	private static final long serialVersionUID = 1L;

    public InvalidCaptchaException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidCaptchaException(String message) {
        super(message);
    }

    public InvalidCaptchaException(Throwable cause) {
        super(cause);
    }

}
