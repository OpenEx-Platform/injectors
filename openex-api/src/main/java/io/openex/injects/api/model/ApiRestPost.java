package io.openex.injects.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ApiRestPost {
    @JsonProperty("uri")
    private String uri;

    @JsonProperty("body")
    private String body;

    @JsonProperty("headers")
    private List<ApiHeader> headers;

    public ApiRestPost() {
        // Default constructor
    }

    public ApiRestPost(String uri, String body, List<ApiHeader> headers) {
        this.uri = uri;
        this.body = body;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
