package com.eagletsoft.boot.framework.data.json;

import com.eagletsoft.boot.framework.common.utils.JsonUtils;
import com.eagletsoft.boot.framework.data.json.plugin.ExtBeanSerializerModifier;
import com.eagletsoft.boot.framework.data.json.plugin.ExtMapper;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ExtMapperFactory {
	public static ObjectMapper create() {
		ObjectMapper mapper = JsonUtils.createMapper();
		mapper.setSerializerFactory(mapper.getSerializerFactory().withSerializerModifier(new ExtBeanSerializerModifier()));
		return new ExtMapper(mapper);
	}
}
