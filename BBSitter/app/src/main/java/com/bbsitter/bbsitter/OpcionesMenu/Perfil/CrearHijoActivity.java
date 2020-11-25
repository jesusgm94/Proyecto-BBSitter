package com.bbsitter.bbsitter.OpcionesMenu.Perfil;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.bbsitter.bbsitter.Perfiles.ProgressBarCrearPerfil;
import com.bbsitter.bbsitter.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.HashMap;
import java.util.Map;

public class CrearHijoActivity extends AppCompatActivity {

    //movidas de firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;
    private StorageReference storageRef;


    //
    private TextInputLayout nombreHijo, edadHijo, otrosDatosHijo;
    private TextInputEditText etNombreHijo, etEdadHijo, etOtrosDatosHijo;

    private Button btnCrearHijo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_hijo);

        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();
        storageRef = FirebaseStorage.getInstance().getReference();


        nombreHijo = findViewById(R.id.nombreHijo_edit_text);
        edadHijo = findViewById(R.id.edadHijo_edit_text);
        otrosDatosHijo = findViewById(R.id.otrosDatosHijo_edit_text);

        etNombreHijo = findViewById(R.id.etNombreHijo);
        etEdadHijo = findViewById(R.id.etEdadHijo);
        etOtrosDatosHijo = findViewById(R.id.etOtrosDatosHijo);


        btnCrearHijo = findViewById(R.id.btnCrearHijo);

        final ProgressBarCrearPerfil progressBarCrearFamilia = new ProgressBarCrearPerfil(CrearHijoActivity.this);



        btnCrearHijo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Cargamos los datos
                String nombre = etNombreHijo.getText().toString().trim();
                String edad = etEdadHijo.getText().toString().trim();
                String otrosDatos = etOtrosDatosHijo.getText().toString().trim();
                final String uid = mAuth.getCurrentUser().getUid();


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

                finish();

            }
        });

    }


}