package com.secondchance.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.secondchance.R;
import com.secondchance.SecondChanceApplication;
import com.secondchance.model.forgotpwdreponsemodel;
import com.secondchance.model.forgotpwdrequestmodel;
import com.secondchance.model.otpvalidaterequestmodel;
import com.secondchance.service.RetrofitApi;
import com.secondchance.service.RetrofitRestClient;
import com.secondchance.ui.login.LoginActivity;
import com.secondchance.utils.Configuration;
import com.secondchance.utils.StorageUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPwdOTPActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout linear_verification;
    EditText editTexOne;
    EditText editTexTwo;
    EditText editTextThree;
    EditText editTexFour;
    Button next_btn;
    SecondChanceApplication secondChanceApplication;
    StorageUtil mStore;
    ImageView back_img;
ProgressBar loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        setContentView(R.layout.forgotpwd_otp_layout);
        secondChanceApplication = (SecondChanceApplication)getApplicationContext();
        mStore = StorageUtil.getInstance(getApplicationContext());
        loading = findViewById(R.id.loading);
        linear_verification = findViewById(R.id.linear_verification);
        editTexOne = findViewById(R.id.editTexOne);
        editTexTwo = findViewById(R.id.editTextwo);
        editTextThree = findViewById(R.id.editTextthree);
        editTexFour = findViewById(R.id.editTextfour);
        next_btn = findViewById(R.id.next_btn);

        next_btn.setOnClickListener(this);
        back_img = findViewById(R.id.back_img);
        back_img.setOnClickListener(this);

    }

    private void callotpvalidateApi() {
        loading.setVisibility(View.VISIBLE);
        if (secondChanceApplication.isNetworkAvailable()) {
            //creating the api interface
            RetrofitApi api = new RetrofitRestClient().urlInfoRetrofit(Configuration.BASE_URL);
            String otp = editTexOne.getText().toString()+  editTexTwo.getText().toString()+ editTextThree.getText().toString()+editTexFour.getText().toString();

            final otpvalidaterequestmodel otpvalidaterequestmodel = new otpvalidaterequestmodel(mStore.getString("emailid"),otp);
            Call<forgotpwdreponsemodel> call = api.getotpvalidateResponse(otpvalidaterequestmodel);

            call.enqueue(new Callback<forgotpwdreponsemodel>() {
                @Override
                public void onResponse(Call<forgotpwdreponsemodel> call, Response<forgotpwdreponsemodel> response) {
                    loading.setVisibility(View.GONE);

                    forgotpwdreponsemodel forgotpwdreponsemodel = response.body();

                    String success = forgotpwdreponsemodel.getSuccess();
                    String data = forgotpwdreponsemodel.getData();


                    if (success.equals("true")) {
                        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();

                        Intent intent = new Intent(getApplicationContext(), ForgotPwdNewpwdActivity.class);
                        startActivity(intent);
                        finish();
                    } else if (success.equals("false")) {
                        Toast.makeText(getApplicationContext(), data, Toast.LENGTH_SHORT).show();

                    } else {
                        Toast.makeText(getApplicationContext(), "Failed! Enter valid OTP", Toast.LENGTH_SHORT).show();

                    }



                }

                @Override
                public void onFailure(Call<forgotpwdreponsemodel> call, Throwable t) {
                    loading.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Failed! Enter valid OTP", Toast.LENGTH_SHORT).show();


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
            if (!editTexOne.getText().toString().isEmpty() &&!editTexTwo.getText().toString().isEmpty()&& !editTextThree.getText().toString().isEmpty()&& !editTexFour.getText().toString().isEmpty()) {
                callotpvalidateApi();
            } else {
                editTexOne.setError("Please enter valid OTP");
            }

        }else if (id == R.id.back_img) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }


    }
}