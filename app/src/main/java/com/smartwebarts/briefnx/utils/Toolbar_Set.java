package com.smartwebarts.briefnx.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.smartwebarts.briefnx.BuildConfig;
import com.smartwebarts.briefnx.R;

import com.smartwebarts.briefnx.dashboard.DashboardActivity;
import com.smartwebarts.briefnx.database.DatabaseClient;
import com.smartwebarts.briefnx.database.Task;
import com.smartwebarts.briefnx.newsdetail.model.NewsModelArticle;
import com.smartwebarts.briefnx.youtube.YoutubeActivity;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

public enum Toolbar_Set {

    INSTANCE;

    public void setToolbar(final Activity activity){
        ImageView back = activity.findViewById(R.id.back);
        ImageView facebook = activity.findViewById(R.id.facebook);
        ImageView insta = activity.findViewById(R.id.insta);
        ImageView twitter = activity.findViewById(R.id.twitter);
        ImageView youtube = activity.findViewById(R.id.youtube);
//        ImageView imageView=activity.findViewById(R.id.showProduct);

        facebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Intent facebookpage=new Intent("android.intent.action.VIEW",Uri.parse("https://www.facebook.com/Briefn.in/"));
            activity.startActivity(facebookpage);
            }
        });
        insta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent insta_page=new Intent("android.intent.action.VIEW",Uri.parse("https://www.instagram.com/briefn.in/?r=nametag"));
                activity.startActivity(insta_page);

            }
        });
        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent twiter_page=new Intent("android.intent.action.VIEW",Uri.parse("https://twitter.com/briefxnews?s=08"));
                activity.startActivity(twiter_page);

            }
        });
        youtube.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent youtubepage=new Intent("android.intent.action.VIEW",Uri.parse("https://www.youtube.com/channel/UC0M6ijmZd9iI8SR_t_rOZQg"));
                activity.startActivity(youtubepage);

            }
        });

        if (back!=null) {
            back.setOnClickListener(v -> activity.onBackPressed());
        }





        getCartList(activity);
    }

    public void setToolbar(final Activity activity, String name){
        ImageView back = activity.findViewById(R.id.back);
        TextView tvName = activity.findViewById(R.id.tv_name);
        tvName.setText(name);
        back.setOnClickListener(v -> activity.onBackPressed());


//        getCartList(activity);
    }
    public void setToolbarBack(final Activity activity, String name, NewsModelArticle newsModelArticle){
        ImageView back = activity.findViewById(R.id.back);
        ImageView share_app = activity.findViewById(R.id.share_app);
        TextView tvName = activity.findViewById(R.id.tv_name);
        tvName.setText(name);

        back.setOnClickListener(v -> activity.onBackPressed());
        share_app.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsefullMethods.share_news(activity,newsModelArticle);

            }
        });


    }

    public void getCartList(final Activity activity) {

        class GetTasks extends AsyncTask<Void, Void, ArrayList<Task>> {

            @Override
            protected ArrayList<Task> doInBackground(Void... voids) {
                List<Task> tasks= DatabaseClient.getmInstance(activity.getApplicationContext())
                        .getAppDatabase()
                        .taskDao()
                        .getAll();
                return new ArrayList<>(tasks);
            }

            @Override
            protected void onPostExecute(ArrayList<Task> tasks) {
                int size = tasks!=null?tasks.size():0;
               /* TextView cartItemsCount = activity.findViewById(R.id.cartItemsCount);
                if (cartItemsCount != null) {
                    cartItemsCount.setText(String.format("%s", size));
                }*/
                if (!(activity instanceof  DashboardActivity)) {
                    try{
                        double sum = 0;
                        if (tasks != null) {
                            for (Task task: tasks) {
                                double price = Double.parseDouble("0"+(task.getCurrentprice()==null?"":task.getCurrentprice()));
                                double qty = Double.parseDouble("0"+task.getQuantity());
                                double total = price*qty;
                                sum+=total;
                            }
                        }

                        NumberFormat nf = NumberFormat.getInstance();
                        nf.setMaximumFractionDigits(2);
                      /*  TextView utility = activity.findViewById(R.id.utility);


                        if (utility!=null) {
                            utility.setText(String.format("%s %s", activity.getString(R.string.currency), nf.format(sum)));
                            utility.setVisibility(View.VISIBLE);
                        }*/

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
//                userWallet(activity);

            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }



    public void refresh(Activity activity) {
        Toolbar_Set.INSTANCE.getCartList(activity);
    }


    public void delete(final Activity activity, final Task task) {

        class GetTasks extends AsyncTask<Void, Void, ArrayList<Task>> {

            @Override
            protected ArrayList<Task> doInBackground(Void... voids) {
                        DatabaseClient.getmInstance(activity.getApplicationContext())
                        .getAppDatabase()
                        .taskDao()
                        .delete(task);
                return null;
            }

            @Override
            protected void onPostExecute(ArrayList<Task> tasks) {
            }
        }

        GetTasks gt = new GetTasks();
        gt.execute();
    }

    public void setBottomNav(final Activity activity, LinearLayout searchll, SearchView search) {
        final BottomNavigationView bottomNavigationView = activity.findViewById(R.id.bottomNavigationView);
/*
        BottomNavigationMenuView mbottomNavigationMenuView =
                (BottomNavigationMenuView) bottomNavigationView.getChildAt(0);

        View view = mbottomNavigationMenuView.getChildAt(3);*/

       // BottomNavigationItemView itemView = (BottomNavigationItemView) view;
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if (item.getItemId() == R.id.home) {
                    searchll.setVisibility(View.GONE);
                    if (!(activity instanceof DashboardActivity)) {
                    activity.startActivity(new Intent(activity, DashboardActivity.class));
                        activity.finishAffinity();
                    }

                }
                else if (item.getItemId() == R.id.category){
                    searchll.setVisibility(View.GONE);
                   Intent intent=new Intent(activity, YoutubeActivity.class);
                   activity.startActivity(intent);

                }
                else if (item.getItemId() == R.id.search){
                    searchll.setVisibility(View.VISIBLE);
//                    search.setFocusable(true);
//                    Toast.makeText(activity, "search", Toast.LENGTH_SHORT).show();

                }

                bottomNavigationView.setSelected(false);
                return false;
            }
        });
    }


//    public void userWallet(Activity activity) {
//
//        AppSharedPreferences preferences = new AppSharedPreferences(activity.getApplication());
//        if (preferences.getLoginUserLoginId() !=null ){
//            TextView utility = activity.findViewById(R.id.utility);
//            if (UtilMethods.INSTANCE.isNetworkAvialable(activity)) {
//                UtilMethods.INSTANCE.userWallet(activity, preferences.getLoginUserLoginId(), new mCallBackResponse() {
//                    @Override
//                    public void success(String from, String message) {
//
//                        AmountModel amountModel = new Gson().fromJson(message, AmountModel.class);
//
////                        if (utility!=null) {
////                            utility.setText(activity.getString(R.string.currency) + " "+ amountModel.getAmount());
////                            utility.setVisibility(View.VISIBLE);
////                        }
//
//                        if (activity instanceof WalletActivity) {
//                            TextView Wallet_Ammount = (TextView) activity.findViewById(R.id.wallet_ammount);
//                            Wallet_Ammount.setText(activity.getString(R.string.currency) + " "+ amountModel.getAmount());
//                        }
//                    }
//
//                    @Override
//                    public void fail(String from) {
//
//                    }
//                });
//            }
//        }
//    }
        }

