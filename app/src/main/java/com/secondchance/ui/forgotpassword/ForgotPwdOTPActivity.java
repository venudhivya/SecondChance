package com.secondchance.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.secondchance.R;

public class ForgotPwdOTPActivity extends AppCompatActivity implements View.OnClickListener {
    LinearLayout linear_verification;
    EditText editTexOne;
    EditText editTexTwo;
    EditText editTextThree;
    EditText editTexFour;
    Button next_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpwd_otp_layout);

        linear_verification = findViewById(R.id.linear_verification);
        editTexOne = findViewById(R.id.editTexOne);
        editTexTwo = findViewById(R.id.editTextwo);
        editTextThree = findViewById(R.id.editTextthree);
        editTexFour = findViewById(R.id.editTextfour);
        next_btn = findViewById(R.id.next_btn);

        next_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.next_btn) {
            if (!editTexOne.getText().toString().isEmpty() &&!editTexTwo.getText().toString().isEmpty()&& !editTextThree.getText().toString().isEmpty()&& !editTexFour.getText().toString().isEmpty()) {
                Toast.makeText(this, "Successful", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, ForgotPwdNewpwdActivity.class);
                startActivity(intent);
            } else {
                editTexOne.setError("Please enter valid OTP");
            }

        }


    }
}
