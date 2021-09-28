package com.ttcsolutions.kwapp.core.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public interface ProxyService {
    CompletableFuture<Void> forward(HttpServletRequest request, HttpServletResponse response) throws IOException;

    CompletableFuture<HttpResponse<byte[]>> forwardRequest(HttpServletRequest request);

    static void forwardResponse(HttpResponse<byte[]> response, HttpServletResponse target) {
        target.setStatus(response.statusCode());
        response.headers().map()
          .forEach((k, headerValues) -> headerValues.forEach(v -> target.addHeader(k, v)));
        try {
            var os = target.getOutputStream();
            os.write(response.body());
            os.flush();
        } catch (IOException e) {
            throw new CompletionException("can't forward response", e);
        }
    }

    CompletableFuture<Void> forwardWithBody(HttpServletRequest request, HttpServletResponse response, Object body) throws IOException;

    CompletableFuture<Void> forwardWithBody(HttpServletRequest request, HttpServletResponse response, byte[] body) throws IOException;
}
