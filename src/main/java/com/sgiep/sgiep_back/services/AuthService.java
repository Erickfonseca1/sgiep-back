package com.sgiep.sgiep_back.services;

import com.sgiep.sgiep_back.client.sgiepAuth.AuthFieldsClient;
import com.sgiep.sgiep_back.dto.LoginRequestDTO;
import com.sgiep.sgiep_back.dto.RegisterRequestDTO;
import com.sgiep.sgiep_back.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthFieldsClient authServiceFeignClient;

    public ResponseDTO login(String email, String password) {
        LoginRequestDTO request = new LoginRequestDTO(email, password);
        return authServiceFeignClient.login(request);
    }

    public String register(String name, String email, String password, String role, String phone, String address) {
        RegisterRequestDTO request = new RegisterRequestDTO(name, email, password, role, phone, address);
        return authServiceFeignClient.register(request);
    }
}
