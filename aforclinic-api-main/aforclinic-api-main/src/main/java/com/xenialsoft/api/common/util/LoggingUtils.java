package com.xenialsoft.api.common.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@UtilityClass
public class LoggingUtils {

    // @formatter:off
    private static final ObjectMapper mapper = JsonMapper
            .builder()
            .addModule(new JavaTimeModule())
            .enable(SerializationFeature.INDENT_OUTPUT)
            .build();
    // @formatter:on

    public void debug(String format, Object arg) {
        if (log.isDebugEnabled()) {
            try {
                log.debug(format, mapper.writeValueAsString(arg));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
                log.debug(format, arg);
            }
        }
    }

}
