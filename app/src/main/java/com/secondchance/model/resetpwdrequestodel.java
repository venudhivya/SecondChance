package com.secondchance.model;

public class resetpwdrequestodel {

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    String email;


    public String getCurrentpassword() {
        return currentpassword;
    }

    public void setCurrentpassword(String currentpassword) {
        this.currentpassword = currentpassword;
    }

    public String getNewpassword() {
        return newpassword;
    }

    public void setNewpassword(String newpassword) {
        this.newpassword = newpassword;
    }

    String currentpassword;
    String newpassword;

    public resetpwdrequestodel(String email,String currentpassword,String newpassword)
    {
        this.email = email;
        this.currentpassword = currentpassword;
        this.newpassword = newpassword;

    }
}
