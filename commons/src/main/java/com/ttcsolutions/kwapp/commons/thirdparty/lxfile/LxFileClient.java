package com.ttcsolutions.kwapp.commons.thirdparty.lxfile;

import com.ttcsolutions.kwapp.commons.model.response.Response;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

public interface LxFileClient {
    CompletableFuture<Response<String>> upload(MultipartFile file, String location);
}
