package com.maps.Utility;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.logging.log4j.util.Strings;
import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;
import org.springframework.web.multipart.MultipartFile;

import liquibase.util.file.FilenameUtils;

public class XssSanitizerUtil {

	private static final List<String> LIST_OF_EXTENSION = Arrays.asList(new String[] {"htm","html","txt"});
	
	private static List<String> XSS_INPUT_VALUES = Arrays.asList(new String[] {"<SCRIPT>","</SCRIPT>","<IFRAME>","SRC[","EVAL(","EXPRESSION","ALERT("
			,"JAVASCRIPT:","ONLOAD","VBSCRIPT:"});
	
	public static boolean sniffForXss(String input) {
		if(input == null || input.isEmpty())
			return false;
		
		return XSS_INPUT_VALUES.stream().anyMatch(xss->   input.toUpperCase().contains(xss));
	}
	
	public static byte[] sanitizeBytes(MultipartFile file) throws IOException {
		String fileExt = FilenameUtils.getExtension(file.getOriginalFilename());
		if(!Strings.isBlank(fileExt) && canContainsHtml(fileExt)) {
			return safeHtml(new String(file.getBytes())).getBytes();
		}
		return file.getBytes();
	}
	
	private static String safeHtml(String text) {
	      return  Jsoup.clean(text, Whitelist.basic());
	}

	private static boolean canContainsHtml(String fileExt) {
		
		return LIST_OF_EXTENSION.contains(fileExt.toLowerCase());
	}

}