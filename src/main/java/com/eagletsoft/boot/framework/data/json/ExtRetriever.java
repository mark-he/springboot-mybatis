package com.eagletsoft.boot.framework.data.json;

import com.eagletsoft.boot.framework.data.json.context.ExtContext;
import com.eagletsoft.boot.framework.data.json.load.ILoader;
import com.eagletsoft.boot.framework.data.json.load.LoaderFactory;
import com.eagletsoft.boot.framework.data.json.meta.ExtView;
import com.eagletsoft.boot.framework.data.json.meta.LoadOne;
import com.eagletsoft.boot.framework.data.json.meta.LoadOnes;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;


public class ExtRetriever {
	
	private static ExtRetriever INSTANCE = new ExtRetriever();
	
	private ExtRetriever() {
	}
	
	public static ExtRetriever getInstance() {
		return INSTANCE;
	}
	
	private Object findValue(Object obj, boolean raw, String name) {
		try {
			if (raw) {
				return this.findFieldValue(obj, name);
			}
			else {
				return this.findMethodValue(obj, name);
			}
		}
		catch (Throwable ex) {
			return null;
		}
	}
	
	private Object findFieldValue(Object obj, String fieldName) {
		try {
			return PropertyUtils.getProperty(obj, fieldName);
		}
		catch (Throwable ex) {
			return null;
		}
	}

	private Object findMethodValue(Object obj, String methodName) {
		try {
			Method m = obj.getClass().getDeclaredMethod(methodName);
			return m.invoke(obj);
		}
		catch (Throwable ex) {
			return null;
		}
	}
	
	public void retrieve(Object obj) throws Exception {
		if (null == obj) {
			return;
		}
		
		Collection<ExtendAnnotation> refs = findExtendAnnotations(obj);
		if (refs.isEmpty()) {
			return;
		}
		else {
			if (ExtContext.isTrack(obj)) {
				return;
			}

			int level = ExtContext.get().getLevel();
			boolean inside = ExtContext.get().isInsideCollection();
			
			for (ExtendAnnotation f: refs) {
				if (f.getMapping() instanceof LoadOne) {
					LoadOne one = (LoadOne)f.getMapping();
					if (!inside || one.batch()) {
						Object v =this.findValue(obj, f.raw, StringUtils.isEmpty(one.src()) ? f.getName() : one.src());
						if (null == v) {
							continue;
						}
						ILoader loader = LoaderFactory.getInstance().createLoader(one.loader());
						Object r = loader.loadOne(v, one);
						ExtContext.addExtend(obj, one.value(), r);
					}
				}
			}
		}
	}
	
	public void retrieve(Collection col) throws Exception {
		if (col.isEmpty()) {
			return;
		}
		Object first = col.iterator().next();
		Collection<ExtendAnnotation> annotations = findExtendAnnotations(first);
		for (ExtendAnnotation f : annotations) {
			if (f.getMapping() instanceof LoadOne) {
				LoadOne one = (LoadOne)f.getMapping();
				ILoader loader = LoaderFactory.getInstance().createLoader(one.loader());
				
				if (one.batch()) {
					Set<Object> values = new HashSet<>();
					for (Object obj : col) {
						ExtContext.track(obj);
						Object v =this.findValue(obj, f.raw, StringUtils.isEmpty(one.src()) ? f.getName() : one.src());
						if (null != v) {
							values.add(v);
						}
					}
					Map batch = loader.loadOneInBatch(values, one);
					for (Object obj : col) {
						Object v =this.findValue(obj, f.raw, StringUtils.isEmpty(one.src()) ? f.getName() : one.src());
						if (null != v) {
							Object r = batch.get(v);
							if (null != r) {
								ExtContext.addExtend(obj, one.value(), r);
							}
						}
					}
				}

			}

		}
	}
	
	public Collection<ExtendAnnotation> findExtendAnnotations(Object value) throws Exception {
		Collection<ExtendAnnotation> refs = null;
		ExtView extendType = value.getClass().getAnnotation(ExtView.class);
		if (null != extendType) {
			Field[] fields = value.getClass().getDeclaredFields();
			for (Field f : fields) {
				Annotation[] as = f.getDeclaredAnnotations();
				for (Annotation a : as) {
					if (a instanceof LoadOne) {
						if (refs == null) {
							refs = new ArrayList<>();
						}
						refs.add(new ExtendAnnotation(true, f.getName(), a));
					} else if (a instanceof LoadOnes) {
						if (refs == null) {
							refs = new ArrayList<>();
						}
						LoadOnes ones = (LoadOnes)a;
						for (LoadOne o : ones.value()) {
							refs.add(new ExtendAnnotation(true, f.getName(), o));
						}
					}
				}
			}
			
			Method[] methods = value.getClass().getDeclaredMethods();
			for (Method f : methods) {
				Annotation[] as = f.getDeclaredAnnotations();
				for (Annotation a : as) {
					if (a instanceof LoadOne) {
						if (refs == null) {
							refs = new ArrayList<>();
						}
						refs.add(new ExtendAnnotation(false, f.getName(), a));
					} else if (a instanceof LoadOnes) {
						if (refs == null) {
							refs = new ArrayList<>();
						}
						LoadOnes ones = (LoadOnes)a;
						for (LoadOne o : ones.value()) {
							refs.add(new ExtendAnnotation(false, f.getName(), o));
						}
					}
				}
			}
			
		}
		if (null == refs) {
			refs = CollectionUtils.EMPTY_COLLECTION;
		}
		
		return refs;
	}
	
	public class ExtendAnnotation {
		private boolean raw;
		private String name;
		private Object mapping;
		public ExtendAnnotation(boolean raw, String name, Object mapping) {
			super();
			this.raw = raw;
			this.name = name;
			this.mapping = mapping;
		}
		public Object getMapping() {
			return mapping;
		}
		public void setMapping(Object mapping) {
			this.mapping = mapping;
		}
		public boolean isRaw() {
			return raw;
		}
		public void setRaw(boolean raw) {
			this.raw = raw;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
	}
}
