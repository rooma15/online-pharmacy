package com.epam.jwd.httpSender;
import com.epam.jwd.exception.EmptyRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

public class HttpSender {
    private final HttpClient httpClient;
    private HttpRequest httpRequest;


    public HttpSender() {
        httpClient = HttpClient.newHttpClient();
        httpRequest = null;
    }

    public void setHttpRequest(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    public static class HttpRequestBuilder{
        private final HttpRequest.Builder builder = HttpRequest.newBuilder();

        public HttpRequestBuilder uri(String uri){
            builder.uri(URI.create(uri));
            return this;
        }

        public HttpRequestBuilder POST(Map<String, String> params) throws JsonProcessingException {
            ObjectMapper objectMapper = new ObjectMapper();
            String requestBody = objectMapper.writeValueAsString(params);
            builder.POST(HttpRequest.BodyPublishers.ofString(requestBody));
            return this;
        }

        public HttpRequestBuilder headers(String... headers){
            builder.headers(headers);
            return this;
        }

        public HttpRequest build(){
            return builder.build();
        }
    }



    public static HttpRequestBuilder newBuilder(){
        return new HttpRequestBuilder();
    }

    public HttpResponse<String> send() throws IOException, InterruptedException, EmptyRequestException {
        if(httpRequest == null){
            throw new EmptyRequestException("request is null");
        }
        return httpClient.send(httpRequest,
                HttpResponse.BodyHandlers.ofString());
    }
}