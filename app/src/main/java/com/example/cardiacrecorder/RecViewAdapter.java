package com.example.cardiacrecorder;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * RecyclerView adapter for displaying data in a list format.
 */
public class RecViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    Context context;
    Constant.OnItemDeleteListener deleteListener;
    ArrayList<DataModel> dataList;

    public RecViewAdapter(Context context, Constant.OnItemDeleteListener deleteListener, ArrayList<DataModel> dataList) {
        this.context = context;
        this.deleteListener = deleteListener;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_design, parent, false);
        return new MyViewHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        String sys, dia, hr, cmnt, datePick, timePick;
        DataModel data = dataList.get(position);

        sys = data.getSystolic() + " mm Hg";
        dia = data.getDiastolic() + " mm Hg";
        hr = data.getHeart_rate() + " bpm";
        cmnt = data.getComment();
        datePick = data.getDate();
        timePick = data.getTime();

        // Add data to access further
        Constant.tmp_sys.add(data.getSystolic());
        Constant.tmp_dia.add(data.getDiastolic());
        Constant.tmp_cmnt.add(data.getComment());
        Constant.tmp_date.add(data.getDate());
        Constant.tmp_time.add(data.getTime());
        Constant.tmp_hr.add(data.getHeart_rate());

        ((MyViewHolder) holder).date.setText(datePick);
        ((MyViewHolder) holder).time.setText(timePick);
        ((MyViewHolder) holder).sbp.setText(sys);
        ((MyViewHolder) holder).dbp.setText(dia);
        ((MyViewHolder) holder).hr.setText(hr);

        int sysRate, diaRate, hrRate;
        sysRate = Integer.parseInt(data.getSystolic());
        diaRate = Integer.parseInt(data.getDiastolic());
        hrRate = Integer.parseInt(data.getHeart_rate());

        if (sysRate >= 90 && sysRate <= 140) {
            ((MyViewHolder) holder).sbpLayout.setBackgroundColor(context.getResources().getColor(R.color.text));
        } else if (sysRate < 90) {
            ((MyViewHolder) holder).sbpLayout.setBackgroundColor(context.getResources().getColor(R.color.low));
        } else {
            ((MyViewHolder) holder).sbpLayout.setBackgroundColor(context.getResources().getColor(R.color.high));
        }

        if (diaRate >= 60 && diaRate <= 90) {
            ((MyViewHolder) holder).dbpLayout.setBackgroundColor(context.getResources().getColor(R.color.text));
        } else if (diaRate < 60) {
            ((MyViewHolder) holder).dbpLayout.setBackgroundColor(context.getResources().getColor(R.color.low));
        } else {
            ((MyViewHolder) holder).dbpLayout.setBackgroundColor(context.getResources().getColor(R.color.high));
        }

        if (hrRate >= 60 && hrRate <= 100) {
            ((MyViewHolder) holder).hrLayout.setBackgroundColor(context.getResources().getColor(R.color.text));
        } else if (hrRate < 60) {
            ((MyViewHolder) holder).hrLayout.setBackgroundColor(context.getResources().getColor(R.color.low));
        } else {
            ((MyViewHolder) holder).hrLayout.setBackgroundColor(context.getResources().getColor(R.color.high));
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Details.class);
                intent.putExtra("pos", position);
                context.startActivity(intent);
            }
        });

        ((MyViewHolder) holder).edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, Update.class);
                intent.putExtra("pos2", position);
                context.startActivity(intent);
            }
        });

        ((MyViewHolder) holder).delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (deleteListener != null) {
                    deleteListener.onItemDelete(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView date, time, dbp, sbp, hr;
        ImageView edit, delete;
        LinearLayout sbpLayout, dbpLayout, hrLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.item_date);
            time = itemView.findViewById(R.id.item_time);
            sbp = itemView.findViewById(R.id.item_sbp);
            dbp = itemView.findViewById(R.id.dbp_item);
            hr = itemView.findViewById(R.id.item_hr);
            edit = itemView.findViewById(R.id.edit_btn);
            delete = itemView.findViewById(R.id.delete_btn);
            sbpLayout = itemView.findViewById(R.id.sbp_layout);
            dbpLayout = itemView.findViewById(R.id.dbp_layout);
            hrLayout = itemView.findViewById(R.id.heart_layout);
        }
    }
}
