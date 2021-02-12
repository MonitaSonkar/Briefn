package com.smartwebarts.briefnx.dashboard.TabAdapter;

import android.app.Activity;
import android.content.Intent;
import android.text.Html;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.dashboard.headerrecylerview.SubArticleTopNews;
import com.smartwebarts.briefnx.newsdetail.NewsDetailsActivity;
import com.smartwebarts.briefnx.newsdetail.model.NewsModelArticle;
import com.smartwebarts.briefnx.newsdetail.model.SubscriptionModel;
import com.smartwebarts.briefnx.retrofit.UtilMethods;
import com.smartwebarts.briefnx.utils.Urls;
import com.smartwebarts.briefnx.utils.UsefullMethods;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

public class NewsSection extends Section {

    private final String title;
    private final ArrayList<NewsModelArticle> list;
    private final ClickListener clickListener;
    private Activity requireActivity;
    private String superCategoryId;

    NewsSection(Activity requireActivity, @NonNull final String title, @NonNull final ArrayList<NewsModelArticle> list,
                @NonNull final ClickListener clickListener, String superCategoryId) {

        super(SectionParameters.builder()
                .itemResourceId(R.layout.home_page_layout)
                .headerResourceId(R.layout.header_layout)
                .footerResourceId(R.layout.section_ex2_footer)
                .build());

        this.title = title;
        this.list = list;
        this.superCategoryId = superCategoryId;
        this.requireActivity = requireActivity;
        this.clickListener = clickListener;
    }

    @Override
    public int getContentItemsTotal() {
        return list.size();
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(final View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindItemViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ItemViewHolder itemHolder = (ItemViewHolder) holder;

        final NewsModelArticle news = list.get(position);

//        itemHolder.description_of_News.setText(news.getTitle());

        SpannableStringBuilder builder = new SpannableStringBuilder();
        String red = news.getDisplayTopic()+": ";
        SpannableString redSpannable= new SpannableString(red);
        redSpannable.setSpan(new ForegroundColorSpan(requireActivity.getResources().getColor(R.color.iconColor)), 0, red.length(), 0);
        builder.append(redSpannable);
        builder.append(Html.fromHtml(news.getTitle()));
        itemHolder.description_of_News.setText(builder, TextView.BufferType.SPANNABLE);
//        itemHolder.description_of_News.setText(news.getTitle());
        Glide.with(requireActivity)
                .load(Urls.NEWS_IMGES+news.getImage())
                .placeholder(R.drawable.img_not_found)
                .into(itemHolder.news);
        String newscontent = Html.fromHtml(news.getTitle()).toString();
        itemHolder.news_category.setText(newscontent);
        itemHolder.news_share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                UsefullMethods.share_news(requireActivity,news);


            }
        });
        itemHolder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(news.getPmtStatus().equalsIgnoreCase("0"))
                {
                    showSubscriptionDailog(UtilMethods.subscribtion_plans,news.getId());
                }
                else
                {
                    Intent i=new Intent(requireActivity, NewsDetailsActivity.class);
                    i.putExtra("newslist", (Serializable) list);
                    i.putExtra("position_id", news.getId());
                    requireActivity.startActivity(i);
                }
            }
        });

       /* itemHolder.rootView.setOnClickListener(v ->
                clickListener.onItemRootViewClicked(this, itemHolder.getAdapterPosition())
        );*/
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(final View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(final RecyclerView.ViewHolder holder) {
        final HeaderViewHolder headerHolder = (HeaderViewHolder) holder;

        headerHolder.tvName.setText(title);
    }

    @Override
    public RecyclerView.ViewHolder getFooterViewHolder(final View view) {
        return new FooterViewHolder(view);
    }

    @Override
    public void onBindFooterViewHolder(final RecyclerView.ViewHolder holder) {
        final FooterViewHolder footerHolder = (FooterViewHolder) holder;
//        footerHolder.rootView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                Toast.makeText("onBindFooterViewHolder", footerHolder.rootView.getTag(), Toast.LENGTH_SHORT).show();
//                Toast.makeText(requireActivity, "onBindFooterViewHolder::"+footerHolder.rootView.getTag(), Toast.LENGTH_SHORT).show();
//            }
//        });
        footerHolder.rootView.setOnClickListener(v -> clickListener.onFooterRootViewClicked(this, String.valueOf(footerHolder.rootView.getTag())));
    }

    interface ClickListener {

        void onItemRootViewClicked(@NonNull final NewsSection section, final int itemAdapterPosition);

        void onFooterRootViewClicked(@NonNull final NewsSection section, final String itemAdapterPosition);
    }

    private class FooterViewHolder extends RecyclerView.ViewHolder{
        final View rootView;
        public FooterViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            rootView.setTag(superCategoryId);
        }
    }

    private class HeaderViewHolder extends RecyclerView.ViewHolder{
//        final View rootView;
        TextView tvName;
        public HeaderViewHolder(@NonNull View itemView) {
            super(itemView);
            this.tvName = (TextView)itemView.findViewById(R.id.tvName);
        }
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder{
        //        final View rootView;
        final View rootView;
        TextView description_of_News,like,comment,news_category,description;
        ImageView news;
        LinearLayout news_share;
        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            rootView = itemView;
            description_of_News=itemView.findViewById(R.id.description_of_News);
            description=itemView.findViewById(R.id.description);
            news_category=itemView.findViewById(R.id.news_category);
            like=itemView.findViewById(R.id.news_like);
            news_share=itemView.findViewById(R.id.news_share);
            comment=itemView.findViewById(R.id.comment);
            news=itemView.findViewById(R.id.news_image);
        }
    }

    private void showSubscriptionDailog(List<SubscriptionModel> package_list, String adapterPosition) {

        Intent i= new Intent(requireActivity, SubscribtionDailog.class);
        i.putExtra("Subscription", (Serializable) package_list);
        i.putExtra("newslist", (Serializable) list);
        i.putExtra("position_id", adapterPosition);
        requireActivity.startActivity(i);

    }
}
