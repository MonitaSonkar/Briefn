package com.smartwebarts.briefnx.newsdetail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.newsdetail.model.NewsModelArticle;
import com.smartwebarts.briefnx.utils.Toolbar_Set;
import com.smartwebarts.briefnx.utils.Urls;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Url;

public class NewsDetailsActivity extends AppCompatActivity {
    private RecyclerView relatednews_recyclerview;
    private TextView news_description, related_tv,image_caption,publisheddate;
    public List<NewsModelArticle> newslist;
    private ImageView newsimage,icon;
    private String position_id = "";
    private WebView webview;
    private AppCompatTextView newstitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
        newslist = null;
        if (getIntent().hasExtra("newslist")) {
            newslist = (List<NewsModelArticle>) getIntent().getSerializableExtra("newslist");
            position_id = getIntent().getStringExtra("position_id");
            Log.e("News Details==", "" + position_id + ";" + newslist.size());
        }
        ArrayList<NewsModelArticle> arrayList = new ArrayList<>();
        int index = -1;
        for (int i = 0; i < newslist.size(); i++) {
            if (!newslist.get(i).getId().equalsIgnoreCase(position_id)) {
                arrayList.add(newslist.get(i));
            } else {
                index = i;
            }
        }
        Toolbar_Set.INSTANCE.setToolbarBack(this, "News Details",newslist.get(index));
        relatednews_recyclerview = findViewById(R.id.relatednews_recyclerview);
        LinearLayoutManager mLayoutManagerVideo = new LinearLayoutManager(NewsDetailsActivity.this);
        mLayoutManagerVideo.setOrientation(LinearLayoutManager.HORIZONTAL);
        relatednews_recyclerview.setLayoutManager(mLayoutManagerVideo);
        news_description = findViewById(R.id.news_description);
        webview = findViewById(R.id.webview);
        related_tv = findViewById(R.id.related_tv);
        newstitle = findViewById(R.id.newstitle);
        publisheddate = findViewById(R.id.publisheddate);
        image_caption = findViewById(R.id.image_caption);
        related_tv.setVisibility(View.GONE);
        relatednews_recyclerview.setVisibility(View.GONE);
        newsimage = findViewById(R.id.newsimage);
        icon = findViewById(R.id.icon);
        SpannableStringBuilder builder = new SpannableStringBuilder();
        String red = newslist.get(index).getDisplayTopic()+": ";
        SpannableString redSpannable= new SpannableString(red);
        redSpannable.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.iconColor)), 0, red.length(), 0);
        builder.append(redSpannable);
        builder.append(Html.fromHtml(newslist.get(index).getTitle()));
        newstitle.setText(builder, TextView.BufferType.SPANNABLE);
//        newstitle.setText();
        Glide.with(NewsDetailsActivity.this)
                .load(Urls.NEWS_IMGES + newslist.get(index).getImage())
                .placeholder(R.drawable.img_not_found)
                .into(newsimage);
        Glide.with(NewsDetailsActivity.this)
                .load(Urls.NEWS_IMGES_Byline + newslist.get(index).getBylinePic())
                .placeholder(R.drawable.img_not_found)
                .into(icon);
        String newscontent = Html.fromHtml(newslist.get(index).getDescription()).toString();
        news_description.setText(newscontent);
        publisheddate.setText(newslist.get(index).getBylineName().trim()+"|Updated :"+newslist.get(index).getPublishDate().trim());
        image_caption.setText(newslist.get(index).getPhotoCaption());
        webview.setVerticalScrollBarEnabled(true);
        webview.getSettings().setJavaScriptEnabled(true);

        String mimeType = "text/html;charset=UTF-8";
        String encoding = "utf-8";
        String htmlText = newslist.get(index).getDescription();
        String text = "<html><head>"
                + "<style type=\"text/css\">@font-face {font-family: MyFont;src: url(\"file:///android_asset/notosans_regular.ttf\")}body{font-family: MyFont;color: #000000}"
                + "</style></head>"
                + "<body>"
                + htmlText
                + "</body></html>";

        webview.loadDataWithBaseURL("",text,"text/html","utf-8","");

        if (arrayList.size() > 0) {
            related_tv.setVisibility(View.VISIBLE);
            relatednews_recyclerview.setVisibility(View.VISIBLE);
            NewsDetailAdapter newsDetailAdapter = new NewsDetailAdapter(NewsDetailsActivity.this, arrayList, newslist);
            relatednews_recyclerview.setAdapter(newsDetailAdapter);
        }

    }
}