package com.eagletsoft.boot.framework.data.json;

import com.eagletsoft.boot.framework.data.json.context.ExtContext;
import com.eagletsoft.boot.framework.data.json.context.KeyValue;
import com.eagletsoft.boot.framework.data.json.load.Calculator;
import com.eagletsoft.boot.framework.data.json.meta.Formula;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.core.annotation.AnnotationUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

public class FormulaDedutor {
	public static Collection<KeyValue> deduce(Object bean, Collection<KeyValue> extendObjs) {
		Collection<KeyValue> formulaRets = new ArrayList<>();
		
		Set<Formula> formulas = AnnotationUtils.getDeclaredRepeatableAnnotations(bean.getClass(), Formula.class);
		if (!formulas.isEmpty()) {
			formulaRets = new ArrayList<>();
			try {
				boolean inside = ExtContext.get().isInsideCollection();
		        for (Formula formula : formulas) {
					if (!inside || formula.batch()) {
			        	Class<Calculator> clazz = (Class<Calculator>)formula.calc();
			        	if (null != clazz) {
			        		Calculator calc = clazz.newInstance();
			        		Object value = calc.calc(bean, extendObjs);
			        		KeyValue kv = new KeyValue(formula.value(), value);
			        		formulaRets.add(kv);
			        	}
					}
		        }
			}
			catch (Exception ex) {
				throw new RuntimeException(ex);
			}
		}
		else {
			formulaRets = CollectionUtils.EMPTY_COLLECTION;
		}
        return formulaRets;
	}
}
