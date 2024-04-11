package org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.rcbg.afku.ImageAdjusterApp.dto.ImageColorConversion;
import org.rcbg.afku.ImageAdjusterApp.dto.ImageProcessAttributes;

public class RabbitMqMessageMapper {

    public static ObjectMapper mapper;

    static {
        mapper = new ObjectMapper();
    }

    public static RabbitMqRequest createRequestObject(String rawFilename, ImageProcessAttributes attributes){
        RabbitMqRequest request = new RabbitMqRequest();
        request.setRawFilename(rawFilename);
        request.setAttributes(attributes);
        return request;
    }

    public static String requestObjectToJsonString(RabbitMqRequest request) throws JsonProcessingException {
        return mapper.writeValueAsString(request);
    }

    public static RabbitMqResponse JsonStringToResponse(String response) throws JsonProcessingException {
        return mapper.readValue(response, RabbitMqResponse.class);
    }
}
