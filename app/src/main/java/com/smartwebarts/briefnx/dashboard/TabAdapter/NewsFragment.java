package com.smartwebarts.briefnx.dashboard.TabAdapter;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.smartwebarts.briefnx.AdapterPositionListener;
import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.adapters.CustomeAdapter;
import com.smartwebarts.briefnx.dashboard.DashboardActivity;
import com.smartwebarts.briefnx.dashboard.headerrecylerview.HeaderModel;
import com.smartwebarts.briefnx.dashboard.headerrecylerview.HeaderRecyclerAdapter;
import com.smartwebarts.briefnx.dashboard.headerrecylerview.Items;
import com.smartwebarts.briefnx.dashboard.headerrecylerview.ListItem;
import com.smartwebarts.briefnx.dashboard.headerrecylerview.SubArticleTopNews;
import com.smartwebarts.briefnx.dashboard.viewmodel.DashboardViewModel;
import com.smartwebarts.briefnx.dashboard.viewmodel.NewsResultCallback;
import com.smartwebarts.briefnx.dashboard.viewmodel.NewsViewModel;
import com.smartwebarts.briefnx.newsdetail.model.Articles_Model;
import com.smartwebarts.briefnx.newsdetail.model.NewsModelArticle;
import com.smartwebarts.briefnx.newsdetail.model.PaymentArticleModel;
import com.smartwebarts.briefnx.retrofit.UtilMethods;
import com.smartwebarts.briefnx.retrofit.mCallBackResponse;
import com.smartwebarts.briefnx.utils.AppSharedPreferences;
import com.smartwebarts.briefnx.utils.UsefullMethods;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import cn.pedant.SweetAlert.SweetAlertDialog;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter;

public class NewsFragment extends Fragment implements AdapterPositionListener, NewsSection.ClickListener {
    private RecyclerView recyclerView,recyclerViewheader;
    private NewsViewModel viewModel;
    private String catgory_id="",subCat_id="";
    private static final String DIALOG_TAG = "SectionItemInfoDialogTag";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(NewsViewModel.class);
        viewModel.init();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_news, container, false);
