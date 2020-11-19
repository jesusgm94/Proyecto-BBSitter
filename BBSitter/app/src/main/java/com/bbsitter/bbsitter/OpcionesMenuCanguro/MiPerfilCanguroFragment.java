package com.bbsitter.bbsitter.OpcionesMenuCanguro;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bbsitter.bbsitter.R;

public class MiPerfilCanguroFragment extends Fragment {

    private MiPerfilCanguroViewModel mViewModel;

    public static MiPerfilCanguroFragment newInstance() {
        return new MiPerfilCanguroFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.mi_perfil_canguro_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MiPerfilCanguroViewModel.class);
        // TODO: Use the ViewModel
    }

}