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
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class SoporteFragment extends Fragment {

    private SoporteViewModel mViewModel;
    Button enviarEmailSoporte;

    private FirebaseAuth mAuth;
    private String emailCanguro;

    public static SoporteFragment newInstance() {
        return new SoporteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.soporte_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();

        enviarEmailSoporte = view.findViewById(R.id.btnEnviarEmailSoporte);
        emailCanguro = mAuth.getCurrentUser().getEmail();

        enviarEmailSoporte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "soportebbsitter@gmail.com", null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "¿Necesitas ayuda? Estoy aquí para ayudarte!");
                getContext().startActivity(Intent.createChooser(emailIntent, null));
            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(SoporteViewModel.class);
        // TODO: Use the ViewModel
    }

}