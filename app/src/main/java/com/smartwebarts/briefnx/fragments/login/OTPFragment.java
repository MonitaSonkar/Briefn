package com.smartwebarts.briefnx.fragments.login;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.dashboard.DashboardActivity;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class OTPFragment extends Fragment implements View.OnClickListener {

    private OTPViewModel mViewModel;
    private TextView login;
    private View view;
    private TextView textTimer, resendcode, number;
    private int time;
    private CircularProgressButton submit;

    public static OTPFragment newInstance() {
        return new OTPFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.otp_fragment, container, false);
        mViewModel = ViewModelProviders.of(this).get(OTPViewModel.class);
        textTimer = (TextView)view.findViewById(R.id.timer);
        resendcode = (TextView) view.findViewById(R.id.resendcode);
        number = (TextView) view.findViewById(R.id.number);
        submit = (CircularProgressButton) view.findViewById(R.id.submit);
        if (getArguments() != null) {
            number.setText(getArguments().getString("data", ""));
        }
        resendcode.setOnClickListener(this);
        number.setOnClickListener(this);
        submit.setOnClickListener(this);
        startTimer();

        mViewModel.getmUser().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
        return view;
    }

    private void startTimer() {
        textTimer.setVisibility(View.VISIBLE);
        resendcode.setEnabled(false);
        time = 60;
        new CountDownTimer(60000, 1000) {

            public void onTick(long millisUntilFinished) {
//                textTimer.setText("0:"+checkDigit(time));
                textTimer.setText(""+time);
                time--;
            }

            public void onFinish() {
                textTimer.setText("60");
                textTimer.setVisibility(View.GONE);
                resendcode.setEnabled(true);
            }

        }.start();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {

        if (v == resendcode) {
            startTimer();
        }
        if (v == number) {
            back(v);
        }
        if (v == submit) {
            dashboard(v);
        }
    }

    private void dashboard(View v) {
        Intent intent = new Intent(requireActivity(), DashboardActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
    }

    public void back(View view) {
        requireActivity().onBackPressed();
    }
}
