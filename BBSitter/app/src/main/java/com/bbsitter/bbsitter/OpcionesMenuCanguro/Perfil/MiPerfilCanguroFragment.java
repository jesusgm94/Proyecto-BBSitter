package com.bbsitter.bbsitter.OpcionesMenuCanguro.Perfil;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bbsitter.bbsitter.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;


public class MiPerfilCanguroFragment extends Fragment {

    private MiPerfilCanguroViewModel mViewModel;

    ExtendedFloatingActionButton btnEditarPerfil;

    public static MiPerfilCanguroFragment newInstance() {
        return new MiPerfilCanguroFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mi_perfil_canguro_fragment, container, false);

        btnEditarPerfil = view.findViewById(R.id.btnEditarPerfilCanguro);

        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Necesitas una intent para  editar el perfil", Snackbar.LENGTH_LONG)
                        .setAction("Necesitas una intent para  editar el perfil", null).show();
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MiPerfilCanguroViewModel.class);
        // TODO: Use the ViewModel
    }

}