package com.bbsitter.bbsitter.OpcionesMenu.Inicio;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager2.widget.ViewPager2;

import com.bbsitter.bbsitter.OpcionesMenu.Chats.RoomChatFamiliaFragment;
import com.bbsitter.bbsitter.OpcionesMenuCanguro.Inicio.MapsFragmentCanguros;
import com.bbsitter.bbsitter.PagerAdapter;
import com.bbsitter.bbsitter.R;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class InicioFragment extends Fragment {

    private InicioViewModel inicioViewModel;

    // TABLAYOUT para ver tanto la lista como el Map
    private ViewPager2 viewPager2;
    private TabLayout tabLayout;
    TabLayoutMediator tabLayoutMediator;

    private ExtendedFloatingActionButton btnMostrarMapa;

    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        inicioViewModel = ViewModelProviders.of(this).get(InicioViewModel.class);


        View view = inflater.inflate(R.layout.fragment_home, container, false);

        btnMostrarMapa = view.findViewById(R.id.btnMostrarMapa);

        btnMostrarMapa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cargarFragment(new MapsFragmentCanguros());

            }
        });

        viewPager2 = view.findViewById(R.id.viewPagerTab);

        viewPager2.setAdapter(new PagerAdapter(this));

        tabLayout = view.findViewById(R.id.tabLayoutCanguros);

        tabLayoutMediator = new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0: {
                        tab.setText("Novedades");
                        //tab.setIcon(R.drawable.ic_baseline_filter_list_24);
                        break;
                    }
                    case 1: {
                        tab.setText("Los más valorados");
                        //tab.setIcon(R.drawable.ic_baseline_add_location_24);
                        break;
                    }
                    case 2: {
                        tab.setText("Los más baratos");
                        //tab.setIcon(R.drawable.ic_baseline_add_location_24);
                        break;
                    }

                }
            }
        });
        tabLayoutMediator.attach();


        return view;
    }
    private void cargarFragment(Fragment fragment){
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

}
