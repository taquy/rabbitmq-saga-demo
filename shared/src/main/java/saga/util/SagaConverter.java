package saga.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SagaConverter {
	public static ObjectMapper mapper = new ObjectMapper();

	public static <T> String encode(T obj) throws JsonProcessingException {
		return mapper.writeValueAsString(obj);
	}

	@SuppressWarnings("unchecked")
	public static <T> T decode(String in, Class<?> cn) throws IOException {
		return (T) mapper.readValue(in, cn);
	}
}
