package com.example.cardiacrecorder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

/**
 * The SplashScreen class represents the initial screen of the application.
 * It displays a splash screen for a certain duration and then navigates to the SignIn activity.
 */

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);



        Thread thread=new Thread(){

            public void run(){
                try {
                    sleep(2000);
                }

                catch (Exception e)
                {
                    e.printStackTrace();
                }

                finally {


                    Intent welcomeIntent = new Intent(SplashScreen.this, SignIn.class);
                 startActivity(welcomeIntent);

//                    SharedPreferences sp = getSharedPreferences("sp",MODE_PRIVATE);
//                    boolean isSignedIn = sp.getBoolean("is_driver_signed_in",false);
//                    if(isSignedIn){
//                        startActivity(new Intent(MainActivity.this,DriverMapActivity.class));
//                    }
//                    else {
//                        boolean isCustomerSignedIn = sp.getBoolean("is_customer_signed_in",false);
//                        if(isCustomerSignedIn){
//                            startActivity(new Intent(MainActivity.this,CustomerMapActivity.class));
//                        }
//                        else {
//                            Intent welcomeIntent = new Intent(MainActivity.this, WelcomeActivity.class);
//                            startActivity(welcomeIntent);
//                        }


                    }
                }

        };

        thread.start();

    }

    public void onPause(){
        super.onPause();

        finish();
    }
}