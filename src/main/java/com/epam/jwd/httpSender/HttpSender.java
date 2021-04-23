package com.epam.jwd.httpSender;
import com.epam.jwd.exception.EmptyRequestException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
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

    public static class HttpRequestBuilder implements HttpRequest.Builder {
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

        @Override
        public HttpRequest.Builder uri(URI uri) {
            return builder.uri(uri);
        }

        @Override
        public HttpRequest.Builder expectContinue(boolean b) {
            return builder.expectContinue(b);
        }

        @Override
        public HttpRequest.Builder version(HttpClient.Version version) {
            return builder.version(version);
        }

        @Override
        public HttpRequest.Builder header(String s, String s1) {
            return builder.header(s, s1);
        }

        @Override
        public HttpRequest.Builder timeout(Duration duration) {
            return builder.timeout(duration);
        }

        @Override
        public HttpRequest.Builder setHeader(String s, String s1) {
            return builder.setHeader(s, s1);
        }

        @Override
        public HttpRequest.Builder GET() {
            return builder.GET();
        }

        @Override
        public HttpRequest.Builder POST(HttpRequest.BodyPublisher bodyPublisher) {
            return builder.POST(bodyPublisher);
        }

        @Override
        public HttpRequest.Builder PUT(HttpRequest.BodyPublisher bodyPublisher) {
            return builder.PUT(bodyPublisher);
        }

        @Override
        public HttpRequest.Builder DELETE() {
            return builder.DELETE();
        }

        @Override
        public HttpRequest.Builder method(String s, HttpRequest.BodyPublisher bodyPublisher) {
            return builder.method(s, bodyPublisher);
        }

        @Override
        public HttpRequest.Builder copy() {
            return builder.copy();
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