package com.secondchance;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.secondchance.utils.StorageUtil;

public class MainActivity extends AppCompatActivity {
    TextView username;
    StorageUtil mStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mStore = StorageUtil.getInstance(getApplicationContext());

        username = findViewById(R.id.username);
        if (mStore.getString("USERNAME") != null) {
            username.setText("Welcome " + mStore.getString("USERNAME") + " !");

        }
    }
}
