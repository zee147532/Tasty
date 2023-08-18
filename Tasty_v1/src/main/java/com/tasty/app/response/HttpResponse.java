package com.tasty.app.response;

public class HttpResponse {
    private Integer statusCode;
    private String msg;

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public HttpResponse() {
    }

    public HttpResponse(Integer statusCode, String msg) {
        this.statusCode = statusCode;
        this.msg = msg;
    }
}
