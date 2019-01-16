package com.eagletsoft.boot.framework.data.json.meta;

import com.eagletsoft.boot.framework.data.json.load.impl.SqlLoader;

import java.lang.annotation.*;

@Repeatable(value = LoadOnes.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface LoadOne {
	String src() default "";
	String value() default "ext";
	String ref() default "id";
	Class<?> target(); 
	String[] fieldset() default {};
	String filter() default "";
	Class<?> loader() default SqlLoader.class;
	boolean batch() default true;
}
