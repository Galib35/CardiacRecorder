package com.example.cardiacrecorder;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

/**
 * Activity for user sign-in.
 */
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

        checkBox.setOnClickListener(view -> check = checkBox.isChecked());

        create_ac.setOnClickListener(view -> {
            Intent intent = new Intent(SignIn.this, SignUp.class);
            startActivity(intent);
        });

        email.addTextChangedListener(watcher);
        pass.addTextChangedListener(watcher);

        btn.setOnClickListener(view -> {
            pr.setVisibility(View.VISIBLE);
            signIn();
        });

        forget.setOnClickListener(view -> {
            Intent intent = new Intent(SignIn.this, ForgetPassword.class);
            startActivity(intent);
        });
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            String password, em;
            em = email.getText().toString().trim();
            password = pass.getText().toString().trim();

            btn.setEnabled(!password.isEmpty() && !em.isEmpty());

            switch (com.example.cardiacrecorder.Constant.ch) {
                case 'e':
                    email_input.setError(null);
                    break;
                case 'p':
                    pass_input.setError(null);
                    break;
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };

    /**
     * Performs the sign-in process.
     */
    void signIn() {
        String password, em;
        em = email.getText().toString().trim();
        password = pass.getText().toString().trim();

        com.example.cardiacrecorder.Constant.ch = 'n';
        if (!Patterns.EMAIL_ADDRESS.matcher(em).matches()) {
            pr.setVisibility(View.INVISIBLE);
            email_input.setError("Invalid email");
            Constant.ch = 'e';
        } else if (password.contains(" ")) {
            pr.setVisibility(View.INVISIBLE);
            pass_input.setError("No space is allowed!");
            Constant.ch = 'p';
        } else if (password.length() < 6) {
            pr.setVisibility(View.INVISIBLE);
            pass_input.setError("Must contain at least 6 characters");
            Constant.ch = 'p';
        } else {
            Log.d("YourTag", "Gabbar0");
            auth.signInWithEmailAndPassword(em, password).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Log.d("YourTag", "Gabbar1");
                    if (auth.getCurrentUser() != null && auth.getCurrentUser().isEmailVerified()) {
                        Log.d("YourTag", "Gabbar2");
                        pr.setVisibility(View.INVISIBLE);
                        Toast.makeText(SignIn.this, "Sign in Successful!", Toast.LENGTH_SHORT).show();
                        SharedPreferences preferences = getSharedPreferences("checkbox", MODE_PRIVATE);
                        SharedPreferences.Editor edit = preferences.edit();
                        edit.putBoolean("rem", check);
                        edit.apply();
                        Intent intent = new Intent(SignIn.this, MainActivity.class);
                        startActivity(intent);
                    } else if (auth.getCurrentUser() != null && !auth.getCurrentUser().isEmailVerified()) {
                        Log.d("YourTag", "Gabbar3");
                        pr.setVisibility(View.INVISIBLE);
                        Toast.makeText(SignIn.this, "Please verify your email!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Log.d("YourTag", "Gabbar4");
                    pr.setVisibility(View.INVISIBLE);
                    Toast.makeText(SignIn.this, Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        if (getIntent().getBooleanExtra("from_main", false) || getIntent().getBooleanExtra("from_fp", false)) {
            finishAffinity();
        } else
            super.onBackPressed();
    }
}
