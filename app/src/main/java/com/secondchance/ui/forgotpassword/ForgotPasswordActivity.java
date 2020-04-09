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

import com.secondchance.R;
import com.secondchance.ui.login.LoginActivity;

public class ForgotPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    Button next_btn;
    EditText emailid;
    LinearLayout linear_verification;
    ImageView img_forgot_pwd;
    LinearLayout linear_email;
    EditText editTexOne;
    EditText editTexTwo;
    EditText editTextThree;
    EditText editTexFour;
    LinearLayout linear_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_pwd_layout);

        next_btn = findViewById(R.id.next_btn);
        emailid = findViewById(R.id.emailid);
        linear_verification = findViewById(R.id.linear_verification);
        img_forgot_pwd = findViewById(R.id.img_forgot_pwd);
        linear_email = findViewById(R.id.linear_email);
        linear_pwd = findViewById(R.id.linear_pwd);
        next_btn.setOnClickListener(this);
        linear_verification.setVisibility(View.GONE);
        editTexOne = findViewById(R.id.editTexOne);
        editTexTwo = findViewById(R.id.editTextwo);
        editTextThree = findViewById(R.id.editTextthree);
        editTexFour = findViewById(R.id.editTextfour);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.next_btn) {
            if (emailid.getText().toString().isEmpty()) {
                emailid.setError("Please Enter Valid emailid");
            } else if (editTexOne.getText().toString().isEmpty() || editTexTwo.getText().toString().isEmpty()) {
                Toast.makeText(this, "Enter Valid OTP", Toast.LENGTH_SHORT).show();
            } else {

                if (!emailid.getText().toString().isEmpty()) {
                    linear_verification.setVisibility(View.VISIBLE);
                    linear_pwd.setVisibility(View.GONE);
                    img_forgot_pwd.setBackground(getResources().getDrawable(R.drawable.num_lock));
                    linear_email.setVisibility(View.GONE);
                } else if (!editTexTwo.getText().toString().isEmpty()) {
                    linear_verification.setVisibility(View.GONE);
                    linear_pwd.setVisibility(View.VISIBLE);
                    img_forgot_pwd.setBackground(getResources().getDrawable(R.drawable.pwd_lock));
                    linear_email.setVisibility(View.GONE);
                }

            }

//            Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
//            Intent intent = new Intent(this, LoginActivity.class);
//            startActivity(intent);
//            finish();
        }
    }
}
