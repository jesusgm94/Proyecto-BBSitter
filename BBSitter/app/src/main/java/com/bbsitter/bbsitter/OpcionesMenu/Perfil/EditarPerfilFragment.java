package com.bbsitter.bbsitter.OpcionesMenu.Perfil;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bbsitter.bbsitter.Login.Login;
import com.bbsitter.bbsitter.OpcionesMenu.Anuncios.AnunciosFragment;
import com.bbsitter.bbsitter.ProgressBarCargando;
import com.bbsitter.bbsitter.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class EditarPerfilFragment extends Fragment {

    private EditarPerfilViewModel mViewModel;
    private final String KEY_API_GOOGLE = "AIzaSyCAq5pFIif49ezgqjq4x6ZEaFMyuGXnCH0";
    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;
    private StorageReference storageRef;

    private String uid;


    private CircleImageView imageEditarPerfilFamilia;
    private TextInputLayout nombre, descripcion, direccion;
    private TextInputEditText etNombre, etDireccion, etDescripcion;
    private Button btnModificarPerfilFamilia, btnEliminarPerfilFamilia;

    /*Para coger la foto de perfil*/
    private Uri uri;
    private String urlFoto = "";

    private LatLng latLng;


    public static EditarPerfilFragment newInstance() {
        return new EditarPerfilFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.editar_perfil_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();

        storageRef = FirebaseStorage.getInstance().getReference();


        nombre = view.findViewById(R.id.nombreEditarFamilia_edit_text);
        descripcion = view.findViewById(R.id.descripcionEditarFamilia_edit_text);
        direccion = view.findViewById(R.id.direccionEditarFamilia_edit_text);
        imageEditarPerfilFamilia = view.findViewById(R.id.imageEditarPerfilFamilia);

        etNombre = view.findViewById(R.id.etNombreEditarPerfilFamilia);
        etDireccion = view.findViewById(R.id.etDireccionEditarPerfilFamilia);
        etDescripcion = view.findViewById(R.id.etDescripcionEditarPerfilFamilia);


        final ProgressBarCargando progressBarCargando = new ProgressBarCargando(getActivity());


        cargarDatosPerfilFamilia();

        establecerAutocompletadoDireccion();


        imageEditarPerfilFamilia.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });


        btnModificarPerfilFamilia = (Button) view.findViewById(R.id.btnModificarPerfilFamilia);
        btnEliminarPerfilFamilia = (Button) view.findViewById(R.id.btnEliminarPerfilFamilia);

        btnEliminarPerfilFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mAuth.getCurrentUser().delete().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {

                            MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext(), R.style.MyMaterialAlertDialog);
                            builder.setTitle("Eliminar mi cuenta");
                            builder.setMessage("Atención: Esta acción es irreversible. ¿Estás seguro/a de que quieres eliminar tu cuenta?");
                            builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                    //ELIMINAMOS ANUNCIO
                                    bbdd.collection("anuncios")
                                            .whereEqualTo("uid", uid)
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                    if (task.isSuccessful()) {

                                                        String idAnuncio = "";
                                                        for (QueryDocumentSnapshot document : task.getResult()) {

                                                            //Recogemos los datos de la base de datos
                                                            idAnuncio = document.get("idAnuncio").toString();

                                                        }

                                                        bbdd.collection("anuncios").document(idAnuncio).delete();


                                                    } else {

                                                    }
                                                }
                                            });

                                    //ELIMINAMOS FAMILIA

                                    bbdd.collection("familias")
                                            .document(uid)
                                            .delete();


                                    //ELIMINAMOS USUARIO
                                    bbdd.collection("usuario")
                                            .document(uid)
                                            .delete();


                                    startActivity(new Intent(getContext(), Login.class));
                                    getActivity().finish();
                                }
                            });
                            builder.show();


                        } else {

                        }
                    }
                });
            }
        });


        btnModificarPerfilFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {

                    /*Cargamos los datos*/
                    String nombre = etNombre.getText().toString().trim();
                    String descripcion = etDescripcion.getText().toString().trim();
                    final String direccion = etDireccion.getText().toString().trim();
                    final String uid = mAuth.getCurrentUser().getUid();
                    final String email = mAuth.getCurrentUser().getEmail();

                    /*Creamos una carpeta con el nombre img_familias para poder meter las fotos*/
                    storageRef = storageRef.child("img_familias").child(uid);


                    /*Creamos un mapa para meter los datos de las familias*/
                    Map<String, Object> mapUser = new HashMap<>();
                    mapUser.put("nombre", nombre);
                    mapUser.put("descripcion", descripcion);
                    mapUser.put("direccion", direccion);
                    mapUser.put("email", email);
                    mapUser.put("uid", uid);


                    try {
                        /* LOCALIZACION*/
                        // Obtener coordenadas de direccion
                        double longitudLoc = latLng.longitude;
                        double latitudLoc = latLng.latitude;


                        // Crear MAPA COORDENADAS para meterlo en la localizacion
                        Map<String, Double> mapLoc = new HashMap<>();
                        mapLoc.put("Latitud", latitudLoc);
                        mapLoc.put("Longitud", longitudLoc);

                        /*metemos el mapa de la latitud y longitud en el mapa de usuario*/
                        mapUser.put("localizacion", mapLoc);

                    } catch (Exception e) {
                        // Crear MAPA COORDENADAS para meterlo en la localizacion
                        Map<String, Double> mapLoc = new HashMap<>();
                        mapLoc.put("Latitud", 40.44);
                        mapLoc.put("Longitud", 3.46);

                        /*metemos el mapa de la latitud y longitud en el mapa de usuario*/
                        mapUser.put("localizacion", mapLoc);
                    }




                    /*Creamos la coleccion Familias en la bbdd*/
                    bbdd.collection("familias")
                            .document(uid)
                            .set(mapUser);

                    if (uri != null) {

                        /*Metemos la foto en Storage*/
                        storageRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        final Uri downloadUrl = uri;
                                        urlFoto = downloadUrl.toString();

                                        /*Creamos un mapa para actualizar la imagen del perfil*/
                                        Map<String, Object> userUpdateImg = new HashMap<>();
                                        userUpdateImg.put("img", urlFoto);

                                        bbdd.collection("familias").document(uid)
                                                .set(userUpdateImg, SetOptions.merge());

                                        bbdd.collection("anuncios")
                                                .whereEqualTo("uid", mAuth.getCurrentUser().getUid())
                                                .get()
                                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                                        if (task.isSuccessful()) {

                                                            String idAnuncio = "";
                                                            for (QueryDocumentSnapshot document : task.getResult()) {

                                                                //Recogemos los datos de la base de datos
                                                                idAnuncio = document.get("idAnuncio").toString();

                                                            }


                                                            /*Creamos un mapa para actualizar la imagen del perfil*/
                                                            Map<String, Object> userUpdateImg = new HashMap<>();
                                                            userUpdateImg.put("img", urlFoto);
                                                            userUpdateImg.put("direccion", direccion);


                                                            try {
                                                                bbdd.collection("anuncios").document(idAnuncio)
                                                                        .set(userUpdateImg, SetOptions.merge());
                                                            }
                                                            catch (IllegalArgumentException e)
                                                            {

                                                            }


                                                        } else {

                                                        }
                                                    }
                                                });


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception exception) {

                                    }
                                });


                            }
                        });

                    } else {
                        //Creamos un mapa para actualizar la imagen del perfil
                        Map<String, Object> userUpdateImg = new HashMap<>();
                        userUpdateImg.put("img", "https://firebasestorage.googleapis.com/v0/b/bbsitter-61bd3.appspot.com/o/img_BBSitter%2Ffotoperfil.jpg?alt=media&token=a76cfc60-0edb-480c-953d-f0925fab2941");

                        bbdd.collection("familias").document(uid)
                                .set(userUpdateImg, SetOptions.merge());
                    }


                    progressBarCargando.StarProgressBar();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBarCargando.finishProgressBar();

                            MiPerfilFamiliaFragment perfilFamiliaFragmentFragment = new MiPerfilFamiliaFragment();
                            getActivity().getSupportFragmentManager().beginTransaction()
                                    .replace(R.id.nav_host_fragment, perfilFamiliaFragmentFragment)
                                    .addToBackStack(null)
                                    .commit();

                        }
                    }, 2000);

                } catch (Exception e) {
                    comprobarDatos();
                }

            }
        });


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(EditarPerfilViewModel.class);
        // TODO: Use the ViewModel
    }

    private void cargarDatosPerfilFamilia() {
        uid = mAuth.getCurrentUser().getUid();

        bbdd.collection("familias")
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                try {
                                    //Recogemos los datos de la base de datos
                                    String nombreFamilia = document.get("nombre").toString();
                                    String imagenFamilia = document.get("img").toString();
                                    String direccionFamilia = document.get("direccion").toString();
                                    String descripcionFamilia = document.get("descripcion").toString();

                                    //Agrega una nueva imagen desde una url usando Picasso.
                                    Picasso.get().load(imagenFamilia).into(imageEditarPerfilFamilia);

                                    //Agrega nuevo nombre
                                    etNombre.setText(nombreFamilia);
                                    etDireccion.setText(direccionFamilia);
                                    etDescripcion.setText(descripcionFamilia);
                                } catch (Exception e) {

                                }


                            }
                        } else {

                        }
                    }
                });

    }

    private boolean comprobarDatos() {
        Boolean validar = true;

        //String fotoFamilia = urlFotoPerfil.toString();
        String nombreFamilia = etNombre.getText().toString().trim();
        String direccionFamilia = etDireccion.getText().toString().trim();
        String descripcionFamilia = etDescripcion.getText().toString().trim();


        if (nombreFamilia.isEmpty()) {
            nombre.setError("Debes rellenar el campo");

            validar = false;
        }
        if (direccionFamilia.isEmpty()) {
            direccion.setError("Debes rellenar el campo");
            validar = false;
        }
        if (descripcionFamilia.isEmpty()) {
            descripcion.setError("Debes rellenar el campo");
            validar = false;
        }

        if (!nombreFamilia.isEmpty()) {
            nombre.setError(null);
        }
        if (!direccionFamilia.isEmpty()) {
            direccion.setError(null);
        }
        if (!descripcionFamilia.isEmpty()) {
            descripcion.setError(null);
        }

        return validar;
    }


    private void cargarImagen() {

        Intent intentCargarFoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        intentCargarFoto.setType("image/");

        startActivityForResult(intentCargarFoto.createChooser(intentCargarFoto, "Seleccione una foto"), 200);


    }

    /*Buscamos la dirrecion con la API de Google*/
    private void establecerAutocompletadoDireccion() {

        // Inicializamos Google.PLACES para autocomplete la direccion
        if (!Places.isInitialized()) {
            Places.initialize(getContext(), KEY_API_GOOGLE);
        }

        etDireccion.setFocusable(false);
        etDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Inicializamos la lista de lugares
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                // Creamos un intent que nos mostrará el autocompletado de direcciones
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(getContext());

                // Start activity
                startActivityForResult(intent, 100);
            }
        });
    }

    /* Resultado de autocompletado Direccion*/
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Resultado de Activity para direccion
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {

                // Instanciamos un PLACE, el cual lo obtendremos del autocomplete
                Place place = Autocomplete.getPlaceFromIntent(data);

                String address = place.getAddress();

                try {

                    etDireccion.setText(address);
                    latLng = place.getLatLng();
                } catch (Exception e) {
                    Toasty.error(getContext(), "Debes rellenar la dirección", Toasty.LENGTH_LONG).show();
                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {

                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(getContext(), "Error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {

            }
        }

        // Resultado de obtener foto del movil
        else if (requestCode == 200) {
            if (resultCode == RESULT_OK) {

                uri = data.getData();

                imageEditarPerfilFamilia.setImageURI(uri);


            } else if (resultCode == RESULT_CANCELED) {
            }
        }
    }

}