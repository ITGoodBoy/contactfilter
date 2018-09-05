package com.celestialapps.contactfilter.Utils;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RegexUtils {


    public static List<String> getNotRegexList(List<String> texts, String regGex) {

        StringBuilder builder = new StringBuilder();

        for (String text : texts) {
            builder.append(text);
            builder.append(System.lineSeparator());
        }

        String textsString = builder.toString();
        builder.setLength(0);

        return Pattern
                .compile(convertToNotRegex(regGex), Pattern.MULTILINE)
                .matcher(textsString)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());
    }


    public static String convertToNotRegex(String regEx) {
        return String.format("^((?!(%s)).)*$", regEx);
    }


}
