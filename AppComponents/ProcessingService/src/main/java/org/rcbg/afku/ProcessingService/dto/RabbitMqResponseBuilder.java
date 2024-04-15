package org.rcbg.afku.ProcessingService.dto;

public class RabbitMqResponseBuilder {

    private String rawFilename;
    private String processedFilename;
    private ImageProcessAttributes attributes;
    private Status status;
    private String messages;

    public RabbitMqResponseBuilder(){
        this.reset();
    }

    public void reset(){
        this.rawFilename = null;
        this.processedFilename = null;
        this. attributes = null;
        this.status = null;
        this.messages = null;
    }

    public RabbitMqResponseBuilder withRawFilename(String rawFilename){
        this.rawFilename = rawFilename;
        return this;
    }

    public RabbitMqResponseBuilder withProcessedFilename(String processedFilename){
        this.processedFilename = processedFilename;
        return this;
    }

    public RabbitMqResponseBuilder withAttributes(ImageProcessAttributes attributes){
        this.attributes = attributes;
        return this;
    }

    public RabbitMqResponseBuilder withStatus(Status status){
        this.status = status;
        return this;
    }

    public RabbitMqResponseBuilder withMessage(String message){
        this.messages = message;
        return this;
    }

    public RabbitMqResponse build(){
        ProcessStatus processStatus = new ProcessStatus(this.status, this.messages);
        RabbitMqResponse response = new RabbitMqResponse();
        response.setProcessStatus(processStatus);
        response.setRawFilename(this.rawFilename);
        response.setProcessedFilename(this.processedFilename);
        response.setAttributes(this.attributes);
        return response;
    }
}
