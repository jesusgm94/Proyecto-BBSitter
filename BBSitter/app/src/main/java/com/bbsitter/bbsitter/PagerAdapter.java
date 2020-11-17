package com.bbsitter.bbsitter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class PagerAdapter  extends FragmentStateAdapter {

    public PagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch(position){
            case 0:
                return new ListaCangurosFragment();
            case 1:
                return new MapsFragmentCanguros();
            default:
                return new ListaCangurosFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
