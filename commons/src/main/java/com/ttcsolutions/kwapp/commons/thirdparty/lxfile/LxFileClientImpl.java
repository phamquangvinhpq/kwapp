package com.ttcsolutions.kwapp.commons.thirdparty.lxfile;

import com.dslplatform.json.JsonReader;
import com.ttcsolutions.kwapp.commons.model.response.Response;
import com.ttcsolutions.kwapp.commons.util.Json;
import com.ttcsolutions.kwapp.commons.thirdparty.RestClient;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Service
@Log4j2
public class LxFileClientImpl implements LxFileClient {

    private final String uploadUrl;
    private final String bucket;
    private final RestClient httpClient;
    private static final JsonReader.ReadObject<Response<String>> RESPONSE_READER = Json.findReader(Response.class);

    public LxFileClientImpl(@Value("${lxfile.bucket}") String bucket, @Value("${lxfile.base-url}") String baseApiUrl , RestClient httpClient) {
        this.bucket = bucket;
        this.uploadUrl = baseApiUrl + "/files";
        this.httpClient = httpClient;
    }

    public CompletableFuture<Response<String>> upload(MultipartFile file, String location) {
        Map<String, Object> fileDataRequest = new HashMap<>();
        fileDataRequest.put("bucket", bucket);
        fileDataRequest.put("file", file);
        fileDataRequest.put("location", location);
        Map<String , String> formDataContentType = Collections.singletonMap("Content-Type", FormDataConverter.FORM_DATA_CONTENT_TYPE);
        return httpClient.post(fileDataRequest, RESPONSE_READER, uploadUrl, formDataContentType);
    }

}
