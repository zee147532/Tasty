package com.tasty.app.request;

public class VerifyRequest {
    private String email;
    private String verifyCode;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVerifyCode() {
        return verifyCode;
    }

    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }

    public VerifyRequest(String email, String verifyCode) {
        this.email = email;
        this.verifyCode = verifyCode;
    }

    public VerifyRequest() {
    }
}
