package com.smartwebarts.briefnx.fragments.login;

import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.smartwebarts.briefnx.R;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class SignInFragment extends Fragment implements View.OnClickListener {

    private SignInViewModel mViewModel;
    TextView forgetPassword, register;
    ImageView iv_register;
    CircularProgressButton login;
    TextInputLayout tilMobile, tilPassword;
    TextInputEditText tiMobile, tiPassword;

    public static SignInFragment newInstance() {
        return new SignInFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sign_in_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(SignInViewModel.class);
        mViewModel.setActivity(requireActivity());
        init(view);
        addListeners();
        return view;

    }

    private void addListeners() {
        forgetPassword.setOnClickListener(this);
        register.setOnClickListener(this);
        iv_register.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    private void init(View view) {
        forgetPassword = view.findViewById(R.id.forgetPassword);
        register = view.findViewById(R.id.register);
        iv_register = view.findViewById(R.id.iv_register);
        login = view.findViewById(R.id.cirLoginButton);
        tilMobile = view.findViewById(R.id.textInputEmail);
        tilPassword = view.findViewById(R.id.textInputPassword);
        tiMobile = view.findViewById(R.id.ed_mobile);
        tiPassword = view.findViewById(R.id.ed_password);
    }

    @Override
    public void onClick(View v) {

        if (v == register || v == iv_register) {
            mViewModel.moveToRegister(v);
        }

        if (v == login && validateform()) {
            mViewModel.setMobileNumber(tiMobile.getText().toString());
            mViewModel.setPassword(tiPassword.getText().toString());
            mViewModel.login(v);
        }

        if (v == forgetPassword) {
            mViewModel.forgetPassword(v);
        }
    }

    private boolean validateform() {

        if(tiMobile.getText().toString().isEmpty() || tiMobile.getText().toString().length()<10){
            tiMobile.setError("Enter 10 digit Mobile Number");
            return false;
        }

        if(tiPassword.getText().toString().equals("")){
            tiPassword.setError("Enter Your Password");
            return false;
        }

        return true;
    }
}