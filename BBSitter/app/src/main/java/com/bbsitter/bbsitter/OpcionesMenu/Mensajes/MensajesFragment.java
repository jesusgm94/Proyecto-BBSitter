package com.bbsitter.bbsitter.OpcionesMenu.Mensajes;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.bbsitter.bbsitter.R;

public class MensajesFragment extends Fragment {

    private MensajesViewModel mensajesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        mensajesViewModel =
                ViewModelProviders.of(this).get(MensajesViewModel.class);
        View root = inflater.inflate(R.layout.fragment_mensajes, container, false);

        /*
        final TextView textView = root.findViewById(R.id.text_gallery);
        mensajesViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        */


        return root;
    }
}
