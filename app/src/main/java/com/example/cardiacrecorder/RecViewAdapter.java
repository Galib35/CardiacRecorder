package com.example.cardiacrecorder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class RecViewAdapter  extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    Context context;

    public RecViewAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_design,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date,time,dbp,sbp,hr,edit,delete;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.item_date);
            time=itemView.findViewById(R.id.item_time);
            sbp=itemView.findViewById(R.id.item_sbp);
            dbp=itemView.findViewById(R.id.dbp_item);
            hr=itemView.findViewById(R.id.item_hr);
            edit=itemView.findViewById(R.id.edit_btn);
            delete=itemView.findViewById(R.id.delete_btn);
        }
    }
}
