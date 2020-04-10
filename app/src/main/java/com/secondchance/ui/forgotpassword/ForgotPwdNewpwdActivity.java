package com.secondchance.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.secondchance.MainActivity;
import com.secondchance.R;
import com.secondchance.SecondChanceApplication;
import com.secondchance.model.forgotpwdreponsemodel;
import com.secondchance.model.otpvalidaterequestmodel;
import com.secondchance.model.resetpwdwithOTPModel;
import com.secondchance.service.RetrofitApi;
import com.secondchance.service.RetrofitRestClient;
import com.secondchance.ui.login.LoginActivity;
import com.secondchance.utils.Configuration;
import com.secondchance.utils.StorageUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPwdNewpwdActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout linear_pwd;
    Button next_btn;
    TextInputEditText password;
    TextInputEditText re_password;
    SecondChanceApplication secondChanceApplication;
    StorageUtil mStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        secondChanceApplication = (SecondChanceApplication)getApplicationContext();
        mStore = StorageUtil.getInstance(getApplicationContext());
        setContentView(R.layout.forgotpwd_new_layout);
        password = findViewById(R.id.password);
        re_password = findViewById(R.id.re_password);
        linear_pwd = findViewById(R.id.linear_pwd);
        next_btn = findViewById(R.id.next_btn);

        next_btn.setOnClickListener(this);

    }
    private void callpwdwithOTPApi() {
//        loading.setVisibility(View.VISIBLE);
        if (secondChanceApplication.isNetworkAvailable()) {
            //creating the api interface
            RetrofitApi api = new RetrofitRestClient().urlInfoRetrofit(Configuration.BASE_URL);

            final resetpwdwithOTPModel otpvalidaterequestmodel = new resetpwdwithOTPModel(mStore.getString("emailid"),password.getText().toString(),re_password.getText().toString());
            Call<forgotpwdreponsemodel> call = api.getresetpwdwithOTPResponse(otpvalidaterequestmodel);

            call.enqueue(new Callback<forgotpwdreponsemodel>() {
                @Override
                public void onResponse(Call<forgotpwdreponsemodel> call, Response<forgotpwdreponsemodel> response) {
//                    loading.setVisibility(View.GONE);

                    forgotpwdreponsemodel forgotpwdreponsemodel = response.body();

                    String success = forgotpwdreponsemodel.getSuccess();
                    String data = forgotpwdreponsemodel.getData();


                    if (success.equals("true")) {
                        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (success.equals("false")) {
                        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to validate OTP", Toast.LENGTH_SHORT).show();

                    }



                }

                @Override
                public void onFailure(Call<forgotpwdreponsemodel> call, Throwable t) {
//                    loading.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Failed to validate OTP", Toast.LENGTH_SHORT).show();


                }

            });

        } else {
            Toast.makeText(getApplicationContext(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();

        }

    }
    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.next_btn) {
            if (!password.getText().toString().isEmpty() &&!re_password.getText().toString().isEmpty()) {
                callpwdwithOTPApi();
            } else {
                password.setError("Please enter  password");
            }
        }

    }
}
