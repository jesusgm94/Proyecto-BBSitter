package com.bbsitter.bbsitter.Bienvenida;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;

import com.bbsitter.bbsitter.R;

public class BienvenidaCanguroActivity extends AppCompatActivity {

    private ViewPager sliderPagerView;
    private SliderCanguroAdapter adapterSlider;
    //private LinearLayout layoutPuntos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bienvenida_canguro);

        sliderPagerView = (ViewPager) findViewById(R.id.sliderViewPagerBienvenidaCanguro);
        adapterSlider = new SliderCanguroAdapter(this);
        sliderPagerView.setAdapter(adapterSlider);


        //layoutPuntos = findViewById(R.id.layout_puntos);


    }
}