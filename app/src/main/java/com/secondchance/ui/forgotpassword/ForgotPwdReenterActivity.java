package com.secondchance.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.secondchance.R;
import com.secondchance.SecondChanceApplication;
import com.secondchance.model.forgotpwdreponsemodel;
import com.secondchance.model.resetpwdrequestodel;
import com.secondchance.model.resetpwdwithOTPModel;
import com.secondchance.service.RetrofitApi;
import com.secondchance.service.RetrofitRestClient;
import com.secondchance.ui.login.LoginActivity;
import com.secondchance.utils.Configuration;
import com.secondchance.utils.StorageUtil;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPwdReenterActivity extends AppCompatActivity implements View.OnClickListener {
    SecondChanceApplication secondChanceApplication;
    StorageUtil mStore;
    EditText current_password;
    EditText new_password;
    EditText confirm_password;
    Button done;
    ProgressBar loading;
    ImageView back_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.fadeout, R.anim.fadein);
        setContentView(R.layout.forgotpwd_reenter_layout);
        secondChanceApplication = (SecondChanceApplication) getApplicationContext();
        mStore = StorageUtil.getInstance(getApplicationContext());
        current_password = findViewById(R.id.current_password);
        confirm_password = findViewById(R.id.confirm_password);
        new_password = findViewById(R.id.new_password);
        done = findViewById(R.id.next_btn);
        done.setOnClickListener(this);
        loading = findViewById(R.id.loading);
        back_img = findViewById(R.id.back_img);
        back_img.setOnClickListener(this);
    }

    private void callreenterpwdwithApi() {
        loading.setVisibility(View.VISIBLE);
        if (secondChanceApplication.isNetworkAvailable()) {
            //creating the api interface
            RetrofitApi api = new RetrofitRestClient().urlInfoRetrofit(Configuration.BASE_URL);

            final resetpwdrequestodel resetpwdrequestodel = new resetpwdrequestodel(mStore.getString("emailid"), current_password.getText().toString(), new_password.getText().toString());
            Call<forgotpwdreponsemodel> call = api.getreenterpwdResponse(resetpwdrequestodel);

            call.enqueue(new Callback<forgotpwdreponsemodel>() {
                @Override
                public void onResponse(Call<forgotpwdreponsemodel> call, Response<forgotpwdreponsemodel> response) {
                    loading.setVisibility(View.GONE);

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
                    loading.setVisibility(View.GONE);
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

            boolean iscurrentpass = false;
            boolean isnewpass = false;
            boolean isconfirmpass = false;
            // Check for a valid password.
            if (current_password.getText().toString().isEmpty()) {
                current_password.setError(getResources().getString(R.string.password_error));
                iscurrentpass = false;
            } else if (current_password.getText().length() < 6) {
                current_password.setError(getResources().getString(R.string.error_invalid_password));
                iscurrentpass = false;
            } else {
                iscurrentpass = true;
            }

            if (new_password.getText().toString().isEmpty()) {
                new_password.setError(getResources().getString(R.string.password_error));
                isnewpass = false;
            } else if (new_password.getText().length() < 6) {
                new_password.setError(getResources().getString(R.string.error_invalid_password));
                isnewpass = false;
            } else {
                isnewpass = true;
            }

            if (confirm_password.getText().toString().isEmpty()) {
                confirm_password.setError(getResources().getString(R.string.password_error));
                isconfirmpass = false;
            } else if (confirm_password.getText().length() < 6) {
                confirm_password.setError(getResources().getString(R.string.error_invalid_password));
                isconfirmpass = false;
            } else {
                isconfirmpass = true;
            }


            if (isconfirmpass && isnewpass && isconfirmpass) {
                if (new_password.getText().toString().equals(confirm_password.getText().toString())) {
                    if (!new_password.getText().toString().equals(current_password.getText().toString())) {
                        callreenterpwdwithApi();
                    } else {
                        Toast.makeText(getApplicationContext(), "Current password and New password shold be different!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "New password and conformed password shoud be same!", Toast.LENGTH_SHORT).show();
                }


            }


        }else if (id == R.id.back_img) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }
}