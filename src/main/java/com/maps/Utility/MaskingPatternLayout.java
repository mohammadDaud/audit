package com.maps.Utility;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;


public class MaskingPatternLayout extends PatternLayout {
    private Pattern multilinePattern;
    private List<String> maskPatterns = new ArrayList<>();

    public void addMaskPattern(String maskPattern) {
        maskPatterns.add(maskPattern);
        multilinePattern = Pattern.compile(maskPatterns.stream().collect(Collectors.joining("|")), Pattern.MULTILINE);
    }

    @Override
    public String doLayout(ILoggingEvent event) {
        return maskMessage(super.doLayout(event));
    }

    private String maskMessage(String message) {
        if (multilinePattern == null) {
            return message;
        }
        StringBuilder sb = new StringBuilder(message);
        Matcher matcher = multilinePattern.matcher(sb);
        
        while (matcher.find()) {
            IntStream.rangeClosed(1, matcher.groupCount())
        		 .forEach(group -> {
        			 if (matcher.group(group) != null) {
        				 int start = matcher.start(group);
        				 int end = matcher.end(group);
        				 int len =end-start;
        				 int paddingStart=0, paddingEnd=0;
        				 if( len > 5) {
        					paddingStart = len /4;
        					paddingEnd = len/4;
        				 }
        					 IntStream.range(matcher.start(group)+paddingStart, matcher.end(group)- paddingEnd)
        					 				.forEach(i -> sb.setCharAt(i, '*'));
        				 
                }
            });
        }
        return sb.toString();
    }
}