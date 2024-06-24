package com.salesmanager.core.business.utils;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StringToList {

    

    public static List<String> convertStringToList(String str) {
        if (StringUtils.isEmpty(str)){
            return null;
        }
        return Arrays.stream(str.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }
}
