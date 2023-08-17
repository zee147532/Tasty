package com.tasty.app.request;

import com.tasty.app.domain.enumeration.Gender;

public class InfoRequest {
    private String username;
    private String fullName;
    private String phoneNumber;
    private String email;
    private Gender gender;
    private Long profession;

    public String getUsername() {
        return username;
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

    public Long getProfession() {
        return profession;
    }

    public void setProfession(Long profession) {
        this.profession = profession;
    }

    public InfoRequest(String username, String fullName, String phoneNumber, String email, Gender gender, Long profession) {
        this.username = username;
        this.fullName = fullName;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.gender = gender;
        this.profession = profession;
    }

    public InfoRequest() {
    }
}
