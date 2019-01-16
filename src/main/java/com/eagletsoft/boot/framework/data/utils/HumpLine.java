package com.eagletsoft.boot.framework.data.utils;

public class HumpLine {

    //下划线格式名称转驼峰格式名称
    public static String lineToHump(String str){
        StringBuilder result = new StringBuilder();
        if (str != null && str.length() > 0) {
            boolean flag = false;
            for (int i = 0; i < str.length(); i++) {
                char ch = str.charAt(i);
                if ("_".charAt(0) == ch) {
                    flag = true;
                } else {
                    if (flag) {
                        result.append(Character.toUpperCase(ch));
                        flag = false;
                    } else {
                        result.append(ch);
                    }
                }
            }
        }
        return result.toString();
    }

    //驼峰格式名称转下划线格式名称
    public static String humpToLine(String str){
        StringBuilder result = new StringBuilder();
        if (str != null && str.length() > 0) {
            result.append(str.substring(0, 1).toLowerCase());
            for (int i = 1; i < str.length(); i++) {
                char ch = str.charAt(i);
                if (Character.isUpperCase(ch)) {
                    result.append("_");
                    result.append(Character.toLowerCase(ch));
                } else {
                    result.append(ch);
                }
            }
        }
        return result.toString();
    }
}
