package com.bbsitter.bbsitter.Login;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.bbsitter.bbsitter.R;

public class ProgressBarRegistro {

    Activity activity;
    AlertDialog alertDialog;

    public ProgressBarRegistro(Activity miActivity){
        activity = miActivity;
    }

    public void StarProgressBar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.activity_progress_bar_registro, null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();

    }

    public void finishProgressBar(){
        alertDialog.dismiss();
    }
}