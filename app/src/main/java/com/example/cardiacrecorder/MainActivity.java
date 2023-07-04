package com.example.cardiacrecorder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity implements Constant.OnItemDeleteListener{

    Button fab;
    ImageView logout;
    RecViewAdapter adapter;
    RecyclerView recyclerView;
    FirebaseAuth auth;
    ArrayList<DataModel> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fab=findViewById(R.id.add_btn);
        recyclerView=findViewById(R.id.rec_iew);
        logout=findViewById(R.id.logout_btn);

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dataList=new ArrayList<>();
        adapter=new RecViewAdapter((Context) MainActivity.this, (Constant.OnItemDeleteListener) this,dataList);
        recyclerView.setAdapter(adapter);

        auth=FirebaseAuth.getInstance();

        //readData();
        getData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(MainActivity.this,InfoCollection.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences=getSharedPreferences("checkbox",MODE_PRIVATE);
                SharedPreferences.Editor edit=preferences.edit();
                edit.putBoolean("rem",false);
                edit.apply();

                Intent intent=new Intent(MainActivity.this,SignIn.class);
                intent.putExtra("from_main",true);
                startActivity(intent);
            }
        });
    }

    /*void readData()
    {
        //VpPJGIotETYXwwH2F8nEdUsUyEr2
        //Objects.requireNonNull(auth.getCurrentUser()).getUid()
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference()
                .child("User").child("VpPJGIotETYXwwH2F8nEdUsUyEr2").child("Daily Tracker");
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
    }*/

    void getData()
    {

        String key= Objects.requireNonNull(auth.getCurrentUser()).getUid();
        DatabaseReference ref=FirebaseDatabase.getInstance().getReference()
                .child("User").child(key).child("Daily Tracker");
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

                dataList.clear();
                for (DataSnapshot snapshot1:snapshot.getChildren())
                {
                    Constant.key.add(String.valueOf(snapshot1.getKey()));
                    DataModel data=snapshot1.getValue(DataModel.class);
                    dataList.add(data);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onItemDelete(int position) {
        //VpPJGIotETYXwwH2F8nEdUsUyEr2
        //Objects.requireNonNull(auth.getCurrentUser()).getUid()
        String uid= Objects.requireNonNull(auth.getCurrentUser()).getUid();
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference()
                .child("User").child(uid).child("Daily Tracker");

        String key =Constant.key.get(position) ;
        ref.child(key).removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Constant.key.remove(position);
                    Constant.tmp_time.remove(position);
                    Constant.tmp_sys.remove(position);
                    Constant.tmp_dia.remove(position);
                    Constant.tmp_hr.remove(position);
                    Constant.tmp_date.remove(position);
                    Constant.tmp_cmnt.remove(position);
                    dataList.remove(position);

                    adapter.notifyItemRemoved(position);
                    adapter.notifyItemRangeChanged(position, adapter.getItemCount());

                    Toast.makeText(MainActivity.this, "Data deleted", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
    @Override
    protected void onResume() {
        super.onResume();
        getData();
    }

    @Override
    public void onBackPressed() {
        finishAffinity();
    }
}