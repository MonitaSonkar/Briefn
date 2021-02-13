package com.smartwebarts.briefnx.dashboard.search;

import android.app.Dialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.adapters.CustomeAdapter;
import com.smartwebarts.briefnx.dashboard.TabAdapter.SubscribtionDailog;
import com.smartwebarts.briefnx.dashboard.viewmodel.NewsViewModel;
import com.smartwebarts.briefnx.newsdetail.model.Articles_Model;
import com.smartwebarts.briefnx.newsdetail.model.NewsModelArticle;
import com.smartwebarts.briefnx.newsdetail.model.PaymentArticleModel;
import com.smartwebarts.briefnx.retrofit.UtilMethods;
import com.smartwebarts.briefnx.retrofit.mCallBackResponse;
import com.smartwebarts.briefnx.utils.AppSharedPreferences;
import com.smartwebarts.briefnx.utils.UsefullMethods;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SearchActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayout searchBarll;
    private ImageView back;
    private SearchView search;
    private NewsViewModel viewModel;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_news);
        viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        viewModel.init();
        recyclerView=findViewById(R.id.recyclerView);
        search=findViewById(R.id.search);
        back=findViewById(R.id.back);
        searchBarll=findViewById(R.id.searchBarll);
        searchBarll.setVisibility(View.VISIBLE);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.e("SearchActivity==","onQueryTextChange");
                hitApi(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                return true;
            }
        });
        if(getIntent().hasExtra("search_text"))
        {
//            search.setFocusable(true);
            search.onActionViewExpanded();
            search.setQuery(getIntent().getStringExtra("search_text"),true);
            search.clearFocus();

//          hitApi(getIntent().getStringExtra("search_text"));
        }
        getObserver();


    }

    private void hitApi(String search_text) {
        AppSharedPreferences preferences = new AppSharedPreferences(SearchActivity.this.getApplication());
        String language_set="";
        if (!TextUtils.isEmpty(preferences.getLanguage())) {
            Log.e("Language===", "" + preferences.getLanguage());
            if (preferences.getLanguage().equalsIgnoreCase("hi")) {
                language_set = "hindi";
            } else {
                language_set = "english";
            }

        }
        if (UtilMethods.INSTANCE.isNetworkAvialable(SearchActivity.this))
        {
            Dialog dialog = UtilMethods.getCommonProgressDialog(SearchActivity.this);
            dialog.show();
            viewModel.callArticles(preferences.getLoginUserId(),"","",language_set,dialog,search_text,1, new mCallBackResponse() {
                @Override
                public void success(String from, String message) {

                }

                @Override
                public void fail(String from) {
                    UsefullMethods.showMessage(SearchActivity.this, SweetAlertDialog.ERROR_TYPE, "Error", from, "OK", () -> {
                        finish();
                    });
                }
            });
        }
        else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(SearchActivity.this);
        }
    }

    private void getObserver() {
        Observer observer=new Observer() {
            @Override
            public void onChanged(Object o) {

                Articles_Model articles_model= (Articles_Model)o;
                PaymentArticleModel paymentArticleModel=articles_model.getPaymentData();
                List<NewsModelArticle>list=articles_model.getNews();
                Log.e("NewsFRagment",""+list);
                setAdapter(list);
            }
        };
        viewModel.getArticles().observe(SearchActivity.this,observer);
    }
    private void setAdapter(List<NewsModelArticle> list) {
        Log.e("News Fragment==", "setAdapter: "+list.size());
        CustomeAdapter adapter=new CustomeAdapter(SearchActivity.this,list,viewModel);
        recyclerView.setAdapter(adapter);
    }
}
