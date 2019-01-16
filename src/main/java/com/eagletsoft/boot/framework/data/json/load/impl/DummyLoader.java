package com.eagletsoft.boot.framework.data.json.load.impl;

import com.eagletsoft.boot.framework.data.json.load.ILoader;
import com.eagletsoft.boot.framework.data.json.meta.LoadOne;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class DummyLoader implements ILoader {
	
	@Override
	public Map<Object, Object> loadOneInBatch(Collection<Object> batch, LoadOne one) {
		Map<Object, Object> map = new HashMap<>();
		Map<String, Object> test = new HashMap<>();
		test.put("dummy", "dummy");
		map.put("402880f2646e85f001646ed26987001f", test);
		map.put(9L, test);
		return map;
	}
	
	@Override
	public Object loadOne(Object value, LoadOne one) {
		Map<String, Object> test = new HashMap<>();
		test.put("dummy", "dummy");
		return test;
	}

}
