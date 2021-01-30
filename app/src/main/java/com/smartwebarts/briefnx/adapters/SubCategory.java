package com.smartwebarts.briefnx.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.dashboard.DashboardActivity;
import com.smartwebarts.briefnx.models.NewsByCategoryModel;

import java.util.List;

public class SubCategory extends RecyclerView.Adapter<SubCategory.MYviewHolder> {
    Context context;
   List<NewsByCategoryModel>cotegoryModels;
    int category=0;
    String pos;

    public SubCategory(Context context, List<NewsByCategoryModel> cotegoryModels) {
        this.context = context;
        this.cotegoryModels = cotegoryModels;
    }


    @NonNull
    @Override
    public MYviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MYviewHolder(LayoutInflater.from(context).inflate(R.layout.category_item_layout, parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull MYviewHolder holder, int position) {

        if(category==position)
        {
            holder.view1.setVisibility(View.VISIBLE);
        }
        else {
            holder.view1.setVisibility(View.GONE);
        }
       holder.title.setText(cotegoryModels.get(position).getTitle());
        pos = cotegoryModels.get(position).getId();


    }

    @Override
    public int getItemCount() {
       return cotegoryModels==null?0:cotegoryModels.size();
    }

    public class MYviewHolder extends RecyclerView.ViewHolder {
        TextView title;
        View view1;
        public MYviewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.textView7);
            view1=itemView.findViewById(R.id.view1);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                   /* category=getAdapterPosition();
                    notifyDataSetChanged();*/
                    // get position
                    String post1 =cotegoryModels.get(getAdapterPosition()).getId();
//                    String post=cotegoryModels.get(Integer.parseInt(post1)).getId();
                    Log.e("Deepika===", "onClick: "+post1);
//                    ((DashboardActivity)context).listener.onClick(post1, cotegoryModels.get(getAdapterPosition()).getSuperCategoryId());
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }
}


