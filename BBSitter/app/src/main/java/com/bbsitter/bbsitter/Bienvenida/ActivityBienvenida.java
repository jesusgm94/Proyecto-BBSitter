package com.bbsitter.bbsitter.Bienvenida;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.bbsitter.bbsitter.R;

public class ActivityBienvenida extends AppCompatActivity {

    private ViewPager sliderPagerView;
    private SliderAdapter adapterSlider;
    //private LinearLayout layoutPuntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida);

        sliderPagerView = (ViewPager) findViewById(R.id.sliderViewPagerBienvenida);
        adapterSlider = new SliderAdapter(this);
        sliderPagerView.setAdapter(adapterSlider);


        //layoutPuntos = findViewById(R.id.layout_puntos);


    }
}