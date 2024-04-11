package org.rcbg.afku.ImageAdjusterApp.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.rcbg.afku.ImageAdjusterApp.responses.data.MetaData;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse extends MetaDataResponse{

    private List<String> messages = new ArrayList<>();

    public ErrorResponse(MetaData metaData) {
        this.metaData = metaData;
    }

    public void addMessage(String message){
        messages.add(message);
    }

    @Override
    public String toString(){
        return "{ \"metaData\": " + metaData.toString() + ", \"messages\": \"" + messages.toString() + "\"}";
    }
}
