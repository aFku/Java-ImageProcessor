package org.rcbg.afku.ImageAdjusterApp.responses;

import org.rcbg.afku.ImageAdjusterApp.dto.ProcessedImageDto;
import org.rcbg.afku.ImageAdjusterApp.dto.RawImageDto;
import org.rcbg.afku.ImageAdjusterApp.dto.rabbitmq.RabbitMqRequest;
import org.rcbg.afku.ImageAdjusterApp.responses.data.MetaData;
import org.rcbg.afku.ImageAdjusterApp.responses.data.ProcessedImageResponseData;
import org.rcbg.afku.ImageAdjusterApp.responses.data.RawImageResponseData;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

public class ResponseFactory {

    private static MetaData generateMetadata(String uri, int httpValue, String type){
        MetaData metaData = new MetaData();
        metaData.setUri(uri);
        metaData.setStatusCode(httpValue);
        metaData.setContentType(type);
        return metaData;
    }

    public static ResponseEntity<ErrorResponse> createErrorResponse(String uri, HttpStatus status, List<String> messages) {
        ErrorResponse response = new ErrorResponse();
        response.setMetaData(generateMetadata(uri, status.value(), "object"));
        response.setMessages(messages);
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    public static ResponseEntity<RawImageResponse> createRawImageResponse(String uri, HttpStatus status, RawImageDto dto){
        RawImageResponse response = new RawImageResponse();
        response.setMetaData(generateMetadata(uri, status.value(), "object"));
        RawImageResponseData data = new RawImageResponseData(dto, "/content/" + dto.getFilename());
        response.setData(data);
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    public static ResponseEntity<ProcessedImageResponse> createProcessedImageResponse(String uri, HttpStatus status, ProcessedImageDto dto){
        ProcessedImageResponse response = new ProcessedImageResponse();
        response.setMetaData(generateMetadata(uri, status.value(), "object"));
        ProcessedImageResponseData data = new ProcessedImageResponseData(dto, "/content/" + dto.getFilename());
        response.setData(data);
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    public static ResponseEntity<ProcessedImageListResponse> createProcessedImageListResponse(String uri, HttpStatus status, List<ProcessedImageDto> dtos){
        ProcessedImageListResponse response = new ProcessedImageListResponse();
        response.setMetaData(generateMetadata(uri, status.value(), "list"));
        List<ProcessedImageResponseData> dataList = new ArrayList<>();
        for(ProcessedImageDto dto : dtos){
            ProcessedImageResponseData data = new ProcessedImageResponseData(dto, "/content/" + dto.getFilename());
            dataList.add(data);
        }
        response.setData(dataList);
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }

    public static ResponseEntity<RabbitMqRequestResponse> createRabbitMqRequestResponse(String uri, HttpStatus status, RabbitMqRequest request){
        RabbitMqRequestResponse response = new RabbitMqRequestResponse();
        response.setMetaData(generateMetadata(uri, status.value(), "object"));
        response.setData(request);
        return new ResponseEntity<>(response, new HttpHeaders(), status);
    }


}
