package com.ttcsolutions.kwapp.commons.thirdparty;

import com.dslplatform.json.JsonReader;
import com.dslplatform.json.JsonWriter;
import com.ttcsolutions.kwapp.commons.exception.BusinessException;
import com.ttcsolutions.kwapp.commons.util.ErrorCode;
import com.ttcsolutions.kwapp.commons.util.Json;
import com.ttcsolutions.kwapp.commons.thirdparty.lxfile.FormDataConverter;
import lombok.RequiredArgsConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class RestClient {
  private static final Logger log = LogManager.getLogger(RestClient.class);
  private final HttpClient httpClient;

  public <RS> CompletableFuture<RS> get(JsonReader.ReadObject<RS> reader, String url, Map<String, String> headers) {
    var httpRequest = addHeader(url, headers).GET().build();

    return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofByteArray())
      .thenApply(response -> handlerResponse(reader, url, response));
  }

  public <RQ, RS> CompletableFuture<RS> put(RQ request, JsonWriter.WriteObject<RQ> writer, JsonReader.ReadObject<RS> reader, String url, Map<String, String> headers) {

    HttpRequest.BodyPublisher bodyPublisher = (request == null) ?
      HttpRequest.BodyPublishers.noBody() : HttpRequest.BodyPublishers.ofByteArray(Json.encode(request, writer));
    var httpRequest = addHeader(url, headers)
      .PUT(bodyPublisher)
      .build();
    return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofByteArray())
      .thenApply(response -> handlerResponse(reader, url, response));
  }

  public <RQ, RS> CompletableFuture<RS> post(RQ request, JsonWriter.WriteObject<RQ> writer, JsonReader.ReadObject<RS> reader, String url, Map<String, String> headers) {
    var body = Json.encode(request, writer);
    var httpRequest = addHeader(url, headers)
      .POST(HttpRequest.BodyPublishers.ofByteArray(body))
      .build();
    return httpClient.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofByteArray())
      .thenApply(response -> handlerResponse(reader, url, response));
  }

  public <RS> CompletableFuture<RS> post(Map<String, Object> formDataRequest, JsonReader.ReadObject<RS> reader, String url, Map<String, String> headers) {
    var request = addHeader(url, headers)
            .POST(HttpRequest.BodyPublishers.ofByteArrays(FormDataConverter.convert(formDataRequest)))
            .build();
    return httpClient.sendAsync(request, HttpResponse.BodyHandlers.ofByteArray())
            .thenApply(response -> handlerResponse(reader, url, response));
  }

  private <RS> RS handlerResponse(JsonReader.ReadObject<RS> reader, String url, HttpResponse<byte[]> response) {
    if (response.statusCode() / 100 == 2) {
      log.info("Send result {} to {} successful", response.body(), url);
      return Json.decode(response.body(), reader);
    } else if (response.statusCode() / 100 == 4) {
      log.error("Send result to {} rejected, because: {}", url, response.body());
      throw new BusinessException(ErrorCode.INVALID_REQUEST, new String(response.body(),
        StandardCharsets.UTF_8));
    } else {
      log.error("Send result to {} got error, because: {}", url, response.body());
      throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR, new String(response.body(), StandardCharsets.UTF_8));
    }
  }

  private HttpRequest.Builder addHeader(String url, Map<String, String> headers) {
    var requestBuilder = HttpRequest.newBuilder()
      .uri(URI.create(url));
    headers.forEach(requestBuilder::header);
    return requestBuilder;
  }
}
