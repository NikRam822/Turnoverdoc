package com.turnoverdoc.turnover.controllers.handler;

import com.turnoverdoc.turnover.error.types.NotFoundException;
import com.turnoverdoc.turnover.error.types.TimeoutException;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.net.URI;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

public class RestTemplateErrorHandler implements ResponseErrorHandler {
    @Override
    public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
        ResponseErrorHandler.super.handleError(url, method, response);
    }

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (
                response.getStatusCode().series() == CLIENT_ERROR
                        || response.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        switch (response.getStatusCode()) {
            case NOT_FOUND:
                throw new NotFoundException(response.getStatusText());
            case GATEWAY_TIMEOUT:
                throw new TimeoutException(response.getStatusText());
        }
    }
}
