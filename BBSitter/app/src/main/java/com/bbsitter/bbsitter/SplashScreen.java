package com.bbsitter.bbsitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.bbsitter.bbsitter.MainActivity;
import com.bbsitter.bbsitter.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        /*Ocultamos Actionbar*/
        //getSupportActionBar().hide();

        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                Intent i=new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        };

        Timer timer=new Timer();
        timer.schedule(task, 2000);
    }
}
