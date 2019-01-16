package com.eagletsoft.boot.framework.common.utils;

import org.springframework.util.CollectionUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanUtils {

    public static List<Field> findAllDeclaredFields(Class clazz) {
        List<Field> fields = new ArrayList<>();

        if (null != clazz) {
            fields.addAll(CollectionUtils.arrayToList(clazz.getDeclaredFields()));
            fields.addAll(findAllDeclaredFields(clazz.getSuperclass()));
        }
        return fields;
    }

    public static Map<String, Field> findAllDeclaredFieldMap(Class clazz) {
        Map<String, Field> fieldMap = new HashMap<>();

        if (null != clazz) {
            Field[] fields = clazz.getDeclaredFields();
            for (Field f : fields) {
                fieldMap.put(f.getName(), f);
            }
            fieldMap.putAll(findAllDeclaredFieldMap(clazz.getSuperclass()));
        }
        return fieldMap;
    }
}
