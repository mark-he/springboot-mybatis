package com.eagletsoft.boot.framework.data.json.meta;

import com.eagletsoft.boot.framework.data.json.load.Calculator;

import java.lang.annotation.*;

@Repeatable(value = Formulas.class) 
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Formula {
	String value() default "ret";
	String expression() default "";
	Class<? extends Calculator> calc();
	boolean batch() default true;
}
