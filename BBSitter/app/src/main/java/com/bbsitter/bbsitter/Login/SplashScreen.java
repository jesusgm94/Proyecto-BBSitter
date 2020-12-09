package com.bbsitter.bbsitter.Login;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.bbsitter.bbsitter.R;

import java.util.Timer;
import java.util.TimerTask;

public class SplashScreen extends AppCompatActivity {

    VideoView video;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        /*Ocultamos Actionbar*/
        //getSupportActionBar().hide();

        video = (VideoView) findViewById(R.id.video);

        // Meter VIDEO, cargado en el proyecto
        MediaController controller = new MediaController(this);
        //controller.setAnchorView(this.video);// Enganchamos los controles al video
        Uri uriVideo = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.videobbsittersplahscreen);
        this.video.setVideoURI(uriVideo);
        //this.video.setMediaController(controller);
        this.video.start();

        TimerTask task=new TimerTask() {
            @Override
            public void run() {
                Intent i=new Intent(getApplicationContext(), Login.class);
                startActivity(i);
                finish();
            }
        };

        Timer timer=new Timer();
        timer.schedule(task, 4000);
    }
}
