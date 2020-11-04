package com.bbsitter.bbsitter.Perfiles;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bbsitter.bbsitter.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CrearPerfilFamilia extends AppCompatActivity {


    private TextInputLayout nombreFamilia, descripcionFamilia;
    private TextInputEditText direccionFamilia;

    private Button btnCrearPerfilFamilia;

    private String nombre = "";
    private String descripcion = "";

    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    private final String KEY_API_GOOGLE = "AIzaSyCAq5pFIif49ezgqjq4x6ZEaFMyuGXnCH0";
    private Place place;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_perfil_familia);

        nombreFamilia = findViewById(R.id.nombreFamilia_edit_text);
        descripcionFamilia = findViewById(R.id.descripcion_edit_text);
        direccionFamilia = findViewById(R.id.etDireccionFamilia);

        btnCrearPerfilFamilia = (Button) findViewById(R.id.btnCrearPerfilFamilia);

        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();

        establecerAutocompletadoDireccion();

        btnCrearPerfilFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                /*Creamos una familia y le metemos los datos que ha metido el usuario*/

                String nombre = nombreFamilia.getEditText().getText().toString().trim();
                String descripcion = descripcionFamilia.getEditText().getText().toString().trim();
                String uid = mAuth.getCurrentUser().getUid();

                Map<String, Object> mapUser = new HashMap<>();
                mapUser.put("nombre", nombre);
                mapUser.put("descripcion", descripcion);


                Map<String, Double> mapLoc = new HashMap<>();
                mapLoc.put("latitud", 50.43242);
                mapLoc.put("longitud", 40.30193);


                mapUser.put("localizacion", mapLoc);

                bbdd.collection("familias")
                        .document(uid)
                        .set(mapUser);

                Toast.makeText(CrearPerfilFamilia.this, "Perfil Creado!", Toast.LENGTH_SHORT).show();
                finish();

            }
        });
    }


    /*Buscamos la dirrecion con la API de Google*/
    private void establecerAutocompletadoDireccion() {
        // Iniciamos Google.PLACES para autocomplete del la direccion
        Places.initialize(getApplicationContext(),KEY_API_GOOGLE);

        direccionFamilia.setFocusable(false);
        direccionFamilia.setOnClickListener(new View.OnClickListener() {
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
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == 100 && resultCode == RESULT_OK) {
            // Si sucede, inicializamos el lugar
            Place place = Autocomplete.getPlaceFromIntent(data);

            Toast.makeText(getApplicationContext(), place.getAddress(), Toast.LENGTH_LONG).show();

            // Escribimos la direccion en el campo direccion
            direccionFamilia.setHint(place.getAddress());

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            // Cuando falle
            // Inicializamos estado
            Status status = Autocomplete.getStatusFromIntent(data);

            // Ponemos un Toast
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_LONG).show();
        }
    }
}