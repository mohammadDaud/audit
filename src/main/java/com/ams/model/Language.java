package com.ams.model;

import oracle.security.crypto.util.InvalidFormatException;

public enum Language {
	EN("EN"),
	ENG("ENG"),
	FR("FR");

	private final String language;

	Language(String language) {
		this.language = language;
	}

	public String getLanguage() {
		return this.language;
	}

	public static Language getValue(String language) {
		  for(Language e: Language.values()) {
		    if(e.language.equals(language)) {
		      return e;
		    }
		  }
		  throw new InvalidFormatException("Invalid Language"); 
		}
}
