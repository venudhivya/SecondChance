package com.secondchance.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.secondchance.MainActivity;
import com.secondchance.R;
import com.secondchance.ui.forgotpassword.ForgotPasswordActivity;
import com.secondchance.ui.register.RegisterAppActivity;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button login_btn;
    TextView register_btn;
    TextView forgot_pwd_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_layout);

        login_btn= findViewById(R.id.login_btn);
        register_btn=findViewById(R.id.reg_btn);
        forgot_pwd_text=findViewById(R.id.forgot_pwd_text);
        login_btn.setOnClickListener(this);
        register_btn.setOnClickListener(this);
        forgot_pwd_text.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        int id = v.getId();
        if(id == R.id.login_btn)
        {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
       else if(id == R.id.reg_btn)
        {
            Intent intent = new Intent(this, RegisterAppActivity.class);
            startActivity(intent);
            finish();
        }
        else if(id == R.id.forgot_pwd_text)
        {
            Intent intent = new Intent(this, ForgotPasswordActivity.class);
            startActivity(intent);
            finish();
        }

    }
}
