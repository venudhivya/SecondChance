package com.secondchance.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.secondchance.R;
import com.secondchance.SecondChanceApplication;
import com.secondchance.model.registerresponsemodel;
import com.secondchance.service.RetrofitApi;
import com.secondchance.service.RetrofitRestClient;
import com.secondchance.ui.login.LoginActivity;
import com.secondchance.utils.Configuration;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_layout);
        secondChanceApplication = (SecondChanceApplication) getApplicationContext();

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
        if (secondChanceApplication.isNetworkAvailable()) {
            //creating the api interface
            RetrofitApi api = new RetrofitRestClient().urlInfoRetrofit(Configuration.BASE_URL);

            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("email", email.getText().toString());
                jsonObject.put("password", password.getText().toString());
                jsonObject.put("phone_number", phone_num.getText().toString());
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Call<registerresponsemodel> call = api.getregisterResponse(jsonObject);

            call.enqueue(new Callback<registerresponsemodel>() {
                @Override
                public void onResponse(Call<registerresponsemodel> call, Response<registerresponsemodel> response) {
                    registerresponsemodel registerresponsemodel = response.body();
                    Toast.makeText(getApplicationContext(), "Successful", Toast.LENGTH_SHORT).show();

//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish();
                }

                @Override
                public void onFailure(Call<registerresponsemodel> call, Throwable t) {

                }

            });

        } else {

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
