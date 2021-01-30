package com.smartwebarts.briefnx.fragments.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.gson.Gson;
import com.smartwebarts.briefnx.MainActivity;
import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.adapters.Spinner_Adapter;
import com.smartwebarts.briefnx.dashboard.TabAdapter.SubscribtionDailog;
import com.smartwebarts.briefnx.models.OTPModel;
import com.smartwebarts.briefnx.models.StateModel;
import com.smartwebarts.briefnx.newsdetail.model.Articles_Model;
import com.smartwebarts.briefnx.newsdetail.model.NewsModelArticle;
import com.smartwebarts.briefnx.newsdetail.model.PaymentArticleModel;
import com.smartwebarts.briefnx.retrofit.UtilMethods;
import com.smartwebarts.briefnx.retrofit.mCallBackResponse;
import com.smartwebarts.briefnx.utils.UsefullMethods;

import java.util.ArrayList;
import java.util.List;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    private SignInViewModel mViewModel;
    private View movetologinb, movetologina;
    private TextInputEditText ed_firstname, ed_lastname, ed_shopname, ed_gstin, ed_mobile, ed_location, ed_zipcode, ed_password;
    private CircularProgressButton register;
    private Spinner state_spinner, city_spinner;
    private String state_value = "", city_value = "";

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.sign_up_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(SignInViewModel.class);
        mViewModel.init();
        mViewModel.setActivity(requireActivity());
        init(view);
        addListeners();
        getObserver();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    private void getObserver() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            Dialog dialog = UtilMethods.getCommonProgressDialog(requireActivity());
            dialog.show();
            mViewModel.callState("states", dialog, new mCallBackResponse() {
                @Override
                public void success(String from, String message) {
                    state_spinner.setVisibility(View.VISIBLE);
                }

                @Override
                public void fail(String from) {
                state_spinner.setVisibility(View.GONE);
                    UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.ERROR_TYPE, "Error", from, "OK", () -> {
//                        dialog.dismiss();
                    });
                }
            });
        }
        else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(getActivity());
            }
        Observer observer = new Observer() {
            @Override
            public void onChanged(Object o) {

                ArrayList<StateModel> statemodel = (ArrayList<StateModel>) o;
                Log.e("SingUpFragment=====", "" + statemodel.size());
                if (statemodel != null && statemodel.size() > 0) {
                    setAdapter(statemodel);
                }
            }
        };
        mViewModel.getState().observe(requireActivity(), observer);

        Observer cityobserver = new Observer() {
            @Override
            public void onChanged(Object o) {
                ArrayList<StateModel> cityModel = (ArrayList<StateModel>) o;
                Log.e("SingUpFragment=====", "" + cityModel.size());
                if (cityModel != null && cityModel.size() > 0) {
                    setAdapterCity(cityModel);
                }
            }
        };
        mViewModel.getCity().observe(requireActivity(), cityobserver);


    }

    private void setAdapterCity(ArrayList<StateModel> cityModel) {
        city_spinner.setVisibility(View.VISIBLE);
        SpinnerAdapter adapter = new Spinner_Adapter(requireActivity(), cityModel);
        city_spinner.setAdapter(adapter);
        city_spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        ((TextView) city_spinner.getSelectedView()).setTextColor(getResources().getColor(R.color.whiteTextColor));
                        // It returns the clicked item.
                        StateModel clickedItem = (StateModel)
                                parent.getItemAtPosition(position);
                        String name = clickedItem.getName();
                        String city_id = clickedItem.getId();
                        city_value=city_id;
//                        Toast.makeText(requireActivity(), name + ":::::" + city_id, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
    }

    private void setAdapter(ArrayList<StateModel> statemodel) {
        state_spinner.setVisibility(View.VISIBLE);
        SpinnerAdapter adapter = new Spinner_Adapter(requireActivity(), statemodel);
        state_spinner.setAdapter(adapter);
        state_spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent,
                                               View view, int position, long id) {
                        ((TextView) state_spinner.getSelectedView()).setTextColor(getResources().getColor(R.color.whiteTextColor));
                        // It returns the clicked item.
                        StateModel clickedItem = (StateModel)
                                parent.getItemAtPosition(position);
                        String name = clickedItem.getName();
                        String state_id = clickedItem.getId();
                        state_value=state_id;
                        api_call_city(state_id);
//                        Toast.makeText(requireActivity(), name + ":::::" + state_id, Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
    }

    private void api_call_city(String state_id) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {
            Dialog dialog = UtilMethods.getCommonProgressDialog(requireActivity());
            dialog.show();
            mViewModel.callCity(state_id, dialog, new mCallBackResponse() {
                @Override
                public void success(String from, String message) {
                    city_spinner.setVisibility(View.VISIBLE);
                }

                @Override
                public void fail(String from) {
                    city_spinner.setVisibility(View.GONE);
                    UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.ERROR_TYPE, "Error", from, "OK", () -> {
//                        dialog.dismiss();
                    });





                    }
            });
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(getActivity());
        }
    }

    private void addListeners() {
        movetologina.setOnClickListener(this);
        movetologinb.setOnClickListener(this);
        register.setOnClickListener(this);
    }

    private void init(View view) {
        movetologina = view.findViewById(R.id.movetologina);
        movetologinb = view.findViewById(R.id.movetologinb);
        state_spinner = view.findViewById(R.id.state_spinner);
        city_spinner = view.findViewById(R.id.city_spinner);
        ed_firstname = view.findViewById(R.id.ed_firstname);
        ed_lastname = view.findViewById(R.id.ed_lastname);
        ed_shopname = view.findViewById(R.id.ed_shopname);
        ed_mobile = view.findViewById(R.id.ed_mobile);
        ed_location = view.findViewById(R.id.ed_location);
        ed_password = view.findViewById(R.id.ed_password);
        ed_gstin = view.findViewById(R.id.ed_gstin);
        ed_zipcode = view.findViewById(R.id.ed_zipcode);

        register = view.findViewById(R.id.cirRegisterButton);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {
        if (v == movetologina || v == movetologinb) {
            requireActivity().onBackPressed();
        }

        if (v == register && validateform()) {
            mViewModel.setMobileNumber(ed_mobile.getText().toString().trim());
            mViewModel.setPassword(ed_password.getText().toString().trim());
            mViewModel.setFirstName(ed_firstname.getText().toString().trim());
            mViewModel.setLastName(ed_lastname.getText().toString().trim());
            mViewModel.setLocation(ed_location.getText().toString().trim());
            mViewModel.setPincode(ed_zipcode.getText().toString().trim());
            mViewModel.setState_id(state_value);
            mViewModel.setCity_id(city_value);
            // mViewModel.setShopName(ed_shopname.getText().toString().trim());
//            mViewModel.setGstin(ed_gstin.getText().toString().trim());
            mViewModel.register(v);


          /*  if (UtilMethods.INSTANCE.isNetworkAvialable(getContext())) {

                UtilMethods.INSTANCE.otp(getActivity(), mViewModel.getMobileNumber().getValue().trim(), "2",new mCallBackResponse() {
                    @Override
                    public void success(String from, String message) {
                        OTPModel otpModel = new Gson().fromJson(message, OTPModel.class);
                        mViewModel.openVerifyOTP(v, otpModel.getOtp());
                    }

                    @Override
                    public void fail(String from) {
                        UsefullMethods.showMessage(getActivity(), SweetAlertDialog.ERROR_TYPE, "Error", from, "OK", () -> {

                        });
                    }
                });

            } else {
                UtilMethods.INSTANCE.internetNotAvailableMessage(getContext());
            }*/
        }
    }

    private boolean validateform() {

        if (ed_firstname.getText().toString().equals("")) {
            ed_firstname.setError("Enter First Name");
            return false;
        } else if (ed_lastname.getText().toString().equals("")) {
            ed_lastname.setError("Enter Last Name");
            return false;
        }
//        else if(ed_shopname.getText().toString().equals("")){
//            ed_shopname.setError("Enter Shop Name");
//            return false;
//        }
//        else if(ed_gstin.getText().toString().isEmpty() || ed_gstin.getText().toString().length()<15 || !ed_gstin.getText().toString().matches("^[0-9]{2}[A-Z]{5}[0-9]{4}[A-Z]{1}[0-9]{1}[A-Z]{1}[0-9]{1}$")){
//            ed_gstin.setError("Enter valid 15 digit Gstin");
//            return false;
//        }
        else if (ed_mobile.getText().toString().isEmpty() || ed_mobile.getText().toString().length() < 10) {
            ed_mobile.setError("Enter 10 digit Mobile Number");
            return false;
        } else if (ed_location.getText().toString().equals("")) {
            ed_location.setError("Enter Location");
            return false;
        } else if (ed_zipcode.getText().toString().equals("")) {
            ed_zipcode.setError("Enter Zipcode");
            return false;
        } else if (ed_password.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter Password", Toast.LENGTH_SHORT).show();
        }
        return true;
    }
}