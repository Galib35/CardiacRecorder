package com.example.cardiacrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.TextView;

public class Details extends AppCompatActivity {
    TextView sbp,dbp,hr,cmnt,date,time,sbpInd,dbpInd,hrInd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        sbp=findViewById(R.id.exp_sbp);
        dbp=findViewById(R.id.exp_dbp);
        hr=findViewById(R.id.exp_hr);
        cmnt=findViewById(R.id.exp_cmnt);
        date=findViewById(R.id.exp_date);
        time=findViewById(R.id.exp_time);
        sbpInd=findViewById(R.id.sbp_ind);
        dbpInd=findViewById(R.id.dbp_ind);
        hrInd=findViewById(R.id.hr_ind);

        setData();

    }
    @SuppressLint("SetTextI18n")
    void setData(){
        int position=getIntent().getIntExtra("pos",0);
        String sbpTxt,dbpTxt,hrTxt,dateTxt,timeTxt,cm;
        sbpTxt=Constant.tmp_sys.get(position)+" mm Hg";
        dbpTxt=Constant.tmp_dia.get(position)+" mm Hg";
        hrTxt=Constant.tmp_hr.get(position)+" bpm";
        dateTxt=Constant.tmp_date.get(position);
        timeTxt=Constant.tmp_time.get(position);
        cm=Constant.tmp_cmnt.get(position);

        int sysRate,diaRate,hrRate;
        sysRate=Integer.parseInt(Constant.tmp_sys.get(position));
        diaRate=Integer.parseInt(Constant.tmp_dia.get(position));
        hrRate=Integer.parseInt(Constant.tmp_hr.get(position));

        sbp.setText(sbpTxt);
        dbp.setText(dbpTxt);
        hr.setText(hrTxt);
        date.setText(dateTxt);
        time.setText(timeTxt);
        if(!cm.equals("null"))
        {
            cmnt.setText(cm);
        }

        if(sysRate>=90 && sysRate<=140)
        {
            sbpInd.setText("Normal");
            sbpInd.setBackgroundColor(getResources().getColor(R.color.text));
        }
        else if(sysRate<90)
        {
            sbpInd.setText("Low");
            sbpInd.setBackgroundColor(getResources().getColor(R.color.low));
        }
        else {
            sbpInd.setText("High");
            sbpInd.setBackgroundColor(getResources().getColor(R.color.high));
        }

        if(diaRate>=60 && diaRate<=90)
        {
            dbpInd.setText("Normal");
            dbpInd.setBackgroundColor(getResources().getColor(R.color.text));
        }
        else if(diaRate<90)
        {
            dbpInd.setText("Low");
            dbpInd.setBackgroundColor(getResources().getColor(R.color.low));
        }
        else {
            dbpInd.setText("High");
            dbpInd.setBackgroundColor(getResources().getColor(R.color.high));
        }

        if(hrRate>=60 && hrRate<=100)
        {
            hrInd.setText("Normal");
            hrInd.setBackgroundColor(getResources().getColor(R.color.text));
        }
        else if(hrRate<90)
        {
            hrInd.setText("Low");
            hrInd.setBackgroundColor(getResources().getColor(R.color.low));
        }
        else {
            hrInd.setText("High");
            hrInd.setBackgroundColor(getResources().getColor(R.color.high));
        }
    }
}