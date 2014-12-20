package com.puresoltechnologies.commons.versioning;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectReader;
import org.codehaus.jackson.map.ObjectWriter;

public class JSONSerializer {

    public static String toJSONString(Object o) throws JsonGenerationException,
	    JsonMappingException, IOException {
	ObjectMapper mapper = new ObjectMapper();
	ObjectWriter writer = mapper.writerWithType(o.getClass());
	return writer.writeValueAsString(o);
    }

    public static <T> T fromJSONString(String s, Class<T> type)
	    throws JsonGenerationException, JsonMappingException, IOException {
	ObjectMapper mapper = new ObjectMapper();
	ObjectReader reader = mapper.reader(type);
	return reader.readValue(s);
    }

}
