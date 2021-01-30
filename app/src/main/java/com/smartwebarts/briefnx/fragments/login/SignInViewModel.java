package com.smartwebarts.briefnx.fragments.login;

import android.app.Activity;
import android.app.Application;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.navigation.Navigation;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.dashboard.DashboardActivity;
import com.smartwebarts.briefnx.models.OTPModel;
import com.smartwebarts.briefnx.models.StateModel;
import com.smartwebarts.briefnx.newsdetail.model.Articles_Model;
import com.smartwebarts.briefnx.newsdetail.model.SubscriptionModel;
import com.smartwebarts.briefnx.retrofit.UtilMethods;
import com.smartwebarts.briefnx.retrofit.mCallBackResponse;
import com.smartwebarts.briefnx.utils.AppSharedPreferences;
import com.smartwebarts.briefnx.utils.MyClick;
import com.smartwebarts.briefnx.utils.Urls;
import com.smartwebarts.briefnx.utils.UsefullMethods;
import com.smartwebarts.briefnx.viewmodel.Repository;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import retrofit2.http.Url;

public class SignInViewModel extends AppSharedPreferences {
    // TODO: Implement the ViewModel

    public MutableLiveData<String> mobileNumber;
    public MutableLiveData<String> password;
    public MutableLiveData<String> firstName;
    public MutableLiveData<String> lastName;
    public MutableLiveData<String> ShopName;
    public MutableLiveData<String> gstin;
    public MutableLiveData<String> location;
    public MutableLiveData<String> pincode;
    public MutableLiveData<String> state_id;
    public MutableLiveData<String> city_id;



    private Activity activity;
    private Repository repository;
    private LiveData<List<StateModel>> mutableLiveDataState;
    private LiveData<List<StateModel>> mutableLiveDatapackagesCity;
    public SignInViewModel(Application application) {
        super(application);
        mobileNumber = new MutableLiveData<>();
        password = new MutableLiveData<>();
        firstName = new MutableLiveData<>();
        lastName = new MutableLiveData<>();
        ShopName = new MutableLiveData<>();
        gstin = new MutableLiveData<>();
        location = new MutableLiveData<>();
        pincode = new MutableLiveData<>();
        state_id = new MutableLiveData<>();
        city_id = new MutableLiveData<>();
    }
    public void init() {
        repository = new Repository();
        mutableLiveDataState = repository.getState();
        mutableLiveDatapackagesCity = repository.getCity();
    }

