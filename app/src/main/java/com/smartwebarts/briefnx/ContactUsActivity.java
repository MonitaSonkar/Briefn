package com.smartwebarts.briefnx;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import com.smartwebarts.briefnx.utils.Urls;

public class ContactUsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us2);
    }

    public void back(View view) {
        onBackPressed();
    }

    public void facebook(View view) {
        openUrl(Urls.FACEBOOK);
    }

    public void instagram(View view) {
        openUrl(Urls.INSTAGRAM);
    }

    public void twitter(View view) {
        openUrl(Urls.TWITTER);
    }

    public  void openUrl(String url) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        startActivity(browserIntent);
    }

    public void linkedin(View view) {
        openUrl(Urls.LINKEDIN);
    }

    public void youtube(View view) {
        openUrl(Urls.YOUTUBE);
    }
}