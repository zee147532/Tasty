package com.tasty.app.config;

import com.tasty.app.exception.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.rmi.ServerException;

import static com.tasty.app.constant.Constant.NOT_FOUND;
import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
    private final Logger log = LoggerFactory.getLogger(RestTemplateResponseErrorHandler.class);
    @Override
    public boolean hasError(ClientHttpResponse httpResponse)
        throws IOException {

        return (
            httpResponse.getStatusCode().series() == CLIENT_ERROR
                || httpResponse.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse)
        throws IOException {
        if (httpResponse.getStatusCode()
            .series() == HttpStatus.Series.SERVER_ERROR) {
            log.error("LogMeal server error.");
            throw new ServerException("LogMeal server error.");
        } else if (httpResponse.getStatusCode()
            .series() == HttpStatus.Series.CLIENT_ERROR) {
            if (httpResponse.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.error(String.format(NOT_FOUND, "API in the LogMeal server"));
                throw new NotFoundException(String.format(NOT_FOUND, "API in the LogMeal server"));
            }
            log.error("Can't send the request to the LogMeal server.");
            throw new ServerException("Can't send the request to the LogMeal server.");
        }
    }
}
