package com.bbsitter.bbsitter.Perfiles;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.bbsitter.bbsitter.R;

public class ProgressBarCrearPerfil {

    Activity activity;
    AlertDialog alertDialog;

    public ProgressBarCrearPerfil(Activity miActivity){
        activity = miActivity;
    }

    public void StarProgressBar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progressbar_creacionperfil, null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();

    }

    public void finishProgressBar(){
        alertDialog.dismiss();
    }
}
