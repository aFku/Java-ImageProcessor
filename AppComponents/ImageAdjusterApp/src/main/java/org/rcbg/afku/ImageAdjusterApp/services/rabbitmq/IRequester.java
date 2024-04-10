package org.rcbg.afku.ImageAdjusterApp.services.rabbitmq;

import java.io.IOException;

public interface IRequester {
    public void sendMessage(String message) throws IOException;
}
