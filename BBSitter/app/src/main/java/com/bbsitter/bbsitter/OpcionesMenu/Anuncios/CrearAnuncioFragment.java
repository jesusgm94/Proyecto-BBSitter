package com.bbsitter.bbsitter.OpcionesMenu.Anuncios;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bbsitter.bbsitter.ProgressBarCargando;
import com.bbsitter.bbsitter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrearAnuncioFragment extends Fragment {

    private CrearAnuncioViewModel mViewModel;

    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    private TextInputLayout titulo, descripcion;
    private TextInputEditText etTitulo, etDescripcion;

    private RadioGroup radioGroupTiempo, radioGroupCasa;
    private RadioButton rbFines, rbDiario, rbDiasSueltos, rbHabitualmente, rbCasaFamilia, rbCasaCanguro;


    private Button btnCrearAnuncio;

    private String nombre = "";
    private String direccion = "";
    private String img = "";

    private ListView listViewPluses, listViewIdiomas;
    private ArrayAdapter<String> adapterPluses, adapterIdiomas;
    private String [] arrayPluses = {"Carnet de conducir", "Primeros auxilios", "Cocinar", "Ayuda con los deberes", "Jugar"};
    String [] arrayIdiomas = {"Español", "Inglés", "Francés","Alemán", "Otros"};

    public static CrearAnuncioFragment newInstance() {
        return new CrearAnuncioFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.crear_anuncio_fragment, container, false);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();

        //EditText
        titulo = view.findViewById(R.id.tituloFamilia_edit_text);
        descripcion = view.findViewById(R.id.descripcionAnuncio_edit_text);
        etTitulo = view.findViewById(R.id.etTituloAnuncio);
        etDescripcion = view.findViewById(R.id.etDescripcionAnuncio);

        //CheckBox Tiempo
        radioGroupTiempo = view.findViewById(R.id.radioGroupTiempo);
        rbFines = view.findViewById(R.id.radio_button_Fines);
        rbDiario = view.findViewById(R.id.radio_button_Diario);
        rbDiasSueltos = view.findViewById(R.id.radio_button_diasSueltos);
        rbHabitualmente = view.findViewById(R.id.radio_button_Habitualmente);

        //CheckBox Casa
        radioGroupCasa = view.findViewById(R.id.radioGroupCasa);
        rbCasaFamilia = view.findViewById(R.id.radio_button_CasaFamilia);
        rbCasaCanguro = view.findViewById(R.id.radio_button_CasaCanguro);

        // ListView Checkboxs PLUSES
        listViewPluses = view.findViewById(R.id.listViewPluses);
        adapterPluses = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_multiple_choice, arrayPluses);
        listViewPluses.setAdapter(adapterPluses);

        // ListView Checkboxs IDIOMAS
        listViewIdiomas = view.findViewById(R.id.listViewIdiomas);
        adapterIdiomas = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_multiple_choice, arrayIdiomas);
        listViewIdiomas.setAdapter(adapterIdiomas);

        //Boton Crear anuncio
        btnCrearAnuncio = (Button) view.findViewById(R.id.btnCrearAnuncio);

        final ProgressBarCargando progressBarCargando = new ProgressBarCargando(getActivity());

        btnCrearAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titulo = etTitulo.getText().toString().trim();
                String descripcion = etDescripcion.getText().toString().trim();


                if(titulo.isEmpty() || descripcion.isEmpty())
                {
                    comprobarDatos();
                    Snackbar.make(view, "Debes rellenar todos los datos", Snackbar.LENGTH_LONG)
                            .setAction("Debes rellenar todos los datos", null).show();
                }
                else
                {
                    cargarDatos();
                    progressBarCargando.StarProgressBar();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBarCargando.finishProgressBar();

                            AnunciosFragment anunciosFragment = new AnunciosFragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.nav_host_fragment, anunciosFragment)
                                    .addToBackStack(null)
                                    .commit();

                        }
                    }, 2000);
                }

            }
        });

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(CrearAnuncioViewModel.class);
        // TODO: Use the ViewModel
    }

    private void cargarDatos() {

        final String uid = mAuth.getCurrentUser().getUid();

            bbdd.collection("familias")
                    .whereEqualTo("uid", uid)
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    //Recogemos los datos de la base de datos
                                    nombre = "Familia " + document.get("nombre").toString();
                                    img = document.get("img").toString();
                                    direccion = document.get("direccion").toString();

                                }
                                /*Cargamos los datos*/
                                String titulo = etTitulo.getText().toString().trim();
                                String descripcion = etDescripcion.getText().toString().trim();
                                String tiempo = obtenerTiempo();
                                String casa = obtenerCasa();

                                // Creamos un objeto Date
                                Date fechaPublicacion = new Date();
                                // Especificamos un formato
                                String DATE_FORMAT = "dd MMM HH:mm";
                                // Create object of SimpleDateFormat and pass the desired date format.
                                SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT);

                                String fechaHoy = sdf.format(fechaPublicacion);

                                // ListView CHECKBOX PLUSES
                                Map<String, Boolean> mapPluses = new HashMap<>();
                                for (int cont = 0; cont < listViewPluses.getCount(); cont++) {
                                    if (listViewPluses.isItemChecked(cont)) {
                                        String textoItemSeleccionado = listViewPluses.getItemAtPosition(cont).toString();
                                        mapPluses.put(textoItemSeleccionado, true);
                                    }
                                }

                                // ListView CHECKBOX IDIOMAS
                                Map<String, Boolean> mapIdiomas = new HashMap<>();
                                for (int cont = 0; cont < listViewIdiomas.getCount(); cont++) {
                                    if (listViewIdiomas.isItemChecked(cont)) {
                                        String textoItemSeleccionado = listViewIdiomas.getItemAtPosition(cont).toString();
                                        mapIdiomas.put(textoItemSeleccionado, true);
                                    }
                                }

                                String uid = mAuth.getCurrentUser().getUid();


                                Map<String, Object> mapAnuncio = new HashMap<>();
                                mapAnuncio.put("titulo", titulo);
                                mapAnuncio.put("descripcion", descripcion);
                                mapAnuncio.put("fechaPublicacion", fechaHoy);
                                mapAnuncio.put("casa", casa);
                                mapAnuncio.put("tiempo", tiempo);
                                mapAnuncio.put("nombre", nombre);
                                mapAnuncio.put("img", img);
                                mapAnuncio.put("direccion", direccion);
                                mapAnuncio.put("pluses", mapPluses);
                                mapAnuncio.put("idiomas", mapIdiomas);
                                mapAnuncio.put("uid", uid);



                                /*Creamos la coleccion Anuncios en la bbdd*/
                                bbdd.collection("anuncios")
                                        .document()
                                        .set(mapAnuncio);



                                bbdd.collection("anuncios")
                                        .whereEqualTo("uid", uid)
                                        .get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                if (task.isSuccessful()) {

                                                    String idAnuncio = "";
                                                    for (QueryDocumentSnapshot document : task.getResult()) {

                                                        //Recogemos el ID
                                                        idAnuncio = document.getId();

                                                    }

                                                    // mapa para actualizar el anuncio
                                                    Map<String, Object> userUpdateidAnuncio = new HashMap<>();
                                                    userUpdateidAnuncio.put("idAnuncio", idAnuncio);

                                                    // Actualizamos el anuncio, introduciendo su ID
                                                    bbdd.collection("anuncios").document(idAnuncio)
                                                            .set(userUpdateidAnuncio, SetOptions.merge());


                                                } else {

                                                }
                                            }
                                        });

                            } else {

                            }
                        }
                    });


    }

    private String obtenerTiempo() {

        String tiempo = "";

        if(rbFines.isChecked()){
            tiempo = rbFines.getText().toString();
        }
        else if(rbDiario.isChecked())
        {
            tiempo = rbDiario.getText().toString();
        }
        else if(rbDiasSueltos.isChecked())
        {
            tiempo = rbDiasSueltos.getText().toString();
        }
        else if(rbHabitualmente.isChecked())
        {
            tiempo = rbHabitualmente.getText().toString();
        }

        return tiempo;
    }

    private String obtenerCasa() {

        String casa = "";

        if(rbCasaFamilia.isChecked()){
            casa = rbCasaFamilia.getText().toString();
        }
        else if(rbCasaCanguro.isChecked())
        {
            casa = rbCasaCanguro.getText().toString();
        }

        return casa;
    }

    private boolean comprobarDatos() {
        Boolean validar = true;

        String tituloAnuncio = etTitulo.getText().toString().trim();
        String descripcionAnuncio = etDescripcion.getText().toString().trim();


        if(tituloAnuncio.isEmpty()){
            titulo.setError("Debes rellenar el campo");

            validar = false;
        }
        if(descripcionAnuncio.isEmpty())
        {
            descripcion.setError("Debes rellenar el campo");
            validar = false;
        }


        if(!tituloAnuncio.isEmpty())
        {
            titulo.setError(null);
        }
        if(!descripcionAnuncio.isEmpty())
        {
            descripcion.setError(null);
        }


        return validar;
    }

}