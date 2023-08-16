package com.tasty.app.response;

public class RegistryResponse {
    private String username;
    private String errorMsg;
    private Integer statusCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public RegistryResponse() {
    }

    public RegistryResponse(String username, String errorMsg, Integer statusCode) {
        this.username = username;
        this.errorMsg = errorMsg;
        this.statusCode = statusCode;
    }
}
