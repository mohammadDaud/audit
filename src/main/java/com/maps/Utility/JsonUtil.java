package com.maps.Utility;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.maps.exception.ApplicationException;

import lombok.extern.log4j.Log4j2;

@Log4j2
public class JsonUtil {

	static ObjectMapper mapper = new ObjectMapper();

	public static <T> T fromString(String permission, Class<T> type) {

		try {
			mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
			return mapper.readValue(permission, type);
		} catch (IOException e) {
			throw new ApplicationException();
		}
	}

	public static boolean isEmpty(String json) {
		
		try {
			JsonNode jsonNode = mapper.readTree(json);
			return jsonNode.size()==0;
			
		} catch (IOException e) {
			log.error("Json Error", e);
		}
	
	return false;
	}
	
	public static boolean isEmpty(Object object) {
		
		try {
			JsonNode jsonNode = mapper.readTree(toString(object));
			return jsonNode.size()==0;
			
		} catch (IOException e) {
			log.error("Json Error", e);
		}
	
	return false;
	}
	
	public static String toString(Object object) {
		String jsonString = "";
		if(object == null)
			return null;
		
		try {
			mapper.registerModule(new JavaTimeModule());
			mapper = mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			jsonString = mapper.writeValueAsString(object);

		} catch (JsonProcessingException e) {
			log.error("Object to String  Error", e);
			throw new ApplicationException("JSON parse error");
		}
		return jsonString;
	}
	public static String toPrettyString(Object object) {
		String jsonString = "";
		if(object == null)
			return null;
		try {
			
			mapper.registerModule(new JavaTimeModule());
			mapper.setSerializationInclusion(Include.NON_NULL);
			mapper = mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
			jsonString = mapper.writer().withDefaultPrettyPrinter().writeValueAsString(object);

		} catch (JsonProcessingException e) {
			log.error("Object to Pretty String  Error", e);
			throw new ApplicationException("JSON parse error");
		}
		return jsonString;
	}
}
