package com.bbsitter.bbsitter.Bienvenida;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bbsitter.bbsitter.Main.MainActivity;
import com.bbsitter.bbsitter.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import kotlin.reflect.KVisibility;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;


public class SliderAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    // Lista de imagenes
    public int[] imagenes = {
            R.drawable.welcomepagerview,
            R.drawable.fondobbsitterchat,
            R.drawable.fondobbsittermapa
    };

    // Lista de imagenes
    public String[] descripciones = {
            "Bienvenido a la mejor app de búsqueda de cangur@s",
            "Chatea con los canguros al instante",
            "Encuentra a tus canguros más cercanos"
    };

    public SliderAdapter(Context context){
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout, container, false);

        RelativeLayout layoutSlide = view.findViewById(R.id.slideLayout);
        ImageView imageSlide = (ImageView) view.findViewById(R.id.imageSlider);
        TextView textDescripcion = (TextView) view.findViewById(R.id.tvDescripcionslide);
        ExtendedFloatingActionButton btnComenzar = (ExtendedFloatingActionButton) view.findViewById(R.id.btnComenzar);
        TextView desliza = (TextView) view.findViewById(R.id.tvDesliza);
        imageSlide.setImageResource(imagenes[position]);
        textDescripcion.setText(descripciones[position]);

        if(position == 2){
            btnComenzar.setVisibility(View.VISIBLE);
            btnComenzar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Aqui abrimos la actividad Bienvenida
                    @SuppressLint("RestrictedApi") Intent main = new Intent(getApplicationContext(), MainActivity.class);
                    context.startActivity(main);
                }
            });

        }

        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
       container.removeView((RelativeLayout)object);
    }

    @Override
    public int getCount() {
        return descripciones.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view== (RelativeLayout)object);
    }


}
