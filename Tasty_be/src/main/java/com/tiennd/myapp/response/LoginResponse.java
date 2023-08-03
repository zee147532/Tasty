package com.tiennd.myapp.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String jwtToken;

    public LoginResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
