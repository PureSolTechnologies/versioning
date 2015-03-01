package com.puresoltechnologies.versioning;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;
import com.fasterxml.jackson.databind.ObjectWriter;

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
