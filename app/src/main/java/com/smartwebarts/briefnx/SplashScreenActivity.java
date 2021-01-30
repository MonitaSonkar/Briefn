package com.smartwebarts.briefnx;

import android.Manifest;
import android.annotation.SuppressLint;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.smartwebarts.briefnx.dashboard.DashboardActivity;
import com.smartwebarts.briefnx.dashboard.viewmodel.DashboardViewModel;
import com.smartwebarts.briefnx.intro.Session;
import com.smartwebarts.briefnx.intro.WelcomeActivity;
import com.smartwebarts.briefnx.newsdetail.model.SubscriptionModel;
import com.smartwebarts.briefnx.retrofit.UtilMethods;
import com.smartwebarts.briefnx.utils.AppSharedPreferences;

import java.util.List;
import java.util.Locale;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashScreenActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private DashboardViewModel mViewModel;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private View mControlsView;
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (AUTO_HIDE) {
                        delayedHide(AUTO_HIDE_DELAY_MILLIS);
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    view.performClick();
                    break;
                default:
                    break;
            }
            return false;
        }
    };

    private static final long SPLASH_DELAY = 2500l;
    private ImageView mAppLogoView;
    private LinearLayout description;

    private String user;

    Animation topAnim, bottomAnim;

    private static final int REQUEST_LOCATION = 202;
    private Handler mDelayHandler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {

            startNextActivity();
        }
    };
    private Session session;
    // private GoogleApiClient googleApiClient;

    private void startNextActivity() {

        // Checking for first time launch - before calling setContentView()
        session = new Session(this);
        if (session.isFirstTimeLaunch()) {
            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            finish();
            overridePendingTransition(R.anim.enter, R.anim.exit);
            startActivity(intent);
        } else {

            // checkPermission();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = ViewModelProviders.of(this).get(DashboardViewModel.class);
        mViewModel.init();
        setContentView(R.layout.activity_splash_screen);

        Configuration config = getBaseContext().getResources().getConfiguration();
        AppSharedPreferences preferences = new AppSharedPreferences(getApplication());
        Log.d("Splash==", "onCreate: " + preferences.getLanguage());
        if (!"".equals(preferences.getLanguage()) && !config.locale.getLanguage().equals(preferences.getLanguage())) {

            Locale locale = new Locale(preferences.getLanguage());
            Locale.setDefault(locale);
            config.locale = locale;
            getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
        }

        mVisible = true;
        mControlsView = findViewById(R.id.fullscreen_content_controls);
        mContentView = findViewById(R.id.fullscreen_content);

        // Set up the user interaction to manually show or hide the system UI.
//        mContentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggle();
//            }
//        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);

        //Animation on Icon
        init();

        mDelayHandler.postDelayed(runnable, SPLASH_DELAY);
        Observer observer1 = new Observer() {
            @Override
            public void onChanged(Object o) {

                UtilMethods.subscribtion_plans = (List<SubscriptionModel>) o;
            }
        };
        mViewModel.getPackages().observe(SplashScreenActivity.this, observer1);

        if (UtilMethods.INSTANCE.isNetworkAvialable(SplashScreenActivity.this)) {
            Dialog dialog = UtilMethods.getCommonProgressDialog(SplashScreenActivity.this);
//            dialog.show();
            mViewModel.callPackage("packeges", dialog);
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(SplashScreenActivity.this);
        }
        startAnimation();
    }

    private void startAnimation() {
        mAppLogoView.startAnimation(topAnim);
        description.startAnimation(bottomAnim);
    }

    private void init() {
        topAnim = AnimationUtils.loadAnimation(this, R.anim.top_animator);
        bottomAnim = AnimationUtils.loadAnimation(this, R.anim.bottom_animator);
        mAppLogoView = findViewById(R.id.fullscreen_content);
        mAppLogoView.setImageDrawable(getDrawable(R.drawable.logo));
        description = findViewById(R.id.description);
        next();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(0);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }


    private void checkPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ) {

            // You can use the API that requires the permission.
            //turnongps();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }
    }

 /*   private void turnongps() {
        final LocationManager manager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {
//            Toast.makeText(getApplicationContext(),"Gps already enabled",Toast.LENGTH_SHORT).show();
            //getActivity().finish();
          //  next();
        }
        // Todo Location Already on  ... end

        if(!hasGPSDevice(this)){
//            Toast.makeText(getApplicationContext(),"Gps not Supported",Toast.LENGTH_SHORT).show();
        }

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER) && hasGPSDevice(this)) {
            // Log.e("TAG","Gps already enabled");
//            Toast.makeText(getApplicationContext(),"Gps not enabled",Toast.LENGTH_SHORT).show();
           // enableLoc();
        }else{
            // Log.e("TAG","Gps already enabled");
//            Toast.makeText(getApplicationContext(),"Gps already enabled",Toast.LENGTH_SHORT).show();
        }
    }*/

    public void next() {
        AppSharedPreferences preferences = new AppSharedPreferences(getApplication());

        user = preferences.getLoginDetails();
        if (user == null || user.isEmpty()) {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            finish();
            overridePendingTransition(R.anim.enter, R.anim.exit);
            startActivity(intent);
        } else {
            Intent intent;
            if (preferences.getLoginRole().equalsIgnoreCase("user")) {
                intent = new Intent(getApplicationContext(), DashboardActivity.class);

                intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                finish();
                overridePendingTransition(R.anim.enter, R.anim.exit);
                startActivity(intent);
            }
        }
    }

    private boolean hasGPSDevice(Context context) {
        final LocationManager mgr = (LocationManager) context
                .getSystemService(Context.LOCATION_SERVICE);
        if (mgr == null)
            return false;
        final List<String> providers = mgr.getAllProviders();
        return providers.contains(LocationManager.GPS_PROVIDER);
    }

    /*  private void enableLoc() {

          if (googleApiClient == null) {
              googleApiClient = new GoogleApiClient.Builder(getApplicationContext())
                      .addApi(LocationServices.API)
                      .addConnectionCallbacks(new GoogleApiClient.ConnectionCallbacks() {
                          @Override
                          public void onConnected(Bundle bundle) {
                          }

                          @Override
                          public void onConnectionSuspended(int i) {
                              googleApiClient.connect();
                          }
                      })
                      .addOnConnectionFailedListener(new GoogleApiClient.OnConnectionFailedListener() {
                          @Override
                          public void onConnectionFailed(ConnectionResult connectionResult) {

                              Log.d("Location error","Location error " + connectionResult.getErrorCode());
                          }
                      }).build();
              googleApiClient.connect();

              LocationRequest locationRequest = LocationRequest.create();
              locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
              locationRequest.setInterval(7 * 1000);  //30 * 1000
              locationRequest.setFastestInterval(5 * 1000); //5 * 1000
              LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                      .addLocationRequest(locationRequest);

              builder.setAlwaysShow(true);

              PendingResult<LocationSettingsResult> result =
                      LocationServices.SettingsApi.checkLocationSettings(googleApiClient,builder.build());
              result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                  @Override
                  public void onResult(LocationSettingsResult result) {
                      final Status status = result.getStatus();
                      switch (status.getStatusCode()) {
                          case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                              try {
                                  // Show the dialog by calling startResolutionForResult(),
                                  // and check the result in onActivityResult().
                                  status.startResolutionForResult(SplashScreenActivity.this,REQUEST_LOCATION);

                                  // getActivity().finish();
                              } catch (IntentSender.SendIntentException e) {
                                  // Ignore the error.
                              }
                              break;
                      }

                  }
              });
          }
      }
  */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (REQUEST_LOCATION == requestCode) {
            next();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        switch (requestCode) {
            case 101:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // turnongps();
                } else {
                    // turnongps();
                }
                return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mDelayHandler.removeCallbacks(runnable);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
    }
}