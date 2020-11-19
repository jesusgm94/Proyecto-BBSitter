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

public class ChatsCanguroFragment extends Fragment {

    private ChatsCanguroViewModel mViewModel;

    public static ChatsCanguroFragment newInstance() {
        return new ChatsCanguroFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.chats_canguro_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(ChatsCanguroViewModel.class);
        // TODO: Use the ViewModel
    }

}