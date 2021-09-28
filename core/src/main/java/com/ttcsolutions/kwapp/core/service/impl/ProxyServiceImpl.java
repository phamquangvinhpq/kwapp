package com.ttcsolutions.kwapp.core.service.impl;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.ttcsolutions.kwapp.core.service.ProxyService;
import com.ttcsolutions.kwapp.core.util.ForwardBodyHandler;
import com.ttcsolutions.kwapp.commons.util.Constant;
import com.ttcsolutions.kwapp.commons.util.Json;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
public class ProxyServiceImpl implements ProxyService {
  private static final Set<String> DISALLOWED_HEADERS_SET;

  static {
    // A case insensitive TreeSet of strings.
    TreeSet<String> treeSet = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
    treeSet.addAll(Set.of("connection", "content-length",
      "date", "expect", "from", "host", "upgrade", "via", "warning"));
    DISALLOWED_HEADERS_SET = Collections.unmodifiableSet(treeSet);
  }

  private final HttpClient httpClient;
  private final String baseApiUrl;
  private final int ignorePrefixLength;

  public ProxyServiceImpl(HttpClient httpClient, @Value("${kwapp.base-api-url}") String baseApiUrl,
                          @Value("${server.servlet.context-path}") String contextPath) {
    this.httpClient = httpClient;
    this.baseApiUrl = baseApiUrl;
    ignorePrefixLength = contextPath.length();
  }

  @Override
  public CompletableFuture<Void> forward(HttpServletRequest request, HttpServletResponse response) throws IOException {
    return doForward(request, response, newBodyPublisher(request));
  }

  @Override
  public CompletableFuture<HttpResponse<byte[]>> forwardRequest(HttpServletRequest request) {
    HttpRequest.Builder forwardReq = getBuilder(request, newBodyPublisher(request));
    return httpClient.sendAsync(forwardReq.build(), HttpResponse.BodyHandlers.ofByteArray());
  }

  HttpRequest.BodyPublisher newBodyPublisher(HttpServletRequest request) {
    long contentLength = request.getContentLengthLong();
    if (contentLength == 0) {
      return HttpRequest.BodyPublishers.noBody();
    }
    var bodyPublisher = HttpRequest.BodyPublishers.ofInputStream(() -> {
      try {
        return request.getInputStream();
      } catch (IOException e) {
        log.error("can't get input stream of request to forward", e);
        return null;
      }
    });
    if (contentLength > 0) {
      bodyPublisher = HttpRequest.BodyPublishers.fromPublisher(bodyPublisher, contentLength);
    }
    return bodyPublisher;
  }

  @Override
  public CompletableFuture<Void> forwardWithBody(HttpServletRequest request, HttpServletResponse response, Object body) throws IOException {
    return forwardWithBody(request, response, Json.encode(body));
  }

  @Override
  public CompletableFuture<Void> forwardWithBody(HttpServletRequest request, HttpServletResponse response, byte[] body) throws IOException {
    var bodyPublisher = HttpRequest.BodyPublishers.ofByteArray(body);
    return doForward(request, response, bodyPublisher);
  }

  private CompletableFuture<Void> doForward(HttpServletRequest request, HttpServletResponse response, HttpRequest.BodyPublisher bodyPublisher) throws IOException {
    HttpRequest.Builder forwardReq = getBuilder(request, bodyPublisher);
    return httpClient.sendAsync(forwardReq.build(), new ForwardBodyHandler(response))
      .thenApply(resp -> null);
  }

  private HttpRequest.Builder getBuilder(HttpServletRequest request, HttpRequest.BodyPublisher bodyPublisher) {
    var forwardReq = HttpRequest.newBuilder(resolveTargetUri(request))
      .method(request.getMethod(), bodyPublisher);
    forwardHeaders(forwardReq, request);
    return forwardReq;
  }

  private void forwardHeaders(HttpRequest.Builder builder, HttpServletRequest request) {
    var headers = request.getHeaderNames();
    String header;
    while (headers.hasMoreElements()) {
      header = headers.nextElement();
      if (DISALLOWED_HEADERS_SET.contains(header)) {
        continue;
      }
      var values = request.getHeaders(header);
      while (values.hasMoreElements()) {
        builder.header(header, values.nextElement());
      }
    }
  }

  private URI resolveTargetUri(HttpServletRequest request) {
    var requestURI = request.getRequestURI();
    var builder = new StringBuilder()
      .append(baseApiUrl)
      .append(requestURI, ignorePrefixLength, requestURI.length());
    if (request.getQueryString() != null) {
      builder.append('?').append(request.getQueryString());
    }
    return URI.create(builder.toString());
  }
}
