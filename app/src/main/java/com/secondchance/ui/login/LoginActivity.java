package com.secondchance.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.secondchance.MainActivity;
import com.secondchance.R;
import com.secondchance.SecondChanceApplication;
import com.secondchance.model.data;
import com.secondchance.model.loginreponsemodel;
import com.secondchance.model.loginrequestmodel;
import com.secondchance.model.registerrequestmodel;
import com.secondchance.model.registerresponsemodel;
import com.secondchance.service.RetrofitApi;
import com.secondchance.service.RetrofitRestClient;
import com.secondchance.ui.forgotpassword.ForgotPasswordActivity;
import com.secondchance.ui.register.RegisterAppActivity;
import com.secondchance.utils.Configuration;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button login_btn;
    TextView register_btn;
    TextView forgot_pwd_text;
    SecondChanceApplication secondChanceApplication;
    ProgressBar loading;
    EditText password;
    EditText emailid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        setContentView(R.layout.login_layout);
        secondChanceApplication = (SecondChanceApplication) getApplicationContext();
        loading = findViewById(R.id.loading);
        password = findViewById(R.id.password);
        emailid = findViewById(R.id.emailid);
        login_btn = findViewById(R.id.login_btn);
        register_btn = findViewById(R.id.reg_btn);
        forgot_pwd_text = findViewById(R.id.forgot_pwd_text);
        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        forgot_pwd_text.setOnClickListener(this);
    }

    private void callLoginApi() {
        loading.setVisibility(View.VISIBLE);
        if (secondChanceApplication.isNetworkAvailable()) {
            //creating the api interface
            RetrofitApi api = new RetrofitRestClient().urlInfoRetrofit(Configuration.BASE_URL);

            final loginrequestmodel loginrequestmodel = new loginrequestmodel(emailid.getText().toString(), password.getText().toString());
            Call<loginreponsemodel> call = api.getloginResponse(loginrequestmodel);

            call.enqueue(new Callback<loginreponsemodel>() {
                @Override
                public void onResponse(Call<loginreponsemodel> call, Response<loginreponsemodel> response) {
                    loading.setVisibility(View.GONE);

                    loginreponsemodel loginreponsemodel = response.body();

                    String success = loginreponsemodel.getSuccess();
                    data data = loginreponsemodel.getData();
                    String email = data.getEmail();
                    String password = data.getPassword();

                    if (success.equals("true")) {
                        Toast.makeText(getApplicationContext(), "LOGIN Successfull", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
//                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
//                        startActivity(intent);
                        finish();
                    } else if (success.equals("false")) {
                        Toast.makeText(getApplicationContext(), "User already exist", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "LOGIN Failed", Toast.LENGTH_SHORT).show();

                    }


                }

                @Override
                public void onFailure(Call<loginreponsemodel> call, Throwable t) {
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Login Failed", Toast.LENGTH_SHORT).show();


                }

            });

        } else {
            Toast.makeText(getApplicationContext(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();

        }

    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if (id == R.id.login_btn) {
            boolean isEmailValid = false;
            boolean isPasswordValid = false;

            if (emailid.getText().toString().isEmpty()) {
                emailid.setError(getResources().getString(R.string.email_error));
                isEmailValid = false;
            } else if (!Patterns.EMAIL_ADDRESS.matcher(emailid.getText().toString()).matches()) {
                emailid.setError(getResources().getString(R.string.error_invalid_email));
                isEmailValid = false;
            } else {
                isEmailValid = true;
            }


            // Check for a valid password.
            if (password.getText().toString().isEmpty()) {
                password.setError(getResources().getString(R.string.password_error));
                isPasswordValid = false;
            } else if (password.getText().length() < 6) {
                password.setError(getResources().getString(R.string.error_invalid_password));
                isPasswordValid = false;
            } else {
                isPasswordValid = true;
            }
            if (isEmailValid && isPasswordValid) {
                callLoginApi();
            }


        } else if (id == R.id.reg_btn) {
            Intent intent = new Intent(this, RegisterAppActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.forgot_pwd_text) {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
            finish();
        }

    }
}