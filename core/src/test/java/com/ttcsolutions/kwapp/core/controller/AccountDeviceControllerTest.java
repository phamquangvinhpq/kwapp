package com.ttcsolutions.kwapp.core.controller;

import com.ttcsolutions.kwapp.core.test.ApplicationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountDeviceControllerTest extends ApplicationTest {
    @Autowired
    private MockMvc mvc;

    @Sql(statements = {"truncate account restart identity", "truncate account_device restart identity",
            "INSERT INTO public.account (id, phone_number, password, avatar, created_at, updated_at, email) VALUES (1, '+84921232123', '$2a$10$Zv0smpYzN0GVOr5DnMW4bO8VtuIeiww6n0xXNrDf5/8ZAP1D1EZma', null, '2021-09-07 07:46:32.409268 +00:00', '2021-09-07 07:46:32.409268 +00:00', 'abc@gmail.com')",
            "INSERT INTO public.account_device (id, created_at, updated_at, account_id, admin, device_code, device_id, device_name, icon, relationship, status) VALUES (15, '2021-09-08 12:11:35.175825', '2021-09-08 12:11:35.175825', 1, true, 'dc1', 1, 'con gai', 'icon', 'bo', 0)",
            "INSERT INTO public.account_device (id, created_at, updated_at, account_id, admin, device_code, device_id, device_name, icon, relationship, status) VALUES (14, '2021-09-08 12:06:12.262964', '2021-09-08 12:06:12.262964', 1, true, 'dc2', 2, 'con trai', 'icon', 'bo', 0)"
    })
    @Test
    void getAccountDeviceInfos() throws Exception {
        var request = MockMvcRequestBuilders.get("/account-devices")
                .param("deviceId", "1")
                .param("page", "0")
                .param("size", "20");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data[0].deviceId", equalTo(1)))
                .andExpect(jsonPath("$.data", hasSize(1)))
                .andExpect(jsonPath("$.meta.size", equalTo(20)));

        request = MockMvcRequestBuilders.get("/account-devices")
                .param("page", "0")
                .param("size", "20");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", hasSize(2)))
                .andExpect(jsonPath("$.meta.total", equalTo(2)));
    }
}
