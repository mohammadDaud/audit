package com.ams.common.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class CsvHelper {
	public static String[] TYPE = {"text","csv", "text/csv"};

	public static boolean isValidFormat(String contentType) {	
		log.info("File Type {}", contentType);
		return Arrays.stream(TYPE).anyMatch(contentType::equals);
	}
	public Iterable<CSVRecord> csvToData(InputStream inputStream) throws Exception {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		CSVParser csvParser = new CSVParser(fileReader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			return csvParser.getRecords();		 
		}
	}
}
