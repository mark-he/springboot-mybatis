package com.eagletsoft.boot.framework.data.json.load;

import com.eagletsoft.boot.framework.data.json.meta.LoadOne;

import java.util.Collection;
import java.util.Map;

public interface ILoader {
	Object loadOne(Object value, LoadOne one);
	Map<Object, Object> loadOneInBatch(Collection<Object> batch, LoadOne one);
}
