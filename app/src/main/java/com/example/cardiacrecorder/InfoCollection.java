package com.example.cardiacrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;

public class InfoCollection extends AppCompatActivity {
    EditText date,time,systolic,diastolic,heart,comment;
    Button save;
    TextInputLayout sysLayout,diaLayout,hrLayout,cmntLayout,dateLayout,timeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_collection);
        date=findViewById(R.id.date_pick);
        time=findViewById(R.id.time_pick);
        systolic=findViewById(R.id.sys_edt);
        diastolic=findViewById(R.id.dia_edt);
        heart=findViewById(R.id.hr_edt);
        comment=findViewById(R.id.cmnt_edt);
        save=findViewById(R.id.save_btn);


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(InfoCollection.this,
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

                TimePickerDialog timePickerDialog = new TimePickerDialog(InfoCollection.this,
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

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sys,dia,hr,cmnt,datePick,timePick;
                sys=systolic.getText().toString().trim();
                dia=diastolic.getText().toString().trim();
                hr=heart.getText().toString().trim();
                cmnt=comment.getText().toString().trim();
                datePick=date.getText().toString().trim();
                timePick=time.getText().toString().trim();

                if(sys.isEmpty())
                {
                    sysLayout.setError("This field can't be empty");
                }
                if(dia.isEmpty())
                {
                    diaLayout.setError("This field can't be empty");
                }
                if(hr.isEmpty())
                {
                    hrLayout.setError("This field can't be empty");
                }
                if(cmnt.isEmpty())
                {
                    cmntLayout.setError("This field can't be empty");
                }
                if(datePick.isEmpty())
                {
                    dateLayout.setError("This field can't be empty");
                }
                if(timePick.isEmpty())
                {
                    timeLayout.setError("This field can't be empty");
                }

                
            }
        });
    }
}