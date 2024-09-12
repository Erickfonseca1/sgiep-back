package com.sgiep.sgiep_back.client.sgiepAuth;

import com.sgiep.sgiep_back.dto.LoginRequestDTO;
import com.sgiep.sgiep_back.dto.RegisterRequestDTO;
import com.sgiep.sgiep_back.dto.ResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "sgiepAuth", url = "http://localhost:8081")
public interface AuthFieldsClient {
    @PostMapping("/auth/login")
    ResponseDTO login(@RequestBody LoginRequestDTO request);

    @PostMapping("/auth/register")
    String register(@RequestBody RegisterRequestDTO request);
}
