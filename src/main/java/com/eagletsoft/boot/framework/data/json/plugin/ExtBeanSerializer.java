package com.eagletsoft.boot.framework.data.json.plugin;

import com.eagletsoft.boot.framework.data.json.ExtRetriever;
import com.eagletsoft.boot.framework.data.json.FormulaDedutor;
import com.eagletsoft.boot.framework.data.json.context.ExtContext;
import com.eagletsoft.boot.framework.data.json.context.KeyValue;
import com.eagletsoft.boot.framework.data.json.meta.ExtView;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.BeanPropertyWriter;
import com.fasterxml.jackson.databind.ser.BeanSerializer;
import com.fasterxml.jackson.databind.ser.BeanSerializerBuilder;
import com.fasterxml.jackson.databind.ser.impl.BeanAsArraySerializer;
import com.fasterxml.jackson.databind.ser.impl.ObjectIdWriter;
import com.fasterxml.jackson.databind.ser.impl.UnwrappingBeanSerializer;
import com.fasterxml.jackson.databind.ser.std.BeanSerializerBase;
import com.fasterxml.jackson.databind.util.NameTransformer;

import java.io.IOException;
import java.util.Collection;
import java.util.Set;

public class ExtBeanSerializer extends BeanSerializerBase
{
    private static final long serialVersionUID = -3618164443537292758L;

    /*
    /**********************************************************
    /* Life-cycle: constructors
    /**********************************************************
     */

    @Override
    protected BeanSerializerBase withIgnorals(Set<String> set) {
        return this;
    }

    /**
     * @param builder Builder object that contains collected information
     *   that may be needed for serializer
     * @param properties Property writers used for actual serialization
     */
    public ExtBeanSerializer(JavaType type, BeanSerializerBuilder builder,
            BeanPropertyWriter[] properties, BeanPropertyWriter[] filteredProperties)
    {
        super(type, builder, properties, filteredProperties);
    }
    
    /**
     * Alternate copy constructor that can be used to construct
     * standard {@link BeanSerializer} passing an instance of
     * "compatible enough" source serializer.
     */
    protected ExtBeanSerializer(BeanSerializerBase src) {
        super(src);
    }

    protected ExtBeanSerializer(BeanSerializerBase src,
            ObjectIdWriter objectIdWriter) {
        super(src, objectIdWriter);
    }

    protected ExtBeanSerializer(BeanSerializerBase src,
            ObjectIdWriter objectIdWriter, Object filterId) {
        super(src, objectIdWriter, filterId);
    }
    
    protected ExtBeanSerializer(BeanSerializerBase src, String[] toIgnore) {
        super(src, toIgnore);
    }

    /*
    /**********************************************************
    /* Life-cycle: factory methods, fluent factories
    /**********************************************************
     */

    /**
     * Method for constructing dummy bean serializer; one that
     * never outputs any properties
     */
    public static BeanSerializer createDummy(JavaType forType)
    {
        return new BeanSerializer(forType, null, NO_PROPS, null);
    }

    @Override
    public JsonSerializer<Object> unwrappingSerializer(NameTransformer unwrapper) {
        return new UnwrappingBeanSerializer(this, unwrapper);
    }

    @Override
    public BeanSerializerBase withObjectIdWriter(ObjectIdWriter objectIdWriter) {
        return new ExtBeanSerializer(this, objectIdWriter, _propertyFilterId);
    }

    @Override
    public BeanSerializerBase withFilterId(Object filterId) {
        return new ExtBeanSerializer(this, _objectIdWriter, filterId);
    }

    @Override
    protected BeanSerializerBase withIgnorals(String[] toIgnore) {
        return new ExtBeanSerializer(this, toIgnore);
    }

    /**
     * Implementation has to check whether as-array serialization
     * is possible reliably; if (and only if) so, will construct
     * a {@link BeanAsArraySerializer}, otherwise will return this
     * serializer as is.
     */
    @Override
    protected BeanSerializerBase asArraySerializer()
    {
        /* Can not:
         * 
         * - have Object Id (may be allowed in future)
         * - have "any getter"
         * - have per-property filters
         */
        if ((_objectIdWriter == null)
                && (_anyGetterWriter == null)
                && (_propertyFilterId == null)
                ) {
            return new BeanAsArraySerializer(this);
        }
        // already is one, so:
        return this;
    }
    
    /*
    /**********************************************************
    /* JsonSerializer implementation that differs between impls
    /**********************************************************
     */

    /**
     * Main serialization method that will delegate actual output to
     * configured
     * {@link BeanPropertyWriter} instances.
     */
    @Override
    public final void serialize(Object bean, JsonGenerator gen, SerializerProvider provider)
        throws IOException
    {
        if (_objectIdWriter != null) {
            gen.setCurrentValue(bean); // [databind#631]
            _serializeWithObjectId(bean, gen, provider, true);
            return;
        }
        gen.writeStartObject();
        // [databind#631]: Assign current value, to be accessible by custom serializers
        gen.setCurrentValue(bean);

        if (null != bean.getClass().getAnnotation(ExtView.class)) {
        	ExtContext.get().addLevel();
	        try {
	        	if (ExtContext.get().getLevel() <= 1) {
	                ExtRetriever.getInstance().retrieve(bean);

		            Collection<KeyValue> exts = ExtContext.getExtends(bean);
		            if (null != exts) {
		                for (KeyValue kv : exts) {
		                	gen.writeObjectField(kv.getKey(), kv.getValue());
		                }
		            }
		            //process formula
		            Collection<KeyValue> formulaRets = FormulaDedutor.deduce(bean, exts);
		            if (null != formulaRets) {
		                for (KeyValue kv : formulaRets) {
		                	gen.writeObjectField(kv.getKey(), kv.getValue());
		                }
		            }
	            }
	        }
	        catch (Exception ex) {
	        	throw new RuntimeException(ex);
	        }
	        
            if (_propertyFilterId != null) {
                serializeFieldsFiltered(bean, gen, provider);
            } else {
                serializeFields(bean, gen, provider);
            }
        	ExtContext.get().subLevel();
        }
        else {
            if (_propertyFilterId != null) {
                serializeFieldsFiltered(bean, gen, provider);
            } else {
                serializeFields(bean, gen, provider);
            }
        }
        
        gen.writeEndObject();
    }
    
    /*
    /**********************************************************
    /* Standard methods
    /**********************************************************
     */

    @Override public String toString() {
        return "BeanSerializer for "+handledType().getName();
    }
}
