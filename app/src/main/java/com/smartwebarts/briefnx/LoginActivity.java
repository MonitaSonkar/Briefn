package com.smartwebarts.briefnx;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.smartwebarts.briefnx.dashboard.viewmodel.NewsViewModel;
import com.smartwebarts.briefnx.viewmodel.LoginViewModel;

public class LoginActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
    }
}