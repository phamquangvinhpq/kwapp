package com.ttcsolutions.kwapp.core.controller;

import com.ttcsolutions.kwapp.commons.model.response.Response;
import com.ttcsolutions.kwapp.commons.util.Authentications;
import com.ttcsolutions.kwapp.commons.util.Pageables;
import com.ttcsolutions.kwapp.core.model.filter.AccountDeviceFilter;
import com.ttcsolutions.kwapp.core.model.request.CreateAccountDeviceRequest;
import com.ttcsolutions.kwapp.core.model.response.AccountDeviceInfoResponse;
import com.ttcsolutions.kwapp.core.model.response.AccountDeviceResponse;
import com.ttcsolutions.kwapp.core.service.AccountDeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * CRUD api for Connect Device manager
 *
 * @author mahai
 */
@RestController
@RequestMapping("/account-devices")
@Validated
@RequiredArgsConstructor
public class AccountDeviceController {
    private final AccountDeviceService accountDeviceService;

    /**
     * API to connect a account with a smart watch
     *
     * @param createAccountDeviceRequest
     * @return
     */
    @PostMapping
    public Response<AccountDeviceResponse> create(@Valid @RequestBody CreateAccountDeviceRequest createAccountDeviceRequest) {
        var kwAccount = Authentications.requireKwAccount();
        return Response.ofSucceeded(accountDeviceService.create(kwAccount, createAccountDeviceRequest));
    }

    @GetMapping
    public Response<List<AccountDeviceInfoResponse>> getAccountDeviceInfos(@RequestParam int page, @RequestParam int size,
                                                                           @RequestParam(required = false) String sort,
                                                                           AccountDeviceFilter filter) {
        var res = accountDeviceService.getAccountDeviceInfos(filter, Pageables.of(page, size, sort));
        return Response.ofSucceeded(res);
    }
}
