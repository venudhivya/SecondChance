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
import com.secondchance.model.forgotpwdreponsemodel;
import com.secondchance.model.forgotpwdrequestmodel;
import com.secondchance.model.loginreponsemodel;
import com.secondchance.model.loginrequestmodel;
import com.secondchance.service.RetrofitApi;
import com.secondchance.service.RetrofitRestClient;
import com.secondchance.ui.login.LoginActivity;
import com.secondchance.utils.Configuration;
import com.secondchance.utils.StorageUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button next_btn;
    EditText emailid;
    ImageView img_forgot_pwd;
    LinearLayout linear_email;
SecondChanceApplication secondChanceApplication;
    StorageUtil mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pwd_layout);
secondChanceApplication = (SecondChanceApplication)getApplicationContext();
        mStore = StorageUtil.getInstance(getApplicationContext());

        next_btn = findViewById(R.id.next_btn);
        emailid = findViewById(R.id.emailid);
        img_forgot_pwd = findViewById(R.id.img_forgot_pwd);
        linear_email = findViewById(R.id.linear_email);
        next_btn.setOnClickListener(this);

    }
    private void callForgotPwdApi() {
//        loading.setVisibility(View.VISIBLE);
        if (secondChanceApplication.isNetworkAvailable()) {
            //creating the api interface
            RetrofitApi api = new RetrofitRestClient().urlInfoRetrofit(Configuration.BASE_URL);

            final forgotpwdrequestmodel forgotpwdrequestmodel = new forgotpwdrequestmodel(emailid.getText().toString());
            Call<forgotpwdreponsemodel> call = api.getforgotpwdResponse(forgotpwdrequestmodel);

            call.enqueue(new Callback<forgotpwdreponsemodel>() {
                @Override
                public void onResponse(Call<forgotpwdreponsemodel> call, Response<forgotpwdreponsemodel> response) {
//                    loading.setVisibility(View.GONE);

                    forgotpwdreponsemodel forgotpwdreponsemodel = response.body();

                    String success = forgotpwdreponsemodel.getSuccess();
                    String data = forgotpwdreponsemodel.getData();
mStore.setString("emailid",emailid.getText().toString());

                    if (success.equals("true")) {
                        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), ForgotPwdOTPActivity.class);
                        startActivity(intent);
                    } else if (success.equals("false")) {
                        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to sent email", Toast.LENGTH_SHORT).show();

                    }



                }

                @Override
                public void onFailure(Call<forgotpwdreponsemodel> call, Throwable t) {
//                    loading.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Failed to sent email", Toast.LENGTH_SHORT).show();


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

            if (!emailid.getText().toString().isEmpty()) {
                callForgotPwdApi();
            } else {
                emailid.setError("Please enter valid emailid");
            }

        }
    }
}
