package com.example.cardiacrecorder;

import androidx.annotation.NonNull;
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

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

/**
 * Activity class for collecting user information.
 */
public class InfoCollection extends AppCompatActivity {
    EditText date, time, systolic, diastolic, heart, comment;
    Button save;
    TextInputLayout sysLayout, diaLayout, hrLayout, cmntLayout, dateLayout, timeLayout;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info_collection);

        date = findViewById(R.id.date_pick);
        time = findViewById(R.id.time_pick);
        systolic = findViewById(R.id.sys_edt);
        diastolic = findViewById(R.id.dia_edt);
        heart = findViewById(R.id.hr_edt);
        comment = findViewById(R.id.cmnt_edt);
        save = findViewById(R.id.save_btn);

        auth = FirebaseAuth.getInstance();

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
                saveData();
            }
        });
    }

    /**
     * TextWatcher to enable/disable the save button based on the input fields.
     */
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String sys = systolic.getText().toString().trim();
            String dia = diastolic.getText().toString().trim();
            String hr = heart.getText().toString().trim();
            String datePick = date.getText().toString().trim();
            String timePick = time.getText().toString().trim();
            save.setEnabled(!sys.isEmpty() && !dia.isEmpty() && !hr.isEmpty() && !datePick.isEmpty() && !timePick.isEmpty());
        }

        @Override
        public void afterTextChanged(Editable editable) {
        }
    };

    /**
     * Saves the collected data to the database.
     */
    void saveData() {
        String sys = systolic.getText().toString().trim();
        String dia = diastolic.getText().toString().trim();
        String hr = heart.getText().toString().trim();
        String cmnt = comment.getText().toString().trim();
        String datePick = date.getText().toString().trim();
        String timePick = time.getText().toString().trim();

        if (cmnt.isEmpty()) {
            cmnt = "No Comment";
        }

        String userid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("User").child(userid).child("Daily Tracker");

        DataModel data = new DataModel(sys, dia, hr, datePick, timePick, cmnt);
        ref.push().setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(InfoCollection.this, "Info added", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(InfoCollection.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
