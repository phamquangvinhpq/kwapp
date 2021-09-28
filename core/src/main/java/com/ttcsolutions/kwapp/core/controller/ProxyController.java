package com.ttcsolutions.kwapp.core.controller;

import com.ttcsolutions.kwapp.core.service.ProxyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/proxy/**")
@RequiredArgsConstructor
public class ProxyController {
    private final ProxyService proxyService;

    @RequestMapping
    public CompletableFuture<Void> forward(HttpServletRequest request, HttpServletResponse response) throws IOException {
        return proxyService.forward(request, response);
    }
}
