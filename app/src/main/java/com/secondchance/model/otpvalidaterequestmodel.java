package com.secondchance.model;

public class otpvalidaterequestmodel {

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    String otp;
    String email;

    public otpvalidaterequestmodel(String email,String otp)
    {
        this.email = email;
        this.otp = otp;
    }

}
