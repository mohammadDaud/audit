package com.ams.common.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MessageSourceService {

	@Autowired
	MessageSource messageSource;
	public static final String NOT_FOUND = "error.notfound";
	public static final String ALREADY_EXIST = "error.alreadyExist";
	public static final String ACCESS_DENIED = "error.accessDenied";
	public static final String INVALID_STAGE = "invalid.stage";
	public static final String INVALID = "error.invalid";
	public static final String ERROR_EMAIL_SEND = "error.email";
	public static final String LINK_EXPIRED = "link.expired";
	public static final String WRONG_PASSWORD = "wrong.password";
	public static final String INVALID_CREDENTIALS= "error.credentials";
	public static final String ERROR_CONNECTION = "error.connection";
	public static final String ENCRYPTION = "error.encryption";
	public static final String DECRYPTION = "error.decryption";
	public static final String USER_BLOCKED = "blocked";
	public static final String USER_INACTIVE = "inactive";
	public static final String USER_LOCKED = "locked";
	public static final String ACC_EXPIRED = "account.expired";
	public static final String LOGIN_TIME = "logintime";
	public static final String PASSWORD_REUSE = "reusePassword";
	public static final String ERROR_PROCESS_TERMINAL = "error.processing";
	public static final String FILE_MISSING_HEADER = "missingheader";
	public static final String FILE_SERIES_INC = "incorrectSeries";
	public static final String FILE_FORMAT = "invalid.format";
	public static final String INVALID_COLUMN = "invalid.column";
	public static final String RECORD_EMPTY = "record.empty";
	public static final String RECORD_INVALID = "record.invalid";
	public static final String RECORD_LENGTH = "record.length";
	public static final String RECORD_MAX_LENGTH = "record.morelength";
	public static final String RECORD_PATTERN = "record.pattern";
	public static final String RECORD_UNIQUE = "record.unique";
	public static final String UNDEFINED = "error.undefined";

	
	public String getMessage(String key) {
		return messageSource.getMessage(
				key, null,LocaleContextHolder.getLocale());
	}

	public String getMessage(String key, Object... objectString) {
		return messageSource.getMessage(
				key, objectString, LocaleContextHolder.getLocale());
	}
}

