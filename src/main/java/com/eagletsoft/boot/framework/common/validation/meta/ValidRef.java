package com.eagletsoft.boot.framework.common.validation.meta;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ METHOD, PARAMETER })
@Retention(RUNTIME)
public @interface ValidRef {
	Class value();
}
