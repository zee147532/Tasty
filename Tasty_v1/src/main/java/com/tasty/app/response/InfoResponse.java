package com.tasty.app.response;

public class InfoResponse {
    private String email;
    private String errorMsg;
    private Integer statusCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public InfoResponse() {
    }

    public InfoResponse(String email, String errorMsg, Integer statusCode) {
        this.email = email;
        this.errorMsg = errorMsg;
        this.statusCode = statusCode;
    }
}
