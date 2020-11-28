package com.vinay.myfixeddeposits.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.vinay.myfixeddeposits.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_MyFixedDeposits);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}