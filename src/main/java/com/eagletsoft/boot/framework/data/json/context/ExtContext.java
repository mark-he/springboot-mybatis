package com.eagletsoft.boot.framework.data.json.context;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class ExtContext {
	private static Logger LOG = LoggerFactory.getLogger(ExtContext.class);
	private static ThreadLocal<Context> CONTEXT = new ThreadLocal<>();
	
	public static void addExtend(Object object, String key, Object value) {
		LOG.debug("Add Extend for  " + object);

		ExtContext.track(object);
		Context ctx = CONTEXT.get();
		Collection<KeyValue> extendSet = (TreeSet<KeyValue>)ctx.keep.get(object.toString());
		
		if (null == extendSet) {
			extendSet = new TreeSet<>();
			ctx.keep.put(object.toString(), extendSet);
		}
		extendSet.add(new KeyValue(key, value));
	}

	public static Collection<KeyValue> getExtends(Object object) {
		LOG.debug("get Extend for  " + object);
		
		Context ctx = CONTEXT.get();
		return (Collection<KeyValue>)ctx.keep.get(object.toString());
	}
	
	public static void track(Object object) {
		Context ctx = CONTEXT.get();
		if (ctx.track.contains(object.toString())) {
			ctx.duplicateTrack++;
			if (ctx.duplicateTrack > 2000) {
				throw new RuntimeException("There might be infinite loop!");
			}
		}
		else {
			ctx.track.add(object.toString());
		}
	}
	
	public static Context get() {
		return CONTEXT.get();
	}
	
	public static boolean isTrack(Object obj) {
		Context ctx = CONTEXT.get();
		return ctx.track.contains(obj.toString());
	}
	
	public static void init() {
		CONTEXT.set(new Context());
	}
	
	public static void destroy() {
		CONTEXT.remove();
	}
	
	public static class Context {
		private Map<String, Object> keep = new HashMap<>();
		private Set<Object> track = new HashSet<>();
		private int duplicateTrack = 0;
		private int level;
		private boolean insideCollection;
		
		public Map<String, Object> getKeep() {
			return keep;
		}
		public void setKeep(Map<String, Object> keep) {
			this.keep = keep;
		}
		public Set<Object> getTrack() {
			return track;
		}
		public void setTrack(Set<Object> track) {
			this.track = track;
		}
		
		public void addLevel() {
			level++;
		}
		
		public void subLevel() {
			level--;
		}
		
		public int getLevel() {
			return level;
		}
		public void setLevel(int level) {
			this.level = level;
		}
		public boolean isInsideCollection() {
			return insideCollection;
		}
		public void setInsideCollection(boolean insideCollection) {
			this.insideCollection = insideCollection;
		}
	}
}
