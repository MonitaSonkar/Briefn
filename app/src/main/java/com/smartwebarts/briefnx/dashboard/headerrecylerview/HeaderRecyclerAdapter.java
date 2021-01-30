package com.smartwebarts.briefnx.dashboard.headerrecylerview;

import android.app.Activity;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.utils.Urls;
import com.smartwebarts.briefnx.utils.UsefullMethods;

import java.util.ArrayList;

public class HeaderRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Activity requireActivity;
    ArrayList<ListItem> topnewscontent;
    public HeaderRecyclerAdapter(Activity requireActivity, ArrayList<ListItem> topnewscontent) {
        this.requireActivity=requireActivity;
        this.topnewscontent=topnewscontent;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == ListItem.TYPE_HEADER) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_layout, parent, false);
            return  new VHHeader(view);
        } else if(viewType == ListItem.TYPE_ITEM) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_page_layout, parent, false);
            return new VHItem(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof VHHeader) {
            HeaderModel header = (HeaderModel) topnewscontent.get(position);
            VHHeader VHheader = (VHHeader)holder;
            VHheader.tvName.setText(header.getName());
        }
        else if(holder instanceof VHItem) {
            Items person =  (Items) topnewscontent.get(position);
            VHItem VHitem = (VHItem)holder;

            VHitem.description_of_News.setText(person.getNews().getTitle());

            VHitem.description_of_News.setText(person.getNews().getTitle());
            Glide.with(requireActivity)
                    .load(Urls.NEWS_IMGES+person.getNews().getImage())
                    .placeholder(R.drawable.img_not_found)
                    .into(VHitem.news);
            String newscontent = Html.fromHtml(person.getNews().getTitle()).toString();
            VHitem.news_category.setText(newscontent);
            Log.e("News Fragment==", "CustomerAdapter: "+position);

            VHitem.news_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    UsefullMethods.share_news(requireActivity,list.get(position));


                }
            });
        }
    }



    @Override
    public int getItemCount() {
        return topnewscontent.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    class VHHeader extends RecyclerView.ViewHolder{
        TextView tvName;

        public VHHeader(View itemView) {
            super(itemView);
            this.tvName = (TextView)itemView.findViewById(R.id.tvName);
        }
    }

    class VHItem extends RecyclerView.ViewHolder{
        TextView description_of_News,like,comment,news_category,description;
        ImageView news;
        LinearLayout news_share;
        public VHItem(View itemView) {
            super(itemView);
            description_of_News=itemView.findViewById(R.id.description_of_News);
            description=itemView.findViewById(R.id.description);
            news_category=itemView.findViewById(R.id.news_category);
            like=itemView.findViewById(R.id.news_like);
            news_share=itemView.findViewById(R.id.news_share);
            comment=itemView.findViewById(R.id.comment);
            news=itemView.findViewById(R.id.news_image);
        }
    }

}
