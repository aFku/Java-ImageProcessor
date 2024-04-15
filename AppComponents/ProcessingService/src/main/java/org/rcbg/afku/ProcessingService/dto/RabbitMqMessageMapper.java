package org.rcbg.afku.ProcessingService.dto;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class RabbitMqMessageMapper {

    public static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static RabbitMqRequest createRequestObject(String message) throws JsonProcessingException {
        return mapper.readValue(message, RabbitMqRequest.class);
    }

    public static String createStringMessageFromResponseObject(RabbitMqResponse response) throws JsonProcessingException {
        return mapper.writeValueAsString(response);
    }
}
