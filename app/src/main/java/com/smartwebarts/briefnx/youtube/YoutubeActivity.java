package com.smartwebarts.briefnx.youtube;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.adapters.YoutubeAdapter;
import com.smartwebarts.briefnx.dashboard.DashboardActivity;
import com.smartwebarts.briefnx.models.NewsByCategoryModel;
import com.smartwebarts.briefnx.models.NewsModel;
import com.smartwebarts.briefnx.models.YoutubeItemsModels;
import com.smartwebarts.briefnx.models.YoutubeMainModel;
import com.smartwebarts.briefnx.retrofit.UtilMethods;
import com.smartwebarts.briefnx.retrofit.mCallBackResponse;
import com.smartwebarts.briefnx.utils.Toolbar_Set;
import com.smartwebarts.briefnx.utils.UsefullMethods;

import java.util.ArrayList;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class YoutubeActivity extends AppCompatActivity {
    RecyclerView recyclerView_for_YoutubeChannel;
    private YoutubeMainModel list;
    private YoutubeViewModel youtubeViewModel;
    /*for swiperecyclerview*/
    private static final String TAG = "MainActivity";
    private String page_token = "";
    private ArrayList<YoutubeItemsModels> youtubevideo_list;
    private YoutubeAdapter youtubeAdapter;
    private boolean isLoading = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        youtubeViewModel = ViewModelProviders.of(this).get(YoutubeViewModel.class);
        youtubeViewModel.intit();
        setContentView(R.layout.activity_youtube);
        Toolbar_Set.INSTANCE.setToolbar(this, "Videos");
        recyclerView_for_YoutubeChannel = findViewById(R.id.recyclerView_for_YoutubeChannel);
        initScrollListener();
        youtubevideo_list = new ArrayList<>();
        loadMore();
        youtubeAdapter = new YoutubeAdapter(this, youtubevideo_list);
        recyclerView_for_YoutubeChannel.setAdapter(youtubeAdapter);
        getObserver();

    }

    private void getObserver() {
        Observer observer = new Observer() {
            @Override
            public void onChanged(Object o) {
//
                YoutubeMainModel data = (YoutubeMainModel) o;
                page_token = data.getNextPageToken();
                if(page_token!=null)
                {
                    isLoading=false;

                }
                if(!isLoading)
                {
                    for(int i=0;i<data.getItems().size();i++)
                    {
                        youtubevideo_list.add(data.getItems().get(i));
                    }
                }


//                youtubevideo_list = (ArrayList<YoutubeItemsModels>) data.getItems();
                Log.e("YoutubeClass====", "" + page_token+"::::"+youtubevideo_list.size());
                setAdapter();
            }
        };
        youtubeViewModel.getyoutubeVideo().observe(YoutubeActivity.this, observer);


    }


    private void setAdapter() {
        youtubeAdapter.setData(youtubevideo_list);
    }

    private void initScrollListener() {
        recyclerView_for_YoutubeChannel.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                if (!isLoading) {
                    if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == youtubevideo_list.size() - 1) {
                        //bottom of list!
                        loadMore();
                        isLoading = true;
                    }
                }
            }
        });
    }

    private void loadMore() {
        if (UtilMethods.INSTANCE.isNetworkAvialable(YoutubeActivity.this)) {
            Dialog dialog = UtilMethods.getCommonProgressDialog(YoutubeActivity.this);
            dialog.show();
            youtubeViewModel.callYouttube(page_token, dialog, new mCallBackResponse() {
                @Override
                public void success(String from, String message) {

                }

                @Override
                public void fail(String from) {
                    UsefullMethods.showMessage(YoutubeActivity.this, SweetAlertDialog.ERROR_TYPE, "Error", from, "OK", () -> {
                    });

                }
            });
        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(YoutubeActivity.this);
        }
    }
}