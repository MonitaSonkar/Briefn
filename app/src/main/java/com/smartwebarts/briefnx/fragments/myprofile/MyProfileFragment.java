package com.smartwebarts.briefnx.fragments.myprofile;

import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.retrofit.UtilMethods;
import com.smartwebarts.briefnx.retrofit.mCallBackResponse;
import com.smartwebarts.briefnx.utils.AppSharedPreferences;
import com.smartwebarts.briefnx.utils.MyClick;
import com.smartwebarts.briefnx.utils.MyGlide;
import com.smartwebarts.briefnx.utils.UsefullMethods;

import java.io.File;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.app.Activity.RESULT_OK;
import static com.smartwebarts.briefnx.utils.Urls.USER_PROFILE_IMAGE;
import static com.smartwebarts.briefnx.utils.UsefullMethods.takePicture;

public class MyProfileFragment extends Fragment implements View.OnClickListener {

    private MyProfileViewModel mViewModel;
    TextView Username, UserMobile, userEmail, userAddress, shopName, gstNumber, personalinfobtn;
    ImageView shopPic, gstPic, userProfilePic, adhaarPic, mechanicPic;
    private FloatingActionButton fabAddPhoto, fabAddGalleryPhoto, fabAddGstPhoto, fabAddMechanicPhoto, fabAddAdhaarPhoto;
    AppSharedPreferences preferences;
    private TextView mechanicName, adhaarNumber;

    public static MyProfileFragment newInstance() {
        return new MyProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = ViewModelProviders.of(this).get(MyProfileViewModel.class);
        View view = inflater.inflate(R.layout.my_profile_fragment, container, false);

        init(view);
        addListeners();
        setProfileData();

        return view;
    }

    private void setProfileData() {
        gstNumber.setText(preferences.getLoginUserGstin());
        Username.setText(preferences.getLoginUserName());
        UserMobile.setText(preferences.getLoginMobile());
        userEmail.setText(preferences.getLoginEmail());
        userAddress.setText(preferences.getLoginUserAddress());
        shopName.setText(preferences.getLoginUserShopName());
        gstNumber.setText(preferences.getLoginUserGstin());
        personalinfobtn.setText(preferences.getLoginUserStatus());
        MyGlide.profile(requireActivity(), Uri.parse(USER_PROFILE_IMAGE + preferences.getLoginUserImage()), userProfilePic);
        MyGlide.with(requireActivity(), Uri.parse(USER_PROFILE_IMAGE + preferences.getLoginUserShopImage()));
        MyGlide.with(requireActivity(), Uri.parse(USER_PROFILE_IMAGE + preferences.getLoginUserGstImage()));
        MyGlide.with(requireActivity(), Uri.parse(USER_PROFILE_IMAGE + preferences.getLoginUserAdhaarImage()));
        MyGlide.with(requireActivity(), Uri.parse(USER_PROFILE_IMAGE + preferences.getLoginUserMechanicImage()));
    }

    private void addListeners() {
        fabAddGalleryPhoto.setOnClickListener(this);
        gstPic.setOnClickListener(this);
        fabAddPhoto.setOnClickListener(this);
        fabAddGstPhoto.setOnClickListener(this);
        fabAddAdhaarPhoto.setOnClickListener(this);
        fabAddMechanicPhoto.setOnClickListener(this);
    }

    private void init(View view) {
        Username = view.findViewById(R.id.Username);
        UserMobile = view.findViewById(R.id.UserMobile);
        userEmail = view.findViewById(R.id.userEmail);
        userAddress = view.findViewById(R.id.userAddress);
        shopName = view.findViewById(R.id.shopName);
        personalinfobtn = view.findViewById(R.id.personalinfobtn);
        gstNumber = view.findViewById(R.id.gstNumber);
        mechanicName = view.findViewById(R.id.mechanicName);
        adhaarNumber = view.findViewById(R.id.adhaarNumber);

        shopPic = view.findViewById(R.id.shopPic);
        gstPic = view.findViewById(R.id.backPic);
        userProfilePic = view.findViewById(R.id.userProfilePic);
        adhaarPic = view.findViewById(R.id.adhaarPic);
        mechanicPic = view.findViewById(R.id.mechanicPic);

        fabAddPhoto = view.findViewById(R.id.fab_add_photo);
        fabAddGalleryPhoto = view.findViewById(R.id.fab_add_gallery_photo);
        fabAddGstPhoto = view.findViewById(R.id.fab_add_gst_photo);
        fabAddAdhaarPhoto = view.findViewById(R.id.fab_add_adhaar_photo);
        fabAddMechanicPhoto = view.findViewById(R.id.fab_add_mechanic_photo);

        preferences = new AppSharedPreferences(requireActivity().getApplication());
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        // TODO: Use the ViewModel
    }

