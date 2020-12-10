package com.bbsitter.bbsitter;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

public class AcercaNosotrosFragment extends Fragment {

    private AcercaNosotrosViewModel mViewModel;
    private ImageView ivGit;

    public static AcercaNosotrosFragment newInstance() {
        return new AcercaNosotrosFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.acerca_nosotros_fragment, container, false);

        ivGit = view.findViewById(R.id.ivGit);

        ivGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url="https://github.com/jesusgm94/Proyecto-BBSitter/";
                Uri uri = Uri.parse(url);
                Intent intentNavegador = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intentNavegador);
            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AcercaNosotrosViewModel.class);
        // TODO: Use the ViewModel
    }

}