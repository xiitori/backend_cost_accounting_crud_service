package ru.xiitori.crudservice.dto;

import jakarta.validation.constraints.Size;

public class LoginDTO {

    @Size(min = 4, max = 20, message = "Username may be 4-20 characters long")
    private String username;

    @Size(min = 6, max = 30, message = "Password may be 6-30 characters long")
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public LoginDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginDTO() {
    }
}
