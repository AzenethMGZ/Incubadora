package com.example.incubadora.modelos;

public class login {
    private String Message;
    private String token;

    public login(String message, String token) {
        this.Message = message;
        this.token = token;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
