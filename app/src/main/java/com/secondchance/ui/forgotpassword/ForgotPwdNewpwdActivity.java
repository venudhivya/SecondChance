package com.secondchance.ui.forgotpassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.secondchance.MainActivity;
import com.secondchance.R;

public class ForgotPwdNewpwdActivity extends AppCompatActivity implements View.OnClickListener{
    LinearLayout linear_pwd;
    Button next_btn;
    TextInputEditText password;
    TextInputEditText re_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgotpwd_new_layout);
        password = findViewById(R.id.password);
        re_password = findViewById(R.id.re_password);
        linear_pwd = findViewById(R.id.linear_pwd);
        next_btn = findViewById(R.id.next_btn);

        next_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.next_btn) {
            if (!password.getText().toString().isEmpty() &&!re_password.getText().toString().isEmpty()) {
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                password.setError("Please enter  password");
            }
        }

    }
}
