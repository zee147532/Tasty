package com.tasty.app.request;

import com.tasty.app.domain.enumeration.Gender;

public class RegistryRequest {
    private String username;
    private String password;
    private String fullName;
    private String phoneNumber;
    private String email;
    private Gender gender;
    private Long professionId;

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFullName() {
        return fullName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public Gender getGender() {
        return gender;
    }

    public Long getProfessionId() {
        return professionId;
    }

    public void setProfessionId(Long professionId) {
        this.professionId = professionId;
    }

    public RegistryRequest(String username, String password, String fullName, String phoneNumber, String email, Gender gender, Long professionId) {
        this.username = username;
        this.password = password;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.professionId = professionId;
    }

    public RegistryRequest() {
    }
}
