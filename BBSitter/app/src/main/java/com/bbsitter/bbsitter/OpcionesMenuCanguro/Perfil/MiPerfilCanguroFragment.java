package com.bbsitter.bbsitter.OpcionesMenuCanguro.Perfil;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bbsitter.bbsitter.OpcionesMenu.Perfil.CrearHijoFragment;
import com.bbsitter.bbsitter.ProgressBarCargando;
import com.bbsitter.bbsitter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;


public class MiPerfilCanguroFragment extends Fragment {

    private MiPerfilCanguroViewModel mViewModel;

    private CircleImageView fotoMiPerfilCanguro;
    private TextView tvNombreMiPerfilCanguro, tvDescripcionMiPerfilCanguro, tvPrecioHoraMiPerfilCanguro, tvExperienciaMiPerfilCanguro, tvEdadMiPerfilCanguro;

    private MaterialButton btnDireccionMiPerfilCanguro;
    private ExtendedFloatingActionButton btnEditarPerfil;

    private ChipGroup chipGroupPluses, chipGroupIdiomas, chipGroupPreferencias;

    private RatingBar ratingBar;

    private String uid, imagenCanguro;

    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    public static MiPerfilCanguroFragment newInstance() {
        return new MiPerfilCanguroFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.mi_perfil_canguro_fragment, container, false);


        /*Firebase Auth y BBDD*/
        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();

        fotoMiPerfilCanguro = view.findViewById(R.id.imagenMiPerfilCanguro);
        tvNombreMiPerfilCanguro = view.findViewById(R.id.tvNombreMiPerfilCanguro);
        tvDescripcionMiPerfilCanguro = view.findViewById(R.id.tvDescripcionMiPerfilCanguro);
        tvPrecioHoraMiPerfilCanguro = view.findViewById(R.id.tvPrecioHoraMiPerfilCanguro);
        tvExperienciaMiPerfilCanguro = view.findViewById(R.id.tvExperienciaMiPerfilCanguro);
        tvEdadMiPerfilCanguro = view.findViewById(R.id.tvEdadMiPerfilCanguro);
        btnDireccionMiPerfilCanguro = view.findViewById(R.id.btnDireccionMiPerfilCanguro);

        // Chips Groups
        chipGroupPluses = view.findViewById(R.id.chipgroupPlusesMiPerfilCanguro);
        chipGroupIdiomas = view.findViewById(R.id.chipgroupIdiomasMiPerfilCanguro);
        chipGroupPreferencias = view.findViewById(R.id.chipgroupPrefedadesMiPerfilCanguro);

        ratingBar = view.findViewById(R.id.itemRatingBarMiPerfilCanguro);
        btnEditarPerfil = view.findViewById(R.id.btnEditarPerfilCanguro);

        cargarDatosPerfilCanguro();


        final ProgressBarCargando progressBarCargando = new ProgressBarCargando(getActivity());
        progressBarCargando.StarProgressBar();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBarCargando.finishProgressBar();


            }
        }, 2000);


        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditarPerfilCanguroFragment editarPerfilCanguroFragment = new EditarPerfilCanguroFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment_canguro, editarPerfilCanguroFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        return view;
    }

    private void cargarDatosPerfilCanguro() {
        uid = mAuth.getCurrentUser().getUid();


        bbdd.collection("canguros")
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                try {
                                    //Recogemos los datos de la base de datos
                                    String nombreCanguro = document.get("nombre").toString() + " " + document.get("apellidos").toString();
                                    imagenCanguro = document.get("img").toString();
                                    String direccionCanguro = document.get("direccion").toString();
                                    String descripcionCanguro = document.get("descripcion").toString();
                                    String precioHoraCanguro = document.get("precioHora").toString() + " €";
                                    String edadCanguro = " (" + document.get("edad").toString() + " años)";
                                    String experienciaCanguro = document.get("experiencia").toString();

                                    String ratingCang = document.get("rating").toString();
                                    int ratingCanguroInt = Integer.parseInt(ratingCang);

                                    // Añadir Chips Pluses
                                    HashMap<String, Boolean> pluses = (HashMap<String, Boolean>) document.get("pluses");
                                    Set<String> listaKeysPluses = pluses.keySet();

                                    for (String plus : listaKeysPluses) {
                                        Chip chipPlus = new Chip(getContext());
                                        chipPlus.setText(plus.toString());
                                        chipPlus.setTextColor(Color.GRAY);
                                        chipPlus.setChipBackgroundColorResource(R.color.moradooscuro);
                                        chipPlus.setChipStrokeColorResource(R.color.colorPrimaryDark);
                                        chipPlus.setChipStrokeWidth(3);
                                        chipGroupPluses.addView(chipPlus);
                                    }

                                    // Añadir Chips Idiomas
                                    HashMap<String, Boolean> idiomas = (HashMap<String, Boolean>) document.get("idiomas");
                                    Set<String> listaKeysIdiomas = idiomas.keySet();

                                    for (String idioma : listaKeysIdiomas) {
                                        Chip chipIdioma = new Chip(getContext());
                                        chipIdioma.setText(idioma.toString());
                                        chipIdioma.setTextColor(Color.GRAY);
                                        chipIdioma.setChipBackgroundColorResource(R.color.moradooscuro);
                                        chipIdioma.setChipStrokeColorResource(R.color.colorPrimaryDark);
                                        chipIdioma.setChipStrokeWidth(3);
                                        chipGroupIdiomas.addView(chipIdioma);
                                    }

                                    // Añadir Chips Preferencia Edades
                                    HashMap<String, Boolean> prefEdades = (HashMap<String, Boolean>) document.get("preferenciaEdades");
                                    Set<String> listaKeysprefEdades = prefEdades.keySet();

                                    for (String pref : listaKeysprefEdades) {
                                        Chip chipPrefEdades = new Chip(getContext());
                                        chipPrefEdades.setText(pref.toString());
                                        chipPrefEdades.setTextColor(Color.GRAY);
                                        chipPrefEdades.setChipBackgroundColorResource(R.color.moradooscuro);
                                        chipPrefEdades.setChipStrokeColorResource(R.color.colorPrimaryDark);
                                        chipPrefEdades.setChipStrokeWidth(3);
                                        chipGroupPreferencias.addView(chipPrefEdades);
                                    }

                                    //Agrega una nueva imagen desde una url usando Picasso.
                                    Picasso.get().load(imagenCanguro).into(fotoMiPerfilCanguro);

                                    //Agrega nuevo nombre
                                    tvNombreMiPerfilCanguro.setText(nombreCanguro);
                                    btnDireccionMiPerfilCanguro.setText(direccionCanguro);
                                    tvDescripcionMiPerfilCanguro.setText(descripcionCanguro);
                                    tvPrecioHoraMiPerfilCanguro.setText(precioHoraCanguro);
                                    tvEdadMiPerfilCanguro.setText(edadCanguro);
                                    tvExperienciaMiPerfilCanguro.setText(experienciaCanguro);

                                    ratingBar.setNumStars(ratingCanguroInt);
                                } catch (Exception e) {

                                }


                            }
                        } else {

                        }
                    }
                });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MiPerfilCanguroViewModel.class);
        // TODO: Use the ViewModel
    }

}