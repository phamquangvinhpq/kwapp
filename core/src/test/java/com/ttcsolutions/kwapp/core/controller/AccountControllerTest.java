package com.ttcsolutions.kwapp.core.controller;

import com.ttcsolutions.kwapp.commons.util.Constant;
import com.ttcsolutions.kwapp.commons.util.ErrorCode;
import com.ttcsolutions.kwapp.core.test.ApplicationTest;
import com.ttcsolutions.kwapp.core.test.WithMockKwAccount;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AccountControllerTest extends ApplicationTest {
    @Autowired
    private MockMvc mvc;

    @WithMockKwAccount
    @Sql(statements = {"truncate account restart identity",
            "INSERT INTO public.account (id, phone_number, password, avatar, created_at, updated_at, email) VALUES (1, '+84921232123', '$2a$10$Zv0smpYzN0GVOr5DnMW4bO8VtuIeiww6n0xXNrDf5/8ZAP1D1EZma', null, '2021-09-07 07:46:32.409268 +00:00', '2021-09-07 07:46:32.409268 +00:00', 'abc@gmail.com')"
    })
    @Test
    void changePassword_Success() throws Exception {
        var request = MockMvcRequestBuilders.put("/accounts/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"currentPassword\": \"1234567890\", \"newPassword\": \"0987654321\"}");
        mvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data", notNullValue()));
    }

    @WithMockKwAccount
    @Sql(statements = {"truncate account restart identity",
            "INSERT INTO public.account (id, phone_number, password, avatar, created_at, updated_at, email) VALUES (1, '+84921232123', '$2a$10$Zv0smpYzN0GVOr5DnMW4bO8VtuIeiww6n0xXNrDf5/8ZAP1D1EZma', null, '2021-09-07 07:46:32.409268 +00:00', '2021-09-07 07:46:32.409268 +00:00', 'abc@gmail.com')"
    })
    @Test
    void changePassword_WrongPass() throws Exception {
        var request = MockMvcRequestBuilders.put("/accounts/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"currentPassword\": \"123000315\", \"newPassword\": \"0987654321\"}");
        mvc.perform(request)
                .andExpect(status().is(400))
                .andExpect(jsonPath("$.meta.code", equalTo(Constant.PREFIX_RESPONSE_CODE + ErrorCode.WRONG_PASSWORD.getCode())));
    }
}
