package com.smartwebarts.briefnx.newsdetail;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.newsdetail.model.NewsModelArticle;
import com.smartwebarts.briefnx.utils.Urls;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class NewsDetailAdapter extends RecyclerView.Adapter<NewsDetailAdapter.MYVIEWHolder> {
    private Activity activity;
    private ArrayList<NewsModelArticle> arrayList;
    private List<NewsModelArticle> newslist;
    public NewsDetailAdapter(Activity activity, ArrayList<NewsModelArticle> arrayList, List<NewsModelArticle> newslist) {
        this.activity=activity;
        this.arrayList=arrayList;
        this.newslist=newslist;
    }

    @NonNull
    @Override
    public MYVIEWHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.relatednews_itemview, parent, false);
        return new MYVIEWHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MYVIEWHolder holder, int position) {
        Glide.with(activity)
                .load(Urls.NEWS_IMGES+arrayList.get(position).getImage())
                .placeholder(R.drawable.img_not_found)
                .into(holder.img_thumbnail);
        String newscontent = Html.fromHtml(arrayList.get(position).getTitle()).toString();

        holder.title.setText(newscontent);
    }

    @Override
    public int getItemCount() {
        if(arrayList.size()>5)
        {
            return 5;
        }
        else  if(arrayList.size()<5)
        {
            return arrayList.size();
        }
       return 0;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MYVIEWHolder extends RecyclerView.ViewHolder {
     ImageView img_thumbnail;
     TextView title;
        public MYVIEWHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            img_thumbnail=itemView.findViewById(R.id.img_thumbnail);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent i=new Intent(activity, NewsDetailsActivity.class);
                    i.putExtra("newslist", (Serializable) newslist);
                    i.putExtra("position_id", arrayList.get(getAdapterPosition()).getId());
                    activity.startActivity(i);
                    activity.finish();
                }
            });
        }
    }
}
