package com.secondchance.model;

public class resetpwdwithOTPModel {

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConformpassword() {
        return conformpassword;
    }

    public void setConformpassword(String conformpassword) {
        this.conformpassword = conformpassword;
    }

    String password;
    String conformpassword;

    public resetpwdwithOTPModel(String email,String password,String conformpassword)
    {
        this.email = email;
        this.password = password;
        this.conformpassword = conformpassword;
    }
}
