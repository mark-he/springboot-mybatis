package com.eagletsoft.boot.framework.data.json.plugin;

import com.eagletsoft.boot.framework.data.json.context.ExtContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExtMapper extends ObjectMapper {
	
	public ExtMapper(ObjectMapper src) {
		super(src);
	}

	@Override
	public String writeValueAsString(Object value) throws JsonProcessingException {
		
		ExtContext.init();
		String ret = super.writeValueAsString(value);
		ExtContext.destroy();
		
		return ret;
	}

	@Override
	public byte[] writeValueAsBytes(Object value) throws JsonProcessingException {
		ExtContext.init();
		byte[] ret = super.writeValueAsBytes(value);
		ExtContext.destroy();
		return ret;
	}
}
