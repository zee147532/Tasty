package com.tasty.app.service;

import com.tasty.app.request.InfoRequest;
import com.tasty.app.request.RegistryRequest;
import com.tasty.app.request.VerifyRequest;

public interface LoginService {
    String registry(RegistryRequest request);

    String fillInfo(InfoRequest request);

    String verify(VerifyRequest request);

    String sendCode(String email);
}
