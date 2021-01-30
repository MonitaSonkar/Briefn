package com.smartwebarts.briefnx;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.smartwebarts.briefnx.retrofit.APIClient;
import com.smartwebarts.briefnx.retrofit.UtilMethods;
import com.smartwebarts.briefnx.retrofit.mCallBackResponse;
import com.smartwebarts.briefnx.utils.AccessToken;
import com.smartwebarts.briefnx.utils.AppSharedPreferences;
import com.smartwebarts.briefnx.utils.MyClick;
import com.smartwebarts.briefnx.utils.MyGlide;
import com.smartwebarts.briefnx.utils.Toolbar_Set;
import com.smartwebarts.briefnx.utils.Urls;
import com.smartwebarts.briefnx.utils.UsefullMethods;

import androidx.annotation.NonNull;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private AppBarConfiguration mAppBarConfiguration;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private boolean isHome = true;
    NavController graph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar_Set.INSTANCE.getCartList(this);
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.e("TAG", "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = Objects.requireNonNull(task.getResult()).getToken();
                        new AccessToken().setAccess_token(getApplicationContext(), token);
//                        Log.e("TAG", token);

                        UtilMethods.INSTANCE.updateAccessToken(MainActivity.this, token, new mCallBackResponse() {
                            @Override
                            public void success(String from, String message) {

                            }

                            @Override
                            public void fail(String from) {

                            }
                        });
                    }
                });

        drawer = (DrawerLayout)findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        graph = Navigation.findNavController(findViewById(R.id.nav_host_fragment));
        setGraph(true);
        setToolbar();

        setNavigationDrawer();


        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setDrawerLayout(drawer)
                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
//        NavigationUI.setupWithNavController(navigationView, navController);
    }

    private void setToolbar() {
      /*  RelativeLayout notificationView = findViewById(R.id.notificationView);
        notificationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setGraph(false);
            }
        });*/
    }

    private void setNavigationDrawer() {

        View headerView = navigationView.getHeaderView(0);
        TextView tvUser = headerView.findViewById(R.id.tvName);
        TextView tvEmail = headerView.findViewById(R.id.tvEmail);
        ImageView imageView = headerView.findViewById(R.id.imageView);

        AppSharedPreferences preferences = new AppSharedPreferences(getApplication());
        tvUser.setText(String.format("%s", preferences.getLoginUserName()));
        tvEmail.setText(String.format("%s", preferences.getLoginUserShopName()));
        MyGlide.profile(this, Uri.parse(APIClient.BASE_URL + preferences.getLoginUserImage()), imageView);
//        tvEmail.setText("");

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {

            case R.id.nav_home: {
                setGraph(true);
                break;
            }
            case R.id.nav_gallery: {
                break;
            }


            case R.id.terms : {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.DATA, Urls.TERMS_CONDITION);
                intent.putExtra(WebViewActivity.TITLE, "Terms & Conditions");
                startActivity(intent);
                break;
            }
            case R.id.privacy_policy : {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.DATA, Urls.PRIVACY_POLICY);
                intent.putExtra(WebViewActivity.TITLE, "Privacy Policy");
                startActivity(intent);
                break;
            }
            case R.id.refund_policy : {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.DATA, Urls.REFUND_POLICY);
                intent.putExtra(WebViewActivity.TITLE, "Refund Policy");
                startActivity(intent);
                break;
            }
            case R.id.about_us : {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.DATA, Urls.ABOUT_US);
                intent.putExtra(WebViewActivity.TITLE, "About Us");
                startActivity(intent);
                break;
            }
            case R.id.live_chat : {
                try {
                    String url = "https://chat.whatsapp.com/";
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                } catch (Exception e) {
                    Toast.makeText(this, "Unable to open your whatsapp", Toast.LENGTH_SHORT).show();
                }
                break;
            }
            case R.id.share_app: {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID);
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
                break;
            }
            case R.id.contact_us: {
                startActivity(new Intent(MainActivity.this, ContactUsActivity.class));
                break;
            }
            case R.id.faq : {
                Intent intent = new Intent(MainActivity.this, WebViewActivity.class);
                intent.putExtra(WebViewActivity.DATA, Urls.FAQ);
                intent.putExtra(WebViewActivity.TITLE, "FAQs");
                startActivity(intent);
                break;
            }
            case R.id.logout : {
                logout();
                break;
            }
        }
        //close navigation drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void openDrawer(View view) {
        drawer.openDrawer(GravityCompat.START);
    }

    private void logout() {
        UsefullMethods.showMessage(this, SweetAlertDialog.WARNING_TYPE, "Warning", "Are you sure you want to logout ?", "Yes", new MyClick() {
            @Override
            public void onClick() {
                AppSharedPreferences preferences = new AppSharedPreferences(getApplication());
                preferences.logout(MainActivity.this);
            }
        });
    }

    public void setGraph(boolean isHome) {

        if (isHome) graph.setGraph(R.navigation.mobile_navigation, getIntent().getExtras());
        else graph.setGraph(R.navigation.services_navigation, getIntent().getExtras());
    }

    @SuppressLint("RestrictedApi")
    @Override
    public void onBackPressed() {

//        NavGraph temp = graph.getGraph();
//        Log.e("temp", temp.getDisplayName());


        if (graph.getGraph().getId() == R.id.services_navigation) {
            graph.setGraph(R.navigation.mobile_navigation, getIntent().getExtras());
        } else {
            super.onBackPressed();
        }
    }
}