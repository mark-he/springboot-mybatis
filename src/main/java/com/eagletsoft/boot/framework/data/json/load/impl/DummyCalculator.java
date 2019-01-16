package com.eagletsoft.boot.framework.data.json.load.impl;


import com.eagletsoft.boot.framework.data.json.context.KeyValue;
import com.eagletsoft.boot.framework.data.json.load.Calculator;

import java.util.Collection;

public class DummyCalculator implements Calculator {

	@Override
	public Object calc(Object bean, Collection<KeyValue> exts) {
		if (null != exts) {
			for (KeyValue kv : exts) {
				System.out.println("===========" + kv.getKey() + "============" + kv.getValue());
			}
		}
		return "BBBB";
	}
}
