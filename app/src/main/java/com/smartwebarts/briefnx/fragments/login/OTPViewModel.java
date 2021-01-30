package com.smartwebarts.briefnx.fragments.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class OTPViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    // TODO: Implement the ViewModel

    private MutableLiveData<String> mUser;

    public OTPViewModel() {
        mUser = new MutableLiveData<>();
        mUser.setValue("OTP View Model");

    }

    LiveData<String> getmUser() {
        return mUser;
    }
}