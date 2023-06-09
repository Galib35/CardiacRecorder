package com.example.cardiacrecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignIn extends AppCompatActivity {

    EditText email, pass;
    TextInputLayout email_input, pass_input;
    Button btn;
    TextView create_ac, forget;
    ProgressBar pr;
    CheckBox checkBox;

    FirebaseAuth auth;

    boolean check = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        create_ac = findViewById(R.id.crt_account);
        email = findViewById(R.id.email_sign_in);
        pass = findViewById(R.id.pass_sign_in);
        email_input = findViewById(R.id.email_input_signin);
        pass_input = findViewById(R.id.pass_input_signin);
        btn = findViewById(R.id.sign_in_btn);
        pr = findViewById(R.id.pr_sign_in);
        checkBox = findViewById(R.id.cb);
        forget = findViewById(R.id.fp_txt);

        auth = FirebaseAuth.getInstance();

        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
        boolean remember = preferences.getBoolean("rem", false);

        if (remember) {
            Intent intent = new Intent(SignIn.this, MainActivity.class);
            startActivity(intent);
        }

        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                check = checkBox.isChecked();
            }
        });


    }
}

