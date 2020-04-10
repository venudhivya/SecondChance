package com.secondchance.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

public class ForgotPwdReenterActivity extends AppCompatActivity implements View.OnClickListener{
    SecondChanceApplication secondChanceApplication;
    StorageUtil mStore;
    EditText current_password;
    EditText new_password;
    Button done;
    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpwd_reenter_layout);
        secondChanceApplication = (SecondChanceApplication)getApplicationContext();
        mStore = StorageUtil.getInstance(getApplicationContext());        current_password = findViewById(R.id.current_password);
        new_password = findViewById(R.id.new_password);

done = findViewById(R.id.next_btn);
done.setOnClickListener(this);
    }
    private void callreenterpwdwithApi() {
//        loading.setVisibility(View.VISIBLE);
        if (secondChanceApplication.isNetworkAvailable()) {
            //creating the api interface
            RetrofitApi api = new RetrofitRestClient().urlInfoRetrofit(Configuration.BASE_URL);

            final resetpwdrequestodel resetpwdrequestodel = new resetpwdrequestodel(mStore.getString("emailid"),current_password.getText().toString(),new_password.getText().toString());
            Call<forgotpwdreponsemodel> call = api.getreenterpwdResponse(resetpwdrequestodel);

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
            if (!current_password.getText().toString().isEmpty() &&!new_password.getText().toString().isEmpty()) {
                 callreenterpwdwithApi();
            } else {
                current_password.setError("Please enter  password");
            }
        }
    }
}
