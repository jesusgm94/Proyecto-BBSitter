package com.bbsitter.bbsitter.Perfiles;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bbsitter.bbsitter.Bienvenida.ActivityBienvenida;
import com.bbsitter.bbsitter.Main.MainActivity;
import com.bbsitter.bbsitter.ProgressBarPerfilCreado;
import com.bbsitter.bbsitter.R;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;
import es.dmoral.toasty.Toasty;

public class CrearPerfilFamilia extends AppCompatActivity {

    private final String KEY_API_GOOGLE = "AIzaSyCAq5pFIif49ezgqjq4x6ZEaFMyuGXnCH0";

    /*Movidas de Firebase*/
    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;
    private StorageReference storageRef;

    private CircleImageView foto;
    private TextInputLayout nombre, descripcion, direccion, telefono;
    private TextInputEditText etNombre, etDireccion, etDescripcion, etTelefono;
    private Button btnCrearPerfilFamilia;

    /*Para coger la foto de perfil*/
    private Uri uri;
    private String urlFoto = "";

    private LatLng latLng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_perfil_familia);



        /*Firebase Auth y BBDD*/
        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();

        /*Subir archivos a Storage*/
        storageRef = FirebaseStorage.getInstance().getReference();


        nombre = findViewById(R.id.nombreFamilia_edit_text);
        descripcion = findViewById(R.id.descripcionFamilia_edit_text);
        direccion = findViewById(R.id.direccion_edit_text);
        telefono = findViewById(R.id.telefono_edit_text);
        foto = findViewById(R.id.imageFamilia);

        etNombre = findViewById(R.id.etNombreFamilia);
        etDireccion = findViewById(R.id.etDireccionFamilia);
        etDescripcion = findViewById(R.id.etDescripcionFamilia);
        etTelefono = findViewById(R.id.etTelefonoFamilia);

        btnCrearPerfilFamilia = (Button) findViewById(R.id.btnCrearPerfilFamilia);

        establecerAutocompletadoDireccion();
        final ProgressBarCrearPerfil progressBarCrearFamilia = new ProgressBarCrearPerfil(CrearPerfilFamilia.this);
        final ProgressBarPerfilCreado progressBarPerfilCreado = new ProgressBarPerfilCreado(CrearPerfilFamilia.this);

        foto.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });


        btnCrearPerfilFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                try {
                    if (comprobarDatos()) {
                        /*Cargamos los datos*/
                        String nombre = etNombre.getText().toString().trim();
                        String descripcion = etDescripcion.getText().toString().trim();
                        String direccion = etDireccion.getText().toString().trim();
                        String telefono = etTelefono.getText().toString().trim();
                        final String uid = mAuth.getCurrentUser().getUid();
                        String email = mAuth.getCurrentUser().getEmail();
                        /* LOCALIZACION*/
                        // Obtener coordenadas de direccion
                        double longitud = latLng.longitude;
                        double latitud = latLng.latitude;

                        /*Creamos una carpeta con el nombre img_familias para poder meter las fotos*/
                        storageRef = storageRef.child("img_familias").child(uid);


                        /*Creamos un mapa para meter los datos de las familias*/
                        Map<String, Object> mapUser = new HashMap<>();
                        mapUser.put("nombre", nombre);
                        mapUser.put("descripcion", descripcion);
                        mapUser.put("direccion", direccion);
                        mapUser.put("telefono", telefono);
                        mapUser.put("email", email);
                        mapUser.put("latitud", latitud);
                        mapUser.put("longitud", longitud);
                        mapUser.put("uid", uid);


                        /*Creamos la coleccion Familias en la bbdd*/
                        bbdd.collection("familias")
                                .document(uid)
                                .set(mapUser);

                        /*Creamos un mapa para actualizar el perifl del usuario*/
                        Map<String, Object> userUpdate = new HashMap<>();
                        userUpdate.put("perfil", true);
                        userUpdate.put("tipo", "familia");

                        /*Actualizamos el perfil del usuario para que no vuelva a la pantalla de creacion de perfil*/
                        bbdd.collection("usuarios").document(uid)
                                .set(userUpdate, SetOptions.merge());


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


                        // Creamos PROGRESS BAR para que el usuario sepa que su perfil se está creando)
                        progressBarCrearFamilia.StarProgressBar();
                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                progressBarCrearFamilia.finishProgressBar();

                                // Creamos PROGRESS BAR para que el usuario sepa que su perfil ha sido creado)
                                progressBarPerfilCreado.StarProgressBar();
                                Handler handler2 = new Handler();
                                handler2.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        progressBarPerfilCreado.finishProgressBar();

                                        //Aqui abrimos la actividad Bienvenida
                                        Intent activityBienvenida = new Intent(getApplicationContext(), ActivityBienvenida.class);
                                        startActivity(activityBienvenida);
                                        finish();

                                    }
                                }, 4000);
                            }
                        }, 4000);
                    } else {
                        Toast.makeText(CrearPerfilFamilia.this, "No estan todos los datos completados", Toast.LENGTH_SHORT).show();
                    }


                } catch (Exception e) {
                    comprobarDatos();
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
        String telefonoFamilia = etTelefono.getText().toString().trim();


        if (nombreFamilia.isEmpty()) {
            nombre.setError("Debes rellenar el campo");

            validar = false;
        }
        if (direccionFamilia.isEmpty()) {
            direccion.setError("Debes rellenar el campo");
            validar = false;
        }
        if (telefonoFamilia.isEmpty()) {
            telefono.setError("Debes rellenar el campo");
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
        if (!telefonoFamilia.isEmpty()) {
            telefono.setError(null);
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
            Places.initialize(getApplicationContext(), KEY_API_GOOGLE);
        }

        etDireccion.setFocusable(false);
        etDireccion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Inicializamos la lista de lugares
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                // Creamos un intent que nos mostrará el autocompletado de direcciones
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(CrearPerfilFamilia.this);

                // Start activity
                startActivityForResult(intent, 100);
            }
        });
    }


    /* Resultado de autocompletado Direccion*/
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

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
                    Toasty.error(getApplicationContext(), "Debes rellenar la dirección", Toasty.LENGTH_LONG).show();
                }


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {

                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(CrearPerfilFamilia.this, "Error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();

            } else if (resultCode == RESULT_CANCELED) {

            }
        }

        // Resultado de obtener foto del movil
        else if (requestCode == 200) {
            if (resultCode == RESULT_OK) {

                uri = data.getData();

                foto.setImageURI(uri);

                Log.i("BBSitter", uri.toString());


            } else if (resultCode == RESULT_CANCELED) {


            }
        }
    }

}