    @Override
    public void onClick(View v) {
        if (v == fabAddGstPhoto ) {
            takePicture(this, 101);
        } if( v == fabAddGalleryPhoto ) {
            takePicture(this, 102);
        } if( v == fabAddPhoto) {
            takePicture(this, 103);
        } if( v == fabAddAdhaarPhoto ) {
            takePicture(this, 104);
        } if( v == fabAddMechanicPhoto) {
            takePicture(this, 105);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && data != null) {

            switch (requestCode) {
                case 101: {
                    MyGlide.with(requireActivity(), data.getData());
                    try {
                        File file = new File(data.getData().getPath());
                        UtilMethods.INSTANCE.uploadImage(requireActivity(), file, UtilMethods.user_table, "gstimage", new mCallBackResponse() {
                            @Override
                            public void success(String from, String message) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.SUCCESS_TYPE, "Successfull", "" + message, "OK", new MyClick() {
                                    @Override
                                    public void onClick() {
                                        updateProfile();
                                    }
                                });
                            }

                            @Override
                            public void fail(String from) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.ERROR_TYPE, "Error", ""+from, "OK", null);

                            }
                        });

                    } catch (Exception ignored) {}

                    break;
                }

                case 102: {
                    MyGlide.with(requireActivity(), data.getData());

                    try {

                        File file = new File(data.getData().getPath());

                        UtilMethods.INSTANCE.uploadImage(requireActivity(), file, UtilMethods.user_table, "shopimage",  new mCallBackResponse() {
                            @Override
                            public void success(String from, String message) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.SUCCESS_TYPE, "Successfull", "" + message, "OK", new MyClick() {
                                    @Override
                                    public void onClick() {
                                        updateProfile();
                                    }
                                });
                            }

                            @Override
                            public void fail(String from) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.ERROR_TYPE, "Error", ""+from, "OK", null);

                            }
                        });

                    } catch (Exception ignored) {}

                    break;
                }

                case 103: {
                    MyGlide.profile(requireActivity(), data.getData(), userProfilePic);

                    try {

                        File file = new File(data.getData().getPath());

                        UtilMethods.INSTANCE.uploadImage(requireActivity(), file, UtilMethods.user_table, "image", new mCallBackResponse() {
                            @Override
                            public void success(String from, String message) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.SUCCESS_TYPE, "Successfull", "" + message, "OK", new MyClick() {
                                    @Override
                                    public void onClick() {
                                        updateProfile();
                                    }
                                });
                            }

                            @Override
                            public void fail(String from) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.ERROR_TYPE, "Error", ""+from, "OK", null);
                            }
                        });

                    } catch (Exception ignored) {}

                    break;
                }

                case 104: {
                    MyGlide.with(requireActivity(), data.getData());

                    try {

                        File file = new File(data.getData().getPath());

                        UtilMethods.INSTANCE.uploadImage(requireActivity(), file, UtilMethods.user_table, "adhaarimage",  new mCallBackResponse() {
                            @Override
                            public void success(String from, String message) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.SUCCESS_TYPE, "Successfull", "" + message, "OK", new MyClick() {
                                    @Override
                                    public void onClick() {
                                        updateProfile();
                                    }
                                });
                            }

                            @Override
                            public void fail(String from) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.ERROR_TYPE, "Error", ""+from, "OK", null);

                            }
                        });

                    } catch (Exception ignored) {}

                    break;
                }

                case 105: {
                    MyGlide.with(requireActivity(), data.getData());

                    try {

                        File file = new File(data.getData().getPath());

                        UtilMethods.INSTANCE.uploadImage(requireActivity(), file, UtilMethods.user_table, "mechanicimage", new mCallBackResponse() {
                            @Override
                            public void success(String from, String message) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.SUCCESS_TYPE, "Successfull", "" + message, "OK", new MyClick() {
                                    @Override
                                    public void onClick() {
                                        updateProfile();
                                    }
                                });
                            }

                            @Override
                            public void fail(String from) {
                                UsefullMethods.showMessage(requireActivity(), SweetAlertDialog.ERROR_TYPE, "Error", ""+from, "OK", null);
                            }
                        });

                    } catch (Exception ignored) {}

                    break;
                }
            }
        }
    }

    public void updateProfile() {
        UtilMethods.INSTANCE.userdetail(requireActivity(), new mCallBackResponse() {
            @Override
            public void success(String from, String message) {
                setProfileData();
            }

            @Override
            public void fail(String from) {

            }
        });
    }
}