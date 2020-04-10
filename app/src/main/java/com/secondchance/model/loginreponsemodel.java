package com.secondchance.model;

public class loginreponsemodel {

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    String success;
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    String userId;
    String message;


    public com.secondchance.model.data getData() {
        return data;
    }

    data data;


}
