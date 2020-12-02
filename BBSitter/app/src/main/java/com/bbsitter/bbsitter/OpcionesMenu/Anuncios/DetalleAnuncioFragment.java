package com.bbsitter.bbsitter.OpcionesMenu.Anuncios;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bbsitter.bbsitter.ProgressBarCargando;
import com.bbsitter.bbsitter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetalleAnuncioFragment extends Fragment {

    private DetalleAnuncioViewModel mViewModel;

    private CircleImageView imagenPerfilDetalleAnuncio;
    private TextView tvDescripcionDetalleAnuncio,tvTituloDetalleAnuncio, tvNombreFamiliaDetalleAnuncio, tvDetalleAnuncioFechaPublicacion;
    private MaterialButton btnDireccionDetalleAnuncio;

    private String idAnuncio;

    private Chip chipCasaDetalleAnuncio, chipFrecuenciaDetalleAnuncio;

    private ChipGroup chipGroupDetalleAnuncioPluses, chipGroupDetalleAnuncioIdiomas;

    private FirebaseFirestore bbdd;




    public static DetalleAnuncioFragment newInstance() {
        return new DetalleAnuncioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.detalle_anuncio_fragment, container, false);
        final ProgressBarCargando progressBarCargando = new ProgressBarCargando(getActivity());


        progressBarCargando.StarProgressBar();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBarCargando.finishProgressBar();


            }
        }, 1000);

        bbdd = FirebaseFirestore.getInstance();
        //Recogemos el uid de la familia de InicioCanguroFragment
        Bundle data = this.getArguments();
        if(data != null){
            idAnuncio = data.getString("idAnuncio");
        }

        imagenPerfilDetalleAnuncio = view.findViewById(R.id.imagenFamiliaDetallesAnuncios);
        tvTituloDetalleAnuncio = view.findViewById(R.id.tvTituloDetalleAnuncio);
        tvDescripcionDetalleAnuncio = view.findViewById(R.id.tvDescripcionDetalleAnuncio);
        tvNombreFamiliaDetalleAnuncio = view.findViewById(R.id.tvNombreFamiliaDetalleAnuncio);
        btnDireccionDetalleAnuncio = view.findViewById(R.id.btnDireccionDetalleAnuncio);
        tvDetalleAnuncioFechaPublicacion = view.findViewById(R.id.tvDetalleAnuncioFechaPublicacion);

        //CHIP
        chipCasaDetalleAnuncio = view.findViewById(R.id.chipDetallesAnuncioLugar);
        chipFrecuenciaDetalleAnuncio = view.findViewById(R.id.chipDetallesAnuncioFrecuencia);

        //CHIP GRUOP
        chipGroupDetalleAnuncioPluses = view.findViewById(R.id.chipgroupDetallesAnuncioPluses);
        chipGroupDetalleAnuncioIdiomas = view.findViewById(R.id.chipgroupDetallesAnuncioIdiomas);

        cargarDatosAnuncio();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(DetalleAnuncioViewModel.class);
    }

    private void cargarDatosAnuncio()
    {

        bbdd.collection("anuncios")
                .whereEqualTo("idAnuncio", idAnuncio)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //Recogemos los datos de la base de datos
                                String tituloAnuncio =  document.get("titulo").toString();
                                String descripcionAnuncio = document.get("descripcion").toString();
                                String casaAnuncio = document.get("casa").toString();
                                String frecuenciaAnuncio = document.get("tiempo").toString();
                                String direccionAnuncio = document.get("direccion").toString();
                                String imagenFamiliaAnuncio = document.get("img").toString();
                                String nombreFamiliaAnuncio = document.get("nombre").toString();
                                String fechaPublicacion = document.get("fechaPublicacion").toString();


                                // Añadir Chips Pluses
                                HashMap<String, Boolean> pluses = (HashMap<String, Boolean>) document.get("pluses");
                                Set<String> listaKeysPluses = pluses.keySet();

                                for(String plus : listaKeysPluses){
                                    Chip chipPlus = new Chip(getContext());
                                    chipPlus.setText(plus.toString());
                                    chipPlus.setTextColor(Color.BLACK);
                                    chipPlus.setChipBackgroundColorResource(R.color.colorgris);
                                    chipGroupDetalleAnuncioPluses.addView(chipPlus);
                                }

                                // Añadir Chips Idiomas
                                HashMap<String, Boolean> idiomas = (HashMap<String, Boolean>) document.get("idiomas");
                                Set<String> listaKeysIdiomas = idiomas.keySet();

                                for(String idioma : listaKeysIdiomas){
                                    Chip chipIdioma = new Chip(getContext());
                                    chipIdioma.setText(idioma.toString());
                                    chipIdioma.setTextColor(Color.BLACK);
                                    chipIdioma.setChipBackgroundColorResource(R.color.colorgris);
                                    chipGroupDetalleAnuncioIdiomas.addView(chipIdioma);
                                }


                                //Agrega una nueva imagen desde una url usando Picasso.
                                Picasso.get().load(imagenFamiliaAnuncio).into(imagenPerfilDetalleAnuncio);

                                //Agrega nuevo nombre
                                tvNombreFamiliaDetalleAnuncio.setText(nombreFamiliaAnuncio);
                                tvTituloDetalleAnuncio.setText(tituloAnuncio);
                                tvDescripcionDetalleAnuncio.setText(descripcionAnuncio);
                                tvDetalleAnuncioFechaPublicacion.setText(fechaPublicacion);
                                chipCasaDetalleAnuncio.setText(casaAnuncio);
                                chipCasaDetalleAnuncio.setTextColor(Color.BLACK);
                                chipFrecuenciaDetalleAnuncio.setText(frecuenciaAnuncio);
                                chipFrecuenciaDetalleAnuncio.setTextColor(Color.BLACK);
                                //tvCasaDetalleAnuncio.setText(casaAnuncio);
                                //tvFrecuenciaDetalleAnuncio.setText(frecuenciaAnuncio);
                                btnDireccionDetalleAnuncio.setText(direccionAnuncio);



                            }
                        } else {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}