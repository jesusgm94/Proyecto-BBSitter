package com.bbsitter.bbsitter;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

public class ProgressBarPerfilCreado {

    Activity activity;
    AlertDialog alertDialog;

    public ProgressBarPerfilCreado (Activity miActivity){
        activity = miActivity;
    }

    public void StarProgressBar(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.progressbarperfilcreado, null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();

    }

    public void finishProgressBar(){
        alertDialog.dismiss();
    }
}
