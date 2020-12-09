package com.bbsitter.bbsitter.Bienvenida;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.bbsitter.bbsitter.Main.MainActivityCanguro;
import com.bbsitter.bbsitter.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class SliderCanguroAdapter extends PagerAdapter {

    Context context;
    LayoutInflater layoutInflater;

    // Lista de imagenes
    public int[] imagenesCanguro = {
            R.drawable.welcomepagerview,
            R.drawable.fondobbsitterchat,
            R.drawable.fondobbsittermapa
    };

    // Lista de imagenes
    public String[] descripciones = {
            "Bienvenido a la mejor app de búsqueda de cangur@s",
            "Chatea con las familia desde su anuncio al instante",
            "Encuentra anuncios de familias"
    };

    public SliderCanguroAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.slide_layout_canguro, container, false);

        RelativeLayout layoutSlide = view.findViewById(R.id.slideLayoutCanguro);
        ImageView imageSlide = (ImageView) view.findViewById(R.id.imageSliderCanguro);
        TextView textDescripcion = (TextView) view.findViewById(R.id.tvDescripcionslideCanguro);
        ExtendedFloatingActionButton btnComenzar = (ExtendedFloatingActionButton) view.findViewById(R.id.btnComenzarCanguro);
        TextView desliza = (TextView) view.findViewById(R.id.tvDesliza);


        imageSlide.setImageResource(imagenesCanguro[position]);
        textDescripcion.setText(descripciones[position]);

        if (position == 2) {
            btnComenzar.setVisibility(View.VISIBLE);
            btnComenzar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Aqui abrimos la actividad Bienvenida
                    @SuppressLint("RestrictedApi") Intent main = new Intent(getApplicationContext(), MainActivityCanguro.class);
                    context.startActivity(main);
                }
            });

        }

        container.addView(view);
        return view;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((RelativeLayout) object);
    }

    @Override
    public int getCount() {
        return descripciones.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return (view == (RelativeLayout) object);
    }
}
