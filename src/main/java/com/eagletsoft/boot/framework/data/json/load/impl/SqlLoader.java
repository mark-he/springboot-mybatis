package com.eagletsoft.boot.framework.data.json.load.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.eagletsoft.boot.framework.common.utils.ApplicationUtils;
import com.eagletsoft.boot.framework.data.json.load.ILoader;
import com.eagletsoft.boot.framework.data.json.meta.LoadOne;
import org.apache.commons.beanutils.PropertyUtils;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SqlLoader implements ILoader {

	private Object produceJSONObject(Object obj, String refId, String[] fieldset) {
		if (null != obj && obj.getClass().isArray()) {
			Map<String, Object> objMap = new HashMap<>();
			Object ref = Array.get(obj, 0);
			objMap.put(refId, ref);
			int i = 1;
			for (String f :fieldset) {
				objMap.put(f, Array.get(obj, i++));
			}
			return objMap;
		}
		else {
			return obj;
		}
	}

	@Override
	public Map<Object, Object> loadOneInBatch(Collection<Object> batch, LoadOne loadOne) {
		Map<Object, Object> map = new HashMap<>();
		if (batch.isEmpty()) {
			return map;
		}

		BaseMapper mapper = (BaseMapper)ApplicationUtils.getBean(BaseMapper.class, loadOne.target());
		List<?> ret = mapper.selectBatchIds(batch);
		try {
			for (Object obj : ret) {
				Object jsonObj = produceJSONObject(obj, loadOne.ref(), loadOne.fieldset());
				map.put(PropertyUtils.getProperty(jsonObj, loadOne.ref()), jsonObj);
			}
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		return map;
	}
	
	@Override
	public Object loadOne(Object value, LoadOne loadOne) {
		BaseMapper mapper = (BaseMapper)ApplicationUtils.getBean(BaseMapper.class, loadOne.target());
		Object obj = mapper.selectById((Serializable) value);
		return produceJSONObject(obj, loadOne.ref(), loadOne.fieldset());
	}

}
