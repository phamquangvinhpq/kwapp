package com.ttcsolutions.kwapp.commons.util;

import java.io.ByteArrayInputStream;
import java.net.http.HttpResponse;

public class JsonBodyHandler<T> implements HttpResponse.BodyHandler<T> {
  private final Class<T> type;

  public static <T> JsonBodyHandler<T> jsonBodyHandler(final Class<T> type) {
    return new JsonBodyHandler<>(type);
  }

  private JsonBodyHandler(Class<T> type) {
    this.type = type;
  }

  @Override
  public HttpResponse.BodySubscriber<T> apply(
    final HttpResponse.ResponseInfo responseInfo) {
    return HttpResponse.BodySubscribers.mapping(HttpResponse.BodySubscribers.ofByteArray(),
      byteArray -> Json.decode(new ByteArrayInputStream(byteArray), this.type));
  }
}
