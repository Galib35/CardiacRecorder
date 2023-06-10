package com.example.cardiacrecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton fab;
    RecViewAdapter adapter;
    RecyclerView recyclerView;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab=findViewById(R.id.add_btn);
        recyclerView=findViewById(R.id.rec_iew);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter=new RecViewAdapter(MainActivity.this);
        recyclerView.setAdapter(adapter);

        auth=FirebaseAuth.getInstance();

        readData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,InfoCollection.class);
                startActivity(intent);
            }
        });
    }

    void readData()
    {
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference()
                .child("User").child(Objects.requireNonNull(auth.getCurrentUser()).getUid()).child("Daily Tracker");
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Constant.tmp_sys.clear();
                Constant.tmp_dia.clear();
                Constant.tmp_cmnt.clear();
                Constant.tmp_date.clear();
                Constant.tmp_time.clear();
                Constant.tmp_hr.clear();
                Constant.key.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Constant.key.add(String.valueOf(snapshot1.getKey()));
                    Constant.tmp_sys.add(String.valueOf(snapshot1.child("Systolic").getValue()));
                    Constant.tmp_dia.add(String.valueOf(snapshot1.child("Diastolic").getValue()));
                    Constant.tmp_hr.add(String.valueOf(snapshot1.child("Heart Rate").getValue()));
                    Constant.tmp_cmnt.add(String.valueOf(snapshot1.child("Comment").getValue()));
                    Constant.tmp_date.add(String.valueOf(snapshot1.child("Date").getValue()));
                    Constant.tmp_time.add(String.valueOf(snapshot1.child("Time").getValue()));


                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        readData();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}