    public MutableLiveData<String> getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber.setValue(mobileNumber);
    }

    public MutableLiveData<String> getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id.setValue(state_id);
    }

    public MutableLiveData<String> getCity_id() {
        return city_id;
    }

    public void setCity_id(String city_id) {
        this.city_id.setValue(city_id);
    }

    public MutableLiveData<String> getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password.setValue(password);
    }

    public MutableLiveData<String> getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName.setValue(firstName);
    }

    public MutableLiveData<String> getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName.setValue(lastName);
    }

    public MutableLiveData<String> getShopName() {
        return ShopName;
    }

    public void setShopName(String shopName) {
        ShopName.setValue(shopName);
    }

    public MutableLiveData<String> getGstin() {
        return gstin;
    }

    public void setGstin(String gstin) {
        this.gstin.setValue(gstin);
    }

    public MutableLiveData<String> getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location.setValue(location);
    }

    public MutableLiveData<String> getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode.setValue(pincode);
    }

    public void login(View view) {
//        Bundle bundle = new Bundle();
//        bundle.putSerializable("data", getMobileNumber().getValue());
//        Navigation.findNavController(view).navigate(R.id.action_signInFragment_to_OTPFragment, bundle);
        UtilMethods.INSTANCE.Login(getActivity()
                , mobileNumber.getValue(), password.getValue(), new mCallBackResponse() {
                    @Override
                    public void success(String from, String message) {
                        setLoginDetails(message);

                        AppSharedPreferences preferences = new AppSharedPreferences(getApplication());
                        if (preferences.getLoginRole().equalsIgnoreCase("user")) {
                            Urls.isFirst=true;
                            UsefullMethods.startActivityWithFinishAffinity(getActivity(), DashboardActivity.class);
                        }else {
                            Toast.makeText(getContext(), "You're not a User...", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void fail(String from) {
                        UsefullMethods.showMessage(getActivity(), SweetAlertDialog.ERROR_TYPE, "Error in Login", from, "OK", null);
                    }
                });
    }


    public void moveToRegister(View v) {
        Navigation.findNavController(v).navigate(R.id.action_signInFragment_to_signUpFragment);
    }

    public void forgetPassword(View v) {
        OpenDialogFwd(v);
    }


    public void OpenDialogFwd(View v1) {

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.forgotpass, null);

        final TextInputEditText edMobileFwp = view.findViewById(R.id.ed_mobile_fwp);
        final TextInputLayout tilMobileFwp=(TextInputLayout)view.findViewById(R.id.til_mobile_fwp);
        final Button FwdokButton = (Button) view.findViewById(R.id.okButton);
        Button cancelButton = (Button) view.findViewById(R.id.cancelButton);

        final Dialog dialog = new Dialog(getActivity());

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        edMobileFwp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!validateMobileFwp(edMobileFwp, tilMobileFwp, FwdokButton)) {
                    return;
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        FwdokButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String number = edMobileFwp.getText().toString();
                if (!validateMobileFwp(edMobileFwp, tilMobileFwp, FwdokButton)) {
                    return;
                }

                if (UtilMethods.INSTANCE.isNetworkAvialable(getContext())) {

                    UtilMethods.INSTANCE.otp(getActivity(), edMobileFwp.getText().toString().trim(), "1",new mCallBackResponse() {
                        @Override
                        public void success(String from, String message) {
                            OTPModel otpModel = new Gson().fromJson(message, OTPModel.class);
                            Intent intent = new Intent(getContext(), ChangePasswordFragment.class);
                            intent.putExtra(ChangePasswordFragment.OTP, otpModel);
                            intent.putExtra(ChangePasswordFragment.NUMBER, number);
                            getActivity().startActivity(intent);
                        }

                        @Override
                        public void fail(String from) {
                            UsefullMethods.showMessage(getActivity(), SweetAlertDialog.ERROR_TYPE, "Error", from, "OK", () -> {

                            });
                        }
                    });

                } else {
                    UtilMethods.INSTANCE.internetNotAvailableMessage(getContext());
                }

                dialog.dismiss();
            }
        });

        dialog.show();
    }

    public boolean validateMobileFwp(EditText edMobileFwp, TextInputLayout tilMobileFwp, Button FwdokButton){
        if (edMobileFwp.getText().toString().trim().isEmpty()) {
            tilMobileFwp.setError(getContext().getString(R.string.err_msg_mobile));
            requestFocus(edMobileFwp);
            return false;
        }
        else if (!(edMobileFwp.getText().toString().trim().length()==10)){
            tilMobileFwp.setError(getContext().getString(R.string.err_msg_mobile_length));
            requestFocus(edMobileFwp);
            return false;
        }else {
            tilMobileFwp.setErrorEnabled(false);
            FwdokButton.setEnabled(true);
        }

        return true;
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public void register(View v) {
        String name=firstName.getValue();
        String fathername=lastName.getValue();
        String mobile=mobileNumber.getValue();
        String email=location.getValue();
        String address=pincode.getValue();
        String mpassword=password.getValue();
        String stateid=state_id.getValue();
        String cityid=city_id.getValue();
        UtilMethods.INSTANCE.signup(getActivity(),name,fathername,mobile,email,address,mpassword,stateid,cityid, new mCallBackResponse() {
                    @Override
                    public void success(String from, String message) {

                        UsefullMethods.showMessage(getActivity(), SweetAlertDialog.SUCCESS_TYPE, "Registration Done", "Login to Continue", "Login", new MyClick() {

                            @Override
                            public void onClick() {
                                getActivity().onBackPressed();
                            }
                        });
                    }

                    @Override
                    public void fail(String from) {
                        UsefullMethods.showMessage(getActivity(), SweetAlertDialog.ERROR_TYPE, "Registration Failed", from, "OK", null);
                    }
                }
        );
    }

    public void openVerifyOTP(View v, String otp) {

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.verify_otp, null);

        final TextInputEditText edMobileFwp = view.findViewById(R.id.ed_mobile_fwp);
        final TextInputLayout tilMobileFwp=(TextInputLayout)view.findViewById(R.id.til_mobile_fwp);
        final Button FwdokButton = (Button) view.findViewById(R.id.okButton);
        Button cancelButton = (Button) view.findViewById(R.id.cancelButton);

        final Dialog dialog = new Dialog(getActivity());

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        edMobileFwp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                if (!validateOtpFwp(edMobileFwp, tilMobileFwp, FwdokButton, otp)) {
                    return;
                }
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        FwdokButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String number = getMobileNumber().getValue().trim();
                if (!validateOtpFwp(edMobileFwp, tilMobileFwp, FwdokButton, otp)) {
                    return;
                }
                register(v);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    private boolean validateOtpFwp(EditText edMobileFwp, TextInputLayout tilMobileFwp, Button FwdokButton, String otp){
        if (edMobileFwp.getText().toString().trim().isEmpty()) {
            tilMobileFwp.setError(getContext().getString(R.string.err_msg_mobile));
            requestFocus(edMobileFwp);
            return false;
        }
        else if (!(edMobileFwp.getText().toString().trim().length()==4)){
            tilMobileFwp.setError(getContext().getString(R.string.err_msg_mobile_length));
            requestFocus(edMobileFwp);
            return false;
        }
        else if (!(edMobileFwp.getText().toString().trim().equalsIgnoreCase(otp))){
            tilMobileFwp.setError("OTP not match");
            requestFocus(edMobileFwp);
            return false;
        }else {
            tilMobileFwp.setErrorEnabled(false);
            FwdokButton.setEnabled(true);
        }
        return true;
    }

    public void callState(String states, Dialog dialog, mCallBackResponse mCallBackResponse) {
        repository.apiCallState(states,dialog,mCallBackResponse);
    }

    public LiveData<List<StateModel>> getState() {
        return mutableLiveDataState;
    }

    public LiveData<List<StateModel>> getCity() {
        return mutableLiveDatapackagesCity;
    }

    public void callCity(String state_id, Dialog dialog, mCallBackResponse mCallBackResponse) {
        repository.apiCallCity(state_id,dialog,mCallBackResponse);
    }
}