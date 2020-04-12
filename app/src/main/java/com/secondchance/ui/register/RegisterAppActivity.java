package com.secondchance.ui.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.secondchance.R;
import com.secondchance.SecondChanceApplication;
import com.secondchance.model.data;
import com.secondchance.model.registerrequestmodel;
import com.secondchance.model.registerresponsemodel;
import com.secondchance.service.RetrofitApi;
import com.secondchance.service.RetrofitRestClient;
import com.secondchance.ui.forgotpassword.ForgotPwdReenterActivity;
import com.secondchance.ui.login.LoginActivity;
import com.secondchance.utils.Configuration;
import com.secondchance.utils.StorageUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterAppActivity extends AppCompatActivity implements View.OnClickListener {
    SecondChanceApplication secondChanceApplication;
    Button register_btn;
    TextView signin_btn;
    EditText email;
    EditText password;
    EditText username_text;
    EditText phone_num;
    String emailidStr;
    String passwordStr;
    String phone_numStr;
    ProgressBar loading;
    StorageUtil mStore;
    ImageView facebook_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        setContentView(R.layout.register_layout);
        secondChanceApplication = (SecondChanceApplication) getApplicationContext();
        loading = findViewById(R.id.loading);
        mStore = StorageUtil.getInstance(getApplicationContext());
        register_btn = findViewById(R.id.register_btn);
        signin_btn = findViewById(R.id.signin_btn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone_num = findViewById(R.id.phone_num);
        username_text = findViewById(R.id.username_text);
        facebook_img = findViewById(R.id.facebook_img);
        register_btn.setOnClickListener(this);
        signin_btn.setOnClickListener(this);
        facebook_img.setOnClickListener(this);
        emailidStr = email.getText().toString();
        passwordStr = password.getText().toString();
        phone_numStr = phone_num.getText().toString();

    }

    private void callRegisterApi() {
        loading.setVisibility(View.VISIBLE);
        if (secondChanceApplication.isNetworkAvailable()) {
            //creating the api interface
            RetrofitApi api = new RetrofitRestClient().urlInfoRetrofit(Configuration.BASE_URL);

            final registerrequestmodel registerrequestmodel = new registerrequestmodel(email.getText().toString(), password.getText().toString(), phone_num.getText().toString());
            Call<registerresponsemodel> call = api.getregisterResponse(registerrequestmodel);

            call.enqueue(new Callback<registerresponsemodel>() {
                @Override
                public void onResponse(Call<registerresponsemodel> call, Response<registerresponsemodel> response) {
                    loading.setVisibility(View.GONE);

                    registerresponsemodel registerresponsemodel = response.body();
                    String success = registerresponsemodel.getSuccess();
                    data data = registerresponsemodel.getData();
                    String userid = data.getUserId();
                    mStore.setString("USERID", userid);
                    mStore.setString("USERNAME", username_text.getText().toString());
                    String message = data.getMessage();

                    if (success.equals("true")) {
                        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (success.equals("false")) {
                        Toast.makeText(getApplicationContext(), "User already exist", Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Failed to register", Toast.LENGTH_SHORT).show();

                    }


                }

                @Override
                public void onFailure(Call<registerresponsemodel> call, Throwable t) {
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Failed to register", Toast.LENGTH_SHORT).show();


                }

            });

        } else {
            Toast.makeText(getApplicationContext(), "Please Check your Internet Connection", Toast.LENGTH_SHORT).show();

        }

    }


    @Override
    public void onClick(View v) {

        int id = v.getId();

        if (id == R.id.register_btn) {

            SetValidation();


        } else if (id == R.id.signin_btn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else if (id == R.id.facebook_img) {
            Intent intent = new Intent(this, ForgotPwdReenterActivity.class);
            startActivity(intent);
            finish();
        }

    }

    private void SetValidation() {

        // Check for a valid email address.
        boolean isEmailValid = false;
        boolean isPasswordValid = false;
        boolean isPhoneValid = false;
        boolean isNameValid = false;

        if (email.getText().toString().isEmpty()) {
            email.setError(getResources().getString(R.string.email_error));
            isEmailValid = false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            email.setError(getResources().getString(R.string.error_invalid_email));
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


        // Check for a valid phone number.
        if (phone_num.getText().toString().isEmpty()) {
            phone_num.setError(getResources().getString(R.string.phone_error));
            isPhoneValid = false;
        } else if (phone_num.getText().length() < 10 || phone_num.getText().length() > 10) {
            phone_num.setError(getResources().getString(R.string.error_invalid_phone));
        } else {
            isPhoneValid = true;
        }


        // Check for a valid name.
        if (username_text.getText().toString().isEmpty()) {
            username_text.setError(getResources().getString(R.string.name_error));
            isNameValid = false;
        } else {
            isNameValid = true;
        }

        if (isNameValid && isEmailValid && isPhoneValid && isPasswordValid) {
            callRegisterApi();
//            Toast.makeText(getApplicationContext(), "Successfully", Toast.LENGTH_SHORT).show();
        } else {

        }


    }
}