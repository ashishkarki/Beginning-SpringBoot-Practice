package com.karkia.todo.error;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.StreamUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;

import java.io.IOException;
import java.nio.charset.Charset;

public class ToDoErrorHandler extends DefaultResponseErrorHandler {

    private Logger logger = LoggerFactory.getLogger(ToDoErrorHandler.class);

    @Override
    public void handleError(ClientHttpResponse response) {
        try {
            logger.error(response.getStatusCode().toString());
            logger.error(StreamUtils.copyToString(response.getBody(), Charset.defaultCharset()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
