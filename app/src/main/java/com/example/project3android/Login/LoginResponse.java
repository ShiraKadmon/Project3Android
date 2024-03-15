package com.example.project3android.Login;

public class LoginResponse {

    private String token;
    private String id = "65f2d0907d31fdeb2611e3fe";

    public LoginResponse(String token, String id) {
        this.token = token;
        this.id = "65f2d0907d31fdeb2611e3fe";
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
