package com.tasty.app.request;

public class RegistryRequest {
    private Long id;
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public Long getId() {
        return id;
    }

    public RegistryRequest(Long id, String username, String password) {
        this.id = id;
        this.username = username;
        this.password = password;
    }

    public RegistryRequest() {
    }
}
