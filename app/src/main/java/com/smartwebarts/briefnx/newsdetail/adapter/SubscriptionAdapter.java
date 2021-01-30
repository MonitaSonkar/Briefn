package com.smartwebarts.briefnx.newsdetail.adapter;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.smartwebarts.briefnx.R;
import com.smartwebarts.briefnx.dashboard.TabAdapter.SubscribtionDailog;
import com.smartwebarts.briefnx.newsdetail.model.SubscriptionModel;

import java.util.List;

public class SubscriptionAdapter extends RecyclerView.Adapter<SubscriptionAdapter.MyViewModel> {
    private Activity context;
    private List<SubscriptionModel> list;
    public static int row_id=-1;

    public SubscriptionAdapter(Activity context, List<SubscriptionModel> list) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SubscriptionAdapter.MyViewModel onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.subscription_itemview, parent, false);
        return new MyViewModel(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SubscriptionAdapter.MyViewModel holder, int position) {
        holder.plan_name.setText(list.get(position).getName());
        holder.plan_amout.setText("Rs. " + list.get(position).getPrice());
        holder.duration.setText(list.get(position).getTimePeriod() + " year");


        Log.e("onBindViewHolder", "row_id : " + row_id + " pos " + position );

        if (row_id == position) {
            holder.planSelected.setChecked(true);
            list.get(position).setSelected(true);
            ((SubscribtionDailog)context).list.get(position).setSelected(true);
        } else {
            list.get(position).setSelected(false);
            ((SubscribtionDailog)context).list.get(position).setSelected(false);
            holder.planSelected.setChecked(false);
        }

        holder.planSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                row_id = position;
//                ((SubscribtionDailog)context).li
                notifyDataSetChanged();
            }
        });



    }





    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public class MyViewModel extends RecyclerView.ViewHolder {
        RadioButton planSelected;
        AppCompatTextView plan_amout, plan_name, duration;

        public MyViewModel(@NonNull View itemView) {
            super(itemView);
            planSelected = itemView.findViewById(R.id.planSelected);
            plan_name = itemView.findViewById(R.id.plan_name);
            plan_amout = itemView.findViewById(R.id.plan_amout);
            duration = itemView.findViewById(R.id.duration);
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
        //return super.getItemId(position);
    }
}
