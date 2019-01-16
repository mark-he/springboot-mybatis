package com.eagletsoft.boot.framework.data.json.plugin;

import com.eagletsoft.boot.framework.data.json.context.ExtContext;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;


public class ExtMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

	@Override
	protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage)
			throws IOException, HttpMessageNotWritableException {
		ExtContext.init();
		super.writeInternal(object, type, outputMessage);
		ExtContext.destroy();
	}


}
