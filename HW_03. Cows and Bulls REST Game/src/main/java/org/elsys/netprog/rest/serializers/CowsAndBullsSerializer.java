package org.elsys.netprog.rest.serializers;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.elsys.netprog.rest.models.CowsAndBulls;

public class CowsAndBullsSerializer extends JsonSerializer<CowsAndBulls> {
    @Override
    public void serialize(CowsAndBulls value, JsonGenerator jgen, SerializerProvider provider) 
      throws IOException, JsonProcessingException {
    	String secret = null;
    	
    	if (!value.getSuccess()) {
        	secret = "****";
        } else {
        	secret = value.getSecret();
        }
    	
        jgen.writeStartObject();
        jgen.writeStringField("gameId", value.getId());
        jgen.writeNumberField("turnsCount", value.getTurnsCount());
        jgen.writeStringField("secret", secret);
        jgen.writeBooleanField("success", value.getSuccess());
        jgen.writeEndObject();
    }
}
