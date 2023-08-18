package com.tasty.app.service;

import com.tasty.app.request.InfoRequest;
import com.tasty.app.request.RegistryRequest;
import com.tasty.app.request.VerifyRequest;
import com.tasty.app.response.HttpResponse;
import com.tasty.app.response.InfoResponse;
import com.tasty.app.response.RegistryResponse;

public interface LoginService {
    RegistryResponse registry(RegistryRequest request);

    InfoResponse fillInfo(InfoRequest request);

    HttpResponse verify(VerifyRequest request);

    String sendCode(String email);
}
