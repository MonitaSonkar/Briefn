package com.smartwebarts.briefnx.adapters;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.smartwebarts.briefnx.BuildConfig;
import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.dashboard.TabAdapter.SubscribtionDailog;
import com.smartwebarts.briefnx.dashboard.viewmodel.NewsViewModel;
import com.smartwebarts.briefnx.newsdetail.NewsDetailsActivity;
import com.smartwebarts.briefnx.newsdetail.model.NewsModelArticle;
import com.smartwebarts.briefnx.newsdetail.model.PaymentArticleModel;
import com.smartwebarts.briefnx.newsdetail.model.SubscriptionModel;
import com.smartwebarts.briefnx.retrofit.UtilMethods;
import com.smartwebarts.briefnx.retrofit.mCallBackResponse;
import com.smartwebarts.briefnx.utils.Urls;
import com.smartwebarts.briefnx.utils.UsefullMethods;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class CustomeAdapter extends RecyclerView.Adapter<CustomeAdapter.MYviewHolder> {
    private List<NewsModelArticle>list;
    FragmentActivity context;
    private NewsViewModel viewModel;

    public CustomeAdapter(FragmentActivity context, List<NewsModelArticle> list, NewsViewModel viewModel) {
        this.context = context;
        this.list=list;
        this.viewModel=viewModel;
    }
    @NonNull
    @Override
    public MYviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MYviewHolder(LayoutInflater.from(context).inflate(R.layout.home_page_layout, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MYviewHolder holder, int position) {
        SpannableStringBuilder builder = new SpannableStringBuilder();
        String red = list.get(position).getDisplayTopic()+": ";
        SpannableString redSpannable= new SpannableString(red);
        redSpannable.setSpan(new ForegroundColorSpan(context.getResources().getColor(R.color.iconColor)), 0, red.length(), 0);
        builder.append(redSpannable);
        builder.append(Html.fromHtml(list.get(position).getTitle()));
        holder.description_of_News.setText(builder, TextView.BufferType.SPANNABLE);
//       holder.description_of_News.setText(list.get(position).getTitle());
        Glide.with(context)
                .load(Urls.NEWS_IMGES+list.get(position).getImage())
                .placeholder(R.drawable.img_not_found)
                .into(holder.news);
        String newscontent = Html.fromHtml(list.get(position).getTitle()).toString();
        holder.news_category.setText(newscontent);
        Log.e("News Fragment==", "CustomerAdapter: "+position);

        holder.news_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UsefullMethods.share_news(context,list.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();

    }

    public void setData(List<NewsModelArticle> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public class MYviewHolder extends RecyclerView.ViewHolder {
        TextView description_of_News,like,comment,news_category,description;
        ImageView news;
        LinearLayout news_share;
        Uri uri;

        public MYviewHolder(@NonNull View itemView) {
            super(itemView);
            description_of_News=itemView.findViewById(R.id.description_of_News);
            description=itemView.findViewById(R.id.description);
            news_category=itemView.findViewById(R.id.news_category);
            like=itemView.findViewById(R.id.news_like);
            news_share=itemView.findViewById(R.id.news_share);
            comment=itemView.findViewById(R.id.comment);
            news=itemView.findViewById(R.id.news_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(list.get(getAdapterPosition()).getPmtStatus().equalsIgnoreCase("0"))
                    {
                        showSubscriptionDailog(UtilMethods.subscribtion_plans,list.get(getAdapterPosition()).getId());
                    }
                    else
                    {
                        Intent i=new Intent(context, NewsDetailsActivity.class);
                        i.putExtra("newslist", (Serializable) list);
                        i.putExtra("position_id", list.get(getAdapterPosition()).getId());
                        context.startActivity(i);
                    }


                }
            });
        }
    }


    private void getPackages(Activity context) {

        if (UtilMethods.INSTANCE.isNetworkAvialable(context)) {
            UtilMethods.INSTANCE.getPackageCall(context,"packeges",new mCallBackResponse() {
                @Override
                public void success(String from, String message) {
                    Type type = new TypeToken<List<SubscriptionModel>>() {
                    }.getType();
                    List<SubscriptionModel> list = new Gson().fromJson(message, type);
//                    showSubscriptionDailog(list, getAdapterPosition());
//                    setAdapter(list,paymentArticleModel);
                }

                @Override
                public void fail(String from) {
                    UsefullMethods.showMessage(context, SweetAlertDialog.ERROR_TYPE, "Error", from, "OK", () -> {

                    });
                }
            });

        } else {
            UtilMethods.INSTANCE.internetNotAvailableMessage(context);
        }
    }

    private void showSubscriptionDailog(List<SubscriptionModel> package_list, String adapterPosition) {

        Intent i= new Intent(context, SubscribtionDailog.class);
        i.putExtra("Subscription", (Serializable) package_list);
        i.putExtra("newslist", (Serializable) list);
        i.putExtra("position_id", adapterPosition);
        context.startActivity(i);

    }
}
