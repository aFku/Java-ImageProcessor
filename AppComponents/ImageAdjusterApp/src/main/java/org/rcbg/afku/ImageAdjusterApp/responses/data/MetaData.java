package org.rcbg.afku.ImageAdjusterApp.responses.data;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class MetaData {
    private String uri;
    private int statusCode;
    private String timestamp;
    private String contentType;

    public MetaData(String uri, int statusCode, String contentType){
        this.timestamp = Instant.now().toString();
        this.uri = uri;
        this.statusCode = statusCode;
        this.contentType = contentType;
    }

    @Override
    public String toString(){
        return "{ \"timestamp\": \"" + this.timestamp + "\", \"uri\": \"" + this.uri + "\", \"statusCode\": \"" + this.statusCode + "\"}";
    }
}
