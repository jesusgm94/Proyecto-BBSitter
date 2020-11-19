package com.bbsitter.bbsitter.OpcionesMenuCanguro.Inicio;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bbsitter.bbsitter.R;

public class InicioCanguroFragment extends Fragment {

    private InicioCanguroViewModel mViewModel;

    public static InicioCanguroFragment newInstance() {
        return new InicioCanguroFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inicio_canguro_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(InicioCanguroViewModel.class);
        // TODO: Use the ViewModel
    }

}