package com.eagletsoft.boot.framework.data.constraint;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.eagletsoft.boot.framework.common.errors.ServiceException;
import com.eagletsoft.boot.framework.common.errors.StandardErrors;
import com.eagletsoft.boot.framework.common.utils.ApplicationUtils;
import com.eagletsoft.boot.framework.data.constraint.meta.Unique;
import com.eagletsoft.boot.framework.data.entity.Entity;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.reflect.Field;
import java.util.*;

public class ConstraintChecker {

	public static boolean checkUnique(Entity entity, String... fields) {
		try {
			QueryWrapper queryWrapper = new QueryWrapper();
			for (int i = 0; i < fields.length; i++) {
				Object obj = PropertyUtils.getProperty(entity, fields[i]);
				if (null != obj && !StringUtils.isEmpty(obj.toString())) {
					queryWrapper.ne(fields[i], obj);
				}
				else {
					//skip for any nulls
					return true;
				}
			}
			BaseMapper mapper = ApplicationUtils.getBean(BaseMapper.class, entity.getClass());
			Page page = new Page(1, 2);
			IPage iPage = mapper.selectPage(page, queryWrapper);
			if (iPage.getRecords().isEmpty() || (iPage.getRecords().size() == 1 && null != entity.getId() && ((Entity)iPage.getRecords().get(0)).getId().equals(entity.getId()))) {
				return true;
			}
			else {
				return false;
			}
		}
		catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public static void checkUnique(Entity entity) {
		Collection<UniqueObj> list = findUniques(entity);
		if (null != list) {
			for (UniqueObj uniqueObj : list) {
				if (!checkUnique(entity, uniqueObj.getFields().toArray(new String[]{}))) {
					throw new ServiceException(StandardErrors.INTERNAL_ERROR.getStatus(), uniqueObj.message);
				}
			}
		}
	}
	
	private static Collection<UniqueObj> findUniques(Entity entity) {
		Field[] fields = entity.getClass().getDeclaredFields();
		
		Map<String, UniqueObj> map = null;
		Collection<UniqueObj> ret = null;

		for (Field f : fields) {
			Unique uq = AnnotationUtils.findAnnotation(f, Unique.class);
			if (null != uq) {
				if (null == ret) {
					ret = new ArrayList<>();
				}
				if (Unique.ALONE.equals(uq.value())) {
					UniqueObj obj = new UniqueObj();
					
					obj.getFields().add(f.getName());
					if (null != uq.with()) {
						obj.getFields().addAll(Arrays.asList(uq.with()));
					}
					obj.setMessage(uq.message());
					ret.add(obj);
					
				}
				else {
					if (null == map) {
						map = new HashMap<>();
					}
					UniqueObj obj = map.get(uq.value());
					if (null == obj) {
						obj = new UniqueObj();
					}
					obj.getFields().add(f.getName());
					if (StringUtils.isEmpty(obj.getMessage())) {
						obj.setMessage(uq.message());
					}
				}
			}
		}
		
		if (null != map) {
			ret.addAll(map.values());
		}
		return ret;
	}
	
	public static class UniqueObj {
		private List<String> fields = new ArrayList<>();
		private String message;
		public List<String> getFields() {
			return fields;
		}
		public void setFields(List<String> fields) {
			this.fields = fields;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
	}
}
