package com.app.ecom.util;

import com.app.ecom.common.exception.InternalServerErrorException;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

@Slf4j
public class AppUtil {

    public static String convertToJson(Object o) {
        Assert.notNull(o, "Object must not be null.");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule()); // Register JavaTimeModule for handling Java 8 date/time types
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException var) {
            log.error("convertToJson() message: {}", var.getMessage());
            log.error("", var);
            throw new InternalServerErrorException(var.getMessage());
        }
    }

    public static <T> T convertJsonToObject(String json, Class<T> tClass) {
        try {
            JsonMapper mapper = JsonMapper.builder()
                    .visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
                    .visibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE)
                    .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    .build();
            return mapper.readValue(json, tClass);
        } catch (JsonProcessingException var) {
            log.error("getObjectToJson() message: {}", var.getMessage());
            log.error("", var);
            throw new InternalServerErrorException(var.getMessage());
        }

    }
}
