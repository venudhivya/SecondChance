package com.secondchance.ui.register;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    EditText phone_num;
    String emailidStr;
    String passwordStr;
    String phone_numStr;
    ProgressBar loading;
StorageUtil mStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        secondChanceApplication = (SecondChanceApplication) getApplicationContext();
        loading = findViewById(R.id.loading);
        mStore = StorageUtil.getInstance(getApplicationContext());
        register_btn = findViewById(R.id.register_btn);
        signin_btn = findViewById(R.id.signin_btn);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone_num = findViewById(R.id.phone_num);
        register_btn.setOnClickListener(this);
        signin_btn.setOnClickListener(this);

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
                    mStore.setString("USERID",userid);
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


            callRegisterApi();
        } else if (id == R.id.signin_btn) {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
