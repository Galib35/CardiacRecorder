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
 * The Update class represents the activity for updating the user's cardiac records.
 * It allows the user to enter and save information such as systolic and diastolic readings,
 * heart rate, date, time, and optional comments.
 */
public class Update extends AppCompatActivity {
    EditText date, time, systolic, diastolic, heart, comment;
    Button update;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        date = findViewById(R.id.date_pick);
        time = findViewById(R.id.time_pick);
        systolic = findViewById(R.id.sys_edt);
        diastolic = findViewById(R.id.dia_edt);
        heart = findViewById(R.id.hr_edt);
        comment = findViewById(R.id.cmnt_edt);
        update = findViewById(R.id.update_btn);

        auth = FirebaseAuth.getInstance();

        date.addTextChangedListener(watcher);
        time.addTextChangedListener(watcher);
        systolic.addTextChangedListener(watcher);
        diastolic.addTextChangedListener(watcher);
        heart.addTextChangedListener(watcher);

        setData();

        /**
         * Handles the click event for the date EditText.
         * Shows a DatePickerDialog to select the date.
         */
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

        /**
         * Handles the click event for the time EditText.
         * Shows a TimePickerDialog to select the time.
         */
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

        /**
         * Handles the click event for the update button.
         * Saves the entered data to the Firebase database.
         */
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    /**
     * TextWatcher for monitoring changes in the input fields.
     * Enables the update button when all required fields have been filled.
     */
    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            String sys, dia, hr, datePick, timePick;
            sys = systolic.getText().toString().trim();
            dia = diastolic.getText().toString().trim();
            hr = heart.getText().toString().trim();

            datePick = date.getText().toString().trim();
            timePick = time.getText().toString().trim();
            update.setEnabled(!sys.isEmpty() && !dia.isEmpty() && !hr.isEmpty() && !datePick.isEmpty() && !timePick.isEmpty());
        }
    };

    /**
     * Sets the data for editing existing records.
     * Retrieves the selected record position and populates the input fields with the corresponding data.
     */
    void setData() {
        int position = getIntent().getIntExtra("pos2", 0);
        String sbpTxt, dbpTxt, hrTxt, dateTxt, timeTxt, cmnt;
        sbpTxt = Constant.tmp_sys.get(position);
        dbpTxt = Constant.tmp_dia.get(position);
        hrTxt = Constant.tmp_hr.get(position);
        dateTxt = Constant.tmp_date.get(position);
        timeTxt = Constant.tmp_time.get(position);
        cmnt = Constant.tmp_cmnt.get(position);

        systolic.setText(sbpTxt);
        diastolic.setText(dbpTxt);
        heart.setText(hrTxt);
        date.setText(dateTxt);
        time.setText(timeTxt);

        if (!cmnt.equals("No Comment")) {
            comment.setText(cmnt);
        }
    }

    /**
     * Saves the entered data to the Firebase database.
     * Retrieves the selected record position and updates the corresponding data in the database.
     */
    void saveData() {
        int position = getIntent().getIntExtra("pos2", 0);
        String sys, dia, hr, cmnt, datePick, timePick;
        sys = systolic.getText().toString().trim();
        dia = diastolic.getText().toString().trim();
        hr = heart.getText().toString().trim();
        cmnt = comment.getText().toString().trim();
        datePick = date.getText().toString().trim();
        timePick = time.getText().toString().trim();

        String uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
                .child("User").child(uid).child("Daily Tracker");

        String key = Constant.key.get(position);

        DataModel data = new DataModel(sys, dia, hr, datePick, timePick, cmnt);
        ref.child(key).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Update.this, "Info Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Update.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
