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
import android.widget.Toast;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

public class InfoCollection extends AppCompatActivity {
    EditText date,time,systolic,diastolic,heart,comment;
    Button save;
    TextInputLayout sysLayout,diaLayout,hrLayout,cmntLayout,dateLayout,timeLayout;
    FirebaseAuth auth;


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

        auth=FirebaseAuth.getInstance();


        date.addTextChangedListener(watcher);
        time.addTextChangedListener(watcher);
        systolic.addTextChangedListener(watcher);
        diastolic.addTextChangedListener(watcher);
        heart.addTextChangedListener(watcher);
        comment.addTextChangedListener(watcher);

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



                DatabaseReference ref=FirebaseDatabase.getInstance().getReference()
                        .child("User").child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("Daily Tracker");

                String key = ref.push().getKey();
                assert key != null;
                ref.child(key).child("Systolic").setValue(sys);
                ref.child(key).child("Diastolic").setValue(dia);
                ref.child(key).child("Heart Rate").setValue(hr);
                ref.child(key).child("Comment").setValue(cmnt);
                ref.child(key).child("Date").setValue(datePick);
                ref.child(key).child("Time").setValue(timePick);

               /* Constant.key.add(key);
                Constant.tmp_sys.add(sys);
                Constant.tmp_dia.add(dia);
                Constant.tmp_hr.add(hr);
                Constant.tmp_cmnt.add(cmnt);
                Constant.tmp_date.add(datePick);
                Constant.tmp_time.add(timePick);*/

                Toast.makeText(InfoCollection.this, "Info added", Toast.LENGTH_SHORT).show();
            }
        });
    }
    TextWatcher watcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String sys,dia,hr,cmnt,datePick,timePick;
            sys=systolic.getText().toString().trim();
            dia=diastolic.getText().toString().trim();
            hr=heart.getText().toString().trim();
            cmnt=comment.getText().toString().trim();
            datePick=date.getText().toString().trim();
            timePick=time.getText().toString().trim();
            save.setEnabled(!sys.isEmpty() && !dia.isEmpty() && !hr.isEmpty() && !cmnt.isEmpty() && !datePick.isEmpty() &&!timePick.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };
}