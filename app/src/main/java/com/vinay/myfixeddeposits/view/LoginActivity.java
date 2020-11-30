package com.vinay.myfixeddeposits.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.vinay.myfixeddeposits.R;
import com.vinay.myfixeddeposits.model.User;
import com.vinay.myfixeddeposits.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity implements TextWatcher {

    LoginViewModel loginViewModel;
    TextInputLayout userNameError,passwordError;
    TextInputEditText userName,password;
    MaterialButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_MyFixedDeposits);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userNameError = findViewById(R.id.userNameError);
        passwordError = findViewById(R.id.passwordError);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);
        userName.addTextChangedListener(this);
        password.addTextChangedListener(this);
        btnLogin = findViewById(R.id.btnLogin);
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        loginViewModel.errorMessageUserName.observe(this, errorMessageUserName -> {
            userNameError.setError(errorMessageUserName);
        });

        loginViewModel.errorMessagePassword.observe(this, errorMessagePassword -> {
            passwordError.setError(errorMessagePassword);
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginViewModel.validateUser(new User(userName.getText().toString().trim(),password.getText().toString().trim()));
            }
        });

        loginViewModel.getUser();
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        loginViewModel.resetErrorMessage();
    }

    @Override
    public void afterTextChanged(Editable s) {

    }
}