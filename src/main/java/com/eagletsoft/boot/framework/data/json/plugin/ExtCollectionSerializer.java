package com.eagletsoft.boot.framework.data.json.plugin;

import com.eagletsoft.boot.framework.data.json.ExtRetriever;
import com.eagletsoft.boot.framework.data.json.context.ExtContext;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;
import java.util.Collection;

public class ExtCollectionSerializer 
	extends JsonSerializer
{
	@Override
	public void serialize(Object value, JsonGenerator gen, SerializerProvider serializers)
			throws IOException, JsonProcessingException {
		Collection col = (Collection)value;
		try {
            ExtRetriever.getInstance().retrieve(col);
        }
        catch (Exception ex) {
        	ex.printStackTrace();
        	throw new RuntimeException(ex);
        }
    	ExtContext.get().setInsideCollection(true);
		gen.writeStartArray();
		for (Object obj : col) {
			gen.writeObject(obj);
		}
		gen.writeEndArray();
    	ExtContext.get().setInsideCollection(false);
	}
}