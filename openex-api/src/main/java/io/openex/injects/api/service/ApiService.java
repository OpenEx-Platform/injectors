package io.openex.injects.api.service;

import io.openex.injects.api.model.ApiRestGet;
import io.openex.injects.api.model.ApiRestPost;
import org.apache.hc.client5.http.ClientProtocolException;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ClassicHttpRequest;
import org.apache.hc.core5.http.HttpStatus;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.logging.Logger;

@Component
public class ApiService {

    private static final Logger LOGGER = Logger.getLogger(ApiService.class.getName());
    private final CloseableHttpClient httpclient = HttpClients.createDefault();

    private String executeHttp(ClassicHttpRequest request) throws IOException, ParseException {
        CloseableHttpResponse response = httpclient.execute(request);
        int status = response.getCode();
        String responseData = EntityUtils.toString(response.getEntity());
        if (status >= HttpStatus.SC_SUCCESS && status < HttpStatus.SC_REDIRECTION) {
            return responseData;
        } else {
            throw new ClientProtocolException("Unexpected response: " + responseData);
        }
    }

    public String executeRestPost(ApiRestPost post) throws IOException, ParseException {
        HttpPost httpPost = new HttpPost(post.getUri());
        post.getHeaders().forEach(apiHeader -> httpPost.setHeader(apiHeader.getKey(), apiHeader.getValue()));
        httpPost.setEntity(new StringEntity(post.getBody()));
        LOGGER.info("Sending post request to " + post.getUri());
        return executeHttp(httpPost);
    }

    public String executeRestGet(ApiRestGet get) throws IOException, ParseException {
        HttpGet httpGet = new HttpGet(get.getUri());
        get.getHeaders().forEach(apiHeader -> httpGet.setHeader(apiHeader.getKey(), apiHeader.getValue()));
        LOGGER.info("Sending get request to " + get.getUri());
        return executeHttp(httpGet);
    }
}
