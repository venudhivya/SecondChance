package com.secondchance.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.secondchance.MainActivity;
import com.secondchance.R;
import com.secondchance.SecondChanceApplication;
import com.secondchance.model.data;
import com.secondchance.model.loginreponsemodel;
import com.secondchance.model.loginrequestmodel;
import com.secondchance.service.RetrofitApi;
import com.secondchance.service.RetrofitRestClient;
import com.secondchance.ui.login.LoginActivity;
import com.secondchance.utils.Configuration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button next_btn;
    EditText emailid;
    ImageView img_forgot_pwd;
    LinearLayout linear_email;
SecondChanceApplication secondChanceApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pwd_layout);
secondChanceApplication = (SecondChanceApplication)getApplicationContext();
        next_btn = findViewById(R.id.next_btn);
        emailid = findViewById(R.id.emailid);
        img_forgot_pwd = findViewById(R.id.img_forgot_pwd);
        linear_email = findViewById(R.id.linear_email);
        next_btn.setOnClickListener(this);

    }
//    private void callLoginApi() {
////        loading.setVisibility(View.VISIBLE);
//        if (secondChanceApplication.isNetworkAvailable()) {
//            //creating the api interface
//            RetrofitApi api = new RetrofitRestClient().urlInfoRetrofit(Configuration.BASE_URL);
//
//            final loginrequestmodel loginrequestmodel = new loginrequestmodel(emailid.getText().toString());
//            Call<loginreponsemodel> call = api.getloginResponse(loginrequestmodel);
//
//            call.enqueue(new Callback<loginreponsemodel>() {
//                @Override
//                public void onResponse(Call<loginreponsemodel> call, Response<loginreponsemodel> response) {
////                    loading.setVisibility(View.GONE);
//
//                    loginreponsemodel loginreponsemodel = response.body();
//
//                    String success = loginreponsemodel.getSuccess();
//                    data data = loginreponsemodel.getData();
//                    String email = data.getEmail();
//                    String password = data.getPassword();
//
//                    if (success.equals("true")) {
//                        Toast.makeText(getApplicationContext(), "LOGIN Successfull", Toast.LENGTH_SHORT).show();
//
//                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else if (success.equals("false")) {
//                        Toast.makeText(getApplicationContext(), "User already exist", Toast.LENGTH_SHORT).show();
//
//                    } else {
//                        Toast.makeText(getApplicationContext(), "Failed to register", Toast.LENGTH_SHORT).show();
//
//                    }
//
//                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                    startActivity(intent);
//                    finish();
//
//                }
//
//                @Override
//                public void onFailure(Call<loginreponsemodel> call, Throwable t) {
////                    loading.setVisibility(View.GONE);
//                    Toast.makeText(getApplicationContext(), "Failed to register", Toast.LENGTH_SHORT).show();
//
//
//                }
//
//            });
//
//        } else {
//            Toast.makeText(getApplicationContext(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();
//
//        }
//
//    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.next_btn) {

            if (!emailid.getText().toString().isEmpty()) {
                Intent intent = new Intent(this, ForgotPwdOTPActivity.class);
                startActivity(intent);
            } else {
                emailid.setError("Please enter valid emailid");
            }

        }
    }
}
