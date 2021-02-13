package com.smartwebarts.briefnx.adapters;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.models.YoutubeItemsModels;
import com.smartwebarts.briefnx.models.YoutubeMainModel;
import com.smartwebarts.briefnx.utils.Urls;
import com.smartwebarts.briefnx.youtube.BaseViewHolder;
import com.smartwebarts.briefnx.youtube.youtubedetail.YoutubeVedioViewActivity;

import java.util.List;

public class YoutubeAdapter  extends RecyclerView.Adapter<YoutubeAdapter.MYviewHolder> {
  /*  private static final int VIEW_TYPE_LOADING = 0;
    private static final int VIEW_TYPE_NORMAL = 1;
    private boolean isLoaderVisible = false;
      Context context;
    List<YoutubeItemsModels>youtubeMainModels;

    public YoutubeAdapter(Context context, List<YoutubeItemsModels> youtubeMainModels) {
        this.context = context;
        this.youtubeMainModels = youtubeMainModels;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case VIEW_TYPE_NORMAL:
                return new ViewHolder(LayoutInflater.from(context).inflate(R.layout.youtube_item_layout, parent, false));
            case VIEW_TYPE_LOADING:
                return new ProgressHolder(
                        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_loading, parent, false));
            default:
                return null;
        }
    }
    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        holder.onBind(position);
    }

    @Override
    public int getItemCount() {
        return youtubeMainModels==null?0:youtubeMainModels.size();
    }

    public void addItems(List<YoutubeItemsModels> postItems) {
       // youtubeMainModels.addAll(postItems);
        notifyDataSetChanged();
    }
    public void addLoading() {
        isLoaderVisible = true;

       // youtubeMainModels.add(new YoutubeItemsModels());
       // notifyItemInserted(youtubeMainModels.size() - 1);
    }
    public void removeLoading() {
        isLoaderVisible = false;
        int position = youtubeMainModels.size() - 1;
        YoutubeItemsModels item = youtubeMainModels.get(position);
        if (item != null) {
            youtubeMainModels.remove(position);
            notifyItemRemoved(position);
        }
    }
    public void clear() {
        youtubeMainModels.clear();
        notifyDataSetChanged();
    }
    YoutubeItemsModels getItem(int position) {
        return youtubeMainModels.get(position);
    }
    public class ViewHolder extends BaseViewHolder {
        ImageView thumbnails;
        TextView youtube_title,youtube_description,youtube_time;

        ViewHolder(View itemView) {
            super(itemView);

            thumbnails=itemView.findViewById(R.id.thumbnails);
            youtube_title=itemView.findViewById(R.id.youtube_title);
            youtube_description=itemView.findViewById(R.id.youtube_description);
            youtube_time=itemView.findViewById(R.id.youtube_time);

        }
        protected void clear() {

        }
        public void onBind(int position) {
            super.onBind(position);
            YoutubeItemsModels item = youtubeMainModels.get(position);
            youtube_title.setText(item.getSnippet().getTitle());
            youtube_description.setText(item.getSnippet().getDescription());
            Glide.with(context)
                    .load(youtubeMainModels.get(position).getSnippet().getThumbnails().getDefault().getUrl())
                    .placeholder(R.drawable.img_not_found)
                    .into(thumbnails);
        }
    }
    public class ProgressHolder extends BaseViewHolder {
        ProgressHolder(View itemView) {
            super(itemView);
            //ButterKnife.bind(this, itemView);
        }
        @Override
        protected void clear() {
        }
    }*/
    Context context;
    List<YoutubeItemsModels>youtubeMainModels;

    public YoutubeAdapter(Context context, List<YoutubeItemsModels> youtubeMainModels) {
        this.context = context;
        this.youtubeMainModels = youtubeMainModels;
    }

    @NonNull
    @Override
    public MYviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new MYviewHolder(LayoutInflater.from(context).inflate(R.layout.youtube_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MYviewHolder holder, int position) {
        Glide.with(context)
                .load(youtubeMainModels.get(position).getSnippet().getThumbnails().getDefault().getUrl())
                .placeholder(R.drawable.img_not_found)
                .into(holder.thumbnails);
        String new_str="";
        new_str = youtubeMainModels.get(position).getSnippet().getTitle();
        // Get a substring of the above string starting from
        // index 0 and ending at index 100.

        if(youtubeMainModels.get(position).getSnippet().getTitle().length()>=20)
        {
             new_str = youtubeMainModels.get(position).getSnippet().getTitle().substring(0, 20);
        }


        holder.youtube_title.setText(new_str/*youtubeMainModels.get(position).getSnippet().getTitle()*/);

        holder.youtube_time.setText(youtubeMainModels.get(position).getSnippet().getPublishTime());

    }

    @Override
    public int getItemCount() {
        return youtubeMainModels==null?0:youtubeMainModels.size();
    }

    public void setData(List<YoutubeItemsModels> youtubeMainModels) {
        this.youtubeMainModels = youtubeMainModels;
        notifyDataSetChanged();
    }
    public class MYviewHolder extends RecyclerView.ViewHolder {
        ImageView thumbnails;
        TextView youtube_title,youtube_description,youtube_time;

        public MYviewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnails=itemView.findViewById(R.id.thumbnails);
            youtube_title=itemView.findViewById(R.id.youtube_title);
            youtube_description=itemView.findViewById(R.id.youtube_description);
            youtube_time=itemView.findViewById(R.id.youtube_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(youtubeMainModels.get(getAdapterPosition()).getId().getVideoId()!=null)
                    {
                        Intent i=new Intent(context, YoutubeVedioViewActivity.class);
                        i.putExtra("videoId",youtubeMainModels.get(getAdapterPosition()).getId().getVideoId());
                        context.startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(context, "There is no video", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }
}
