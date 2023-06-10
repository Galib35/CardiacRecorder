package com.example.cardiacrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;
import java.util.Locale;

public class Update extends AppCompatActivity {
    EditText date,time,systolic,diastolic,heart,comment;
    Button update;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        date=findViewById(R.id.date_pick);
        time=findViewById(R.id.time_pick);
        systolic=findViewById(R.id.sys_edt);
        diastolic=findViewById(R.id.dia_edt);
        heart=findViewById(R.id.hr_edt);
        comment=findViewById(R.id.cmnt_edt);
        update=findViewById(R.id.update_btn);

        auth=FirebaseAuth.getInstance();

        date.addTextChangedListener(watcher);
        time.addTextChangedListener(watcher);
        systolic.addTextChangedListener(watcher);
        diastolic.addTextChangedListener(watcher);
        heart.addTextChangedListener(watcher);

        setData();

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(Update.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int selectedYear, int monthOfYear, int dayOfMonth) {
                                // Update the EditText with the selected date
                                String selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + selectedYear;
                                date.setText(selectedDate);
                            }
                        }, year, month, dayOfMonth);

                datePickerDialog.show();
            }

        });

        time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(Update.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                // Update the EditText with the selected time
                                String selectedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute);
                                time.setText(selectedTime);
                            }
                        }, hour, minute, false);

                timePickerDialog.show();
            }

        });

    }

    TextWatcher watcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String sys,dia,hr,datePick,timePick;
            sys=systolic.getText().toString().trim();
            dia=diastolic.getText().toString().trim();
            hr=heart.getText().toString().trim();

            datePick=date.getText().toString().trim();
            timePick=time.getText().toString().trim();
            update.setEnabled(!sys.isEmpty() && !dia.isEmpty() && !hr.isEmpty() && !datePick.isEmpty() &&!timePick.isEmpty());

        }
    };
    void setData()
    {
        int position=getIntent().getIntExtra("pos2",0);
        String sbpTxt,dbpTxt,hrTxt,dateTxt,timeTxt,cmnt;
        sbpTxt=Constant.tmp_sys.get(position);
        dbpTxt=Constant.tmp_dia.get(position);
        hrTxt=Constant.tmp_hr.get(position);
        dateTxt=Constant.tmp_date.get(position);
        timeTxt=Constant.tmp_time.get(position);
        cmnt=Constant.tmp_cmnt.get(position);

        systolic.setText(sbpTxt);
        diastolic.setText(dbpTxt);
        heart.setText(hrTxt);
        date.setText(dateTxt);
        time.setText(timeTxt);

        if(!cmnt.equals("null"))
        {
            comment.setText(cmnt);
        }



    }
}