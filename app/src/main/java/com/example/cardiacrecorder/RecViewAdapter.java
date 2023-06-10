package com.example.cardiacrecorder;

import android.annotation.SuppressLint;
import android.content.Context;
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

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        String sys,dia,hr,cmnt,datePick,timePick;
        sys=Constant.tmp_sys.get(position)+" mm Hg";
        dia=Constant.tmp_dia.get(position)+" mm Hg";
        hr=Constant.tmp_hr.get(position)+" bpm";
        cmnt=Constant.tmp_cmnt.get(position);
        datePick=Constant.tmp_date.get(position);
        timePick=Constant.tmp_time.get(position);

        ((MyViewHolder)holder).date.setText(datePick);
        ((MyViewHolder)holder).time.setText(timePick);
        ((MyViewHolder)holder).sbp.setText(sys);
        ((MyViewHolder)holder).dbp.setText(dia);
        ((MyViewHolder)holder).hr.setText(hr);

        int sysRate,diaRate,hrRate;
        sysRate=Integer.parseInt(Constant.tmp_sys.get(position));
        diaRate=Integer.parseInt(Constant.tmp_dia.get(position));
        hrRate=Integer.parseInt(Constant.tmp_hr.get(position));

        //int newColor = getResources().getColor(R.color.text);
        //System.out.println(sysRate);
        if(sysRate>=90 && sysRate<=140)
        {

            ((MyViewHolder)holder).sbpLayout.setBackgroundColor(context.getResources().getColor(R.color.text));
        }
        else if(sysRate<90)
        {
            ((MyViewHolder)holder).sbpLayout.setBackgroundColor(context.getResources().getColor(R.color.low));
        }
        else {
            ((MyViewHolder)holder).sbpLayout.setBackgroundColor(context.getResources().getColor(R.color.high));
        }

        if(diaRate>=60 && diaRate<=90)
        {
            ((MyViewHolder)holder).dbpLayout.setBackgroundColor(context.getResources().getColor(R.color.text));
        }
        else if(diaRate<60)
        {
            ((MyViewHolder)holder).dbpLayout.setBackgroundColor(context.getResources().getColor(R.color.low));
        }
        else {
            ((MyViewHolder)holder).dbpLayout.setBackgroundColor(context.getResources().getColor(R.color.high));
        }

        if(hrRate>=60 && hrRate<=100)
        {
            ((MyViewHolder)holder).hrLayout.setBackgroundColor(context.getResources().getColor(R.color.text));
        }
        else if(hrRate<60)
        {
            ((MyViewHolder)holder).hrLayout.setBackgroundColor(context.getResources().getColor(R.color.low));
        }
        else {
            ((MyViewHolder)holder).hrLayout.setBackgroundColor(context.getResources().getColor(R.color.high));
        }




    }

    @Override
    public int getItemCount() {
        return Constant.key.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView date,time,dbp,sbp,hr;
        ImageView edit,delete;
        LinearLayout sbpLayout,dbpLayout,hrLayout;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            date=itemView.findViewById(R.id.item_date);
            time=itemView.findViewById(R.id.item_time);
            sbp=itemView.findViewById(R.id.item_sbp);
            dbp=itemView.findViewById(R.id.dbp_item);
            hr=itemView.findViewById(R.id.item_hr);
            edit=itemView.findViewById(R.id.edit_btn);
            delete=itemView.findViewById(R.id.delete_btn);
            sbpLayout=itemView.findViewById(R.id.sbp_layout);
            dbpLayout=itemView.findViewById(R.id.dbp_layout);
            hrLayout=itemView.findViewById(R.id.heart_layout);
        }
    }
}