//        return inflater.inflate(R.layout.newsfragment_viewpager, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView=view.findViewById(R.id.recyclerView);
        recyclerViewheader=view.findViewById(R.id.recyclerViewheader);
        getObserver();
    }

    private void getObserver() {
        Observer observer=new Observer() {
            @Override
            public void onChanged(Object o) {

                Articles_Model articles_model= (Articles_Model) o;
                if(articles_model!=null&&articles_model.getNews()!=null)
                {
                    List<NewsModelArticle>list=articles_model.getNews();
                    setAdapter(list);
                    Log.e("NewsFRagment OnChanged",""+list+";"+catgory_id+";;"+subCat_id);
                }

                if(articles_model!=null&&articles_model.getSubArticleData()!=null)
                {
                    recyclerViewheader.setVisibility(View.VISIBLE);
                    SectionedRecyclerViewAdapter sectionedAdapter= new SectionedRecyclerViewAdapter();
                    List<String> header_content=new ArrayList<>();
                    for(int i=0;i<articles_model.getSubArticleData().size();i++)
                    {
                        if(!header_content.contains(articles_model.getSubArticleData().get(i).getHeader()))
                            header_content.add(articles_model.getSubArticleData().get(i).getHeader());
                    }
                    for(int j=0;j<header_content.size();j++)
                    {
                        ArrayList<NewsModelArticle> topnewscontent=new ArrayList<>();
                        String sub_catId="";
                        for(int i=0;i<articles_model.getSubArticleData().size();i++)
                        {
                            if(header_content.get(j).equalsIgnoreCase(articles_model.getSubArticleData().get(i).getHeader()))
                            {
                                sub_catId=articles_model.getSubArticleData().get(i).getSuperCategoryId();
                                topnewscontent.add(articles_model.getSubArticleData().get(i));
                            }

                        }
                        Log.e("NewsFragmentSub===",""+header_content.get(j)+";;;"+sub_catId);
                        sectionedAdapter.addSection(new NewsSection(
                                requireActivity(),header_content.get(j),topnewscontent,
                                NewsFragment.this,sub_catId
                        ));


                    }
                    recyclerViewheader.setAdapter(sectionedAdapter);

                }
                else
                {
                    recyclerViewheader.setVisibility(View.GONE);
                }

              /*  if(articles_model!=null&&articles_model.getSubArticleData()!=null)
                {
                    recyclerViewheader.setVisibility(View.VISIBLE);
                    ArrayList<ListItem> topnewscontent=new ArrayList<>();
                    List<String> header_content=new ArrayList<>();
                    for(int i=0;i<articles_model.getSubArticleData().size();i++)
                    {
                        if(!header_content.contains(articles_model.getSubArticleData().get(i).getHeader()))
                        header_content.add(articles_model.getSubArticleData().get(i).getHeader());
                    }

                    for(int j=0;j<header_content.size();j++)
                    {
                        topnewscontent.add(new HeaderModel(header_content.get(j)));
                        for(int i=0;i<articles_model.getSubArticleData().size();i++)
                        {
                            if(header_content.get(j).equalsIgnoreCase(articles_model.getSubArticleData().get(i).getHeader()))
                            {
                                topnewscontent.add(new Items(articles_model.getSubArticleData().get(i)));
                            }

                        }

                    }
                    HeaderRecyclerAdapter adapter=new HeaderRecyclerAdapter(requireActivity(),topnewscontent);
                    recyclerViewheader.setAdapter(adapter);
                    Log.e("NewFragmentChanged",""+topnewscontent.size());

                }*/





            }
        };
        viewModel.getArticles().observe(requireActivity(),observer);



       /* if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity()))
        {
            Dialog dialog = UtilMethods.getCommonProgressDialog(requireActivity());
            dialog.show();
            viewModel.callPackage("packeges",dialog);
        }
        else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(getActivity());
        }*/


    }



    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        Log.e("News fragment===","onAttach");
        ((DashboardActivity)getActivity()).registerlistener(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ((DashboardActivity) getActivity()).unregisterDataUpdateListener(this);
    }

    private void setAdapter(List<NewsModelArticle> list)
    {
        Log.e("News Fragment==", "setAdapter: "+list.size());
        CustomeAdapter adapter=new CustomeAdapter(requireActivity(),list,viewModel);
        recyclerView.setAdapter(adapter);
    }


    @Override
    public void onClick(String position, String superCategoryId)
    {
        Log.e("News Fragment==", "OnClick: "+position);
        AppSharedPreferences preferences = new AppSharedPreferences(getActivity().getApplication());
        String language_set="";
        if (!TextUtils.isEmpty(preferences.getLanguage())) {
            Log.e("Language===", "" + preferences.getLanguage());
            if (preferences.getLanguage().equalsIgnoreCase("hi")) {
                language_set = "hindi";
            } else {
                language_set = "english";
            }

        }
        else
        {
            language_set = "hindi";
        }
        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity()))
        {
            catgory_id=position;
            subCat_id=superCategoryId;
            Dialog dialog = UtilMethods.getCommonProgressDialog(requireActivity());
            dialog.show();
            viewModel.callArticles(preferences.getLoginUserId(),position,superCategoryId,language_set,dialog,"", new mCallBackResponse() {
                @Override
                public void success(String from, String message) {
                }

                @Override
                public void fail(String from) {

                }
            });
        }
        else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(getActivity());
        }
//        hitApi(position,superCategoryId);

    }


    @SuppressLint("LongLogTag")
    @Override
    public void onItemRootViewClicked(@NonNull NewsSection section, int itemAdapterPosition) {
        Log.e(DIALOG_TAG,"onItemRootViewClicked"+itemAdapterPosition);

    }
    @SuppressLint("LongLogTag")
    @Override
    public void onFooterRootViewClicked(@NonNull NewsSection section, String itemAdapterPosition) {
//        Log.e(DIALOG_TAG,"onFooterRootViewClicked"+itemAdapterPosition);

      for(int i=0;i<((DashboardActivity)getActivity()).tabLayout.getTabCount();i++)

        {
            Log.e(DIALOG_TAG,"onFooterRootViewClicked"+((DashboardActivity)getActivity()).tabLayout.getTabAt(i).getTag());
            if(i!=0)
            {
                if(String.valueOf(((DashboardActivity)getActivity()).tabLayout.getTabAt(i).getTag()).equalsIgnoreCase(itemAdapterPosition))
                {
                    ((DashboardActivity)getActivity()).tabLayout.getTabAt(i).select();

//                    ((DashboardActivity)getActivity()).tabLayout.setScrollPosition(pageIndex,0f,true);
                }
            }

        }
//        if(((DashboardActivity)getActivity()).tabLayout.getTag());

    }
}