package com.sumscope.security.spring_security.Utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @author xuejian.sun
 * @date 2018/3/22
 */
@Slf4j
public class JsonUtil {

    private JsonUtil() {
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                .getSerializerProvider();
    }

    public static String writeValueAsString(Object object) throws JsonProcessingException {
        return OBJECT_MAPPER.writeValueAsString(object);
    }

    public static <T> T readValue(String content, Class<T> valueType){
        try {
            return OBJECT_MAPPER.readValue(content, valueType);
        } catch (IOException e) {
            log.error(e.getMessage());
            return null;
        }
    }
}
