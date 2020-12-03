package com.bbsitter.bbsitter.OpcionesMenu.Perfil;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bbsitter.bbsitter.ProgressBarCargando;
import com.bbsitter.bbsitter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class CrearHijoFragment extends Fragment {

    private CrearHijoViewModel mViewModel;

    //movidas de firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;
    private StorageReference storageRef;


    //
    private TextInputLayout nombreHijo, edadHijo, otrosDatosHijo;
    private TextInputEditText etNombreHijo, etEdadHijo, etOtrosDatosHijo;

    private Button btnCrearHijo;

    public static CrearHijoFragment newInstance() {
        return new CrearHijoFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.crear_hijo_fragment, container, false);


        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();


        nombreHijo = view.findViewById(R.id.nombreHijo_edit_text);
        edadHijo = view.findViewById(R.id.edadHijo_edit_text);
        otrosDatosHijo = view.findViewById(R.id.otrosDatosHijo_edit_text);

        etNombreHijo = view.findViewById(R.id.etNombreHijo);
        etEdadHijo = view.findViewById(R.id.etEdadHijo);
        etOtrosDatosHijo = view.findViewById(R.id.etOtrosDatosHijo);


        btnCrearHijo = view.findViewById(R.id.btnCrearHijo);

        final ProgressBarCargando progressBarCargando = new ProgressBarCargando(getActivity());



        btnCrearHijo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Cargamos los datos
                String nombre = etNombreHijo.getText().toString().trim();
                String edad = etEdadHijo.getText().toString().trim();
                String otrosDatos = etOtrosDatosHijo.getText().toString().trim();
                final String uid = mAuth.getCurrentUser().getUid();

                if(nombre.isEmpty() || edad.isEmpty() || otrosDatos.isEmpty())
                {
                    comprobarDatos();
                }
                else
                {
                    /// Insertamos los datos en el mapa
                    Map<String, String> mapHijo = new HashMap<>();
                    mapHijo.put("nombre", nombre);
                    mapHijo.put("edad", edad);
                    mapHijo.put("otrosDatos", otrosDatos);
                    mapHijo.put("uid", uid);

                    //Creamos la coleccion Hijos con nuestro mapa
                    bbdd.collection("hijos")
                            .document()
                            .set(mapHijo, SetOptions.merge());


                    bbdd.collection("hijos")
                            .whereEqualTo("uid", uid)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    if (task.isSuccessful()) {

                                        String idHijo = "";
                                        for (QueryDocumentSnapshot document : task.getResult()) {

                                            //Recogemos los datos de la base de datos
                                            idHijo = document.getId();

                                        }

                                        /*Creamos un mapa para actualizar la imagen del perfil*/
                                        Map<String, Object> userUpdateidAnuncio = new HashMap<>();
                                        userUpdateidAnuncio.put("idHijo", idHijo);

                                        bbdd.collection("hijos").document(idHijo)
                                                .set(userUpdateidAnuncio, SetOptions.merge());


                                    } else {

                                    }
                                }
                            });

                    progressBarCargando.StarProgressBar();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBarCargando.finishProgressBar();

                            MiPerfilFamiliaFragment miPerfilFamiliaFragment = new MiPerfilFamiliaFragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.nav_host_fragment, miPerfilFamiliaFragment)
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
        mViewModel = new ViewModelProvider(this).get(CrearHijoViewModel.class);
        // TODO: Use the ViewModel
    }

    private boolean comprobarDatos()
    {
        Boolean validar = true;

        //String fotoFamilia = urlFotoPerfil.toString();
        String nombre = etNombreHijo.getText().toString().trim();
        String edad = etEdadHijo.getText().toString().trim();
        String otrosDatos = etOtrosDatosHijo.getText().toString().trim();


        if(nombre.isEmpty()){
            nombreHijo.setError("Debes rellenar el campo");

            validar = false;
        }
        if(edad.isEmpty())
        {
            edadHijo.setError("Debes rellenar el campo");
            validar = false;
        }
        if(otrosDatos.isEmpty())
        {
            otrosDatosHijo.setError("Debes rellenar el campo");
            validar = false;
        }

        if(!nombre.isEmpty())
        {
            nombreHijo.setError(null);
        }
        if(!edad.isEmpty())
        {
            edadHijo.setError(null);
        }
        if(!otrosDatos.isEmpty())
        {
            otrosDatosHijo.setError(null);
        }

        return validar;
    }

}