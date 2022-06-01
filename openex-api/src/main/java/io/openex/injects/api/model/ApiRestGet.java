package io.openex.injects.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiRestGet {
    @JsonProperty("uri")
    private String uri;

    @JsonProperty("headers")
    private List<ApiHeader> headers;

    public ApiRestGet() {
        // Default constructor
    }

    public ApiRestGet(String uri, String body, List<ApiHeader> headers) {
        this.uri = uri;
        this.headers = headers;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public List<ApiHeader> getHeaders() {
        return headers;
    }

    public void setHeaders(List<ApiHeader> headers) {
        this.headers = headers;
    }
}
