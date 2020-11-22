package com.bbsitter.bbsitter.OpcionesMenu.Anuncios;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.bbsitter.bbsitter.R;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrearAnuncioActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    private TextInputLayout nombre, descripcion, fechaInicio, fechaFin;
    private TextInputEditText etNombre, etDescripcion, etFechaInicio;
    private Button btnCrearAnuncio;
    //private TextInputEditText etFechaFin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_anuncio);

        /*Firebase Auth y BBDD*/
        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();

        nombre = findViewById(R.id.tituloFamilia_edit_text);
        descripcion = findViewById(R.id.descripcionAnuncio_edit_text);
        fechaInicio = findViewById(R.id.FechaCuidadoInicio_edit_text);
        fechaFin = findViewById(R.id.FechaCuidadoFin_edit_text);

        etNombre = findViewById(R.id.etTituloAnuncio);
        etDescripcion = findViewById(R.id.etDescripcionAnuncio);
        etFechaInicio = findViewById(R.id.etFechaCuidadoInicio);
        //etFechaFin = findViewById(R.id.etFechaCuidadoFin);

        btnCrearAnuncio = (Button) findViewById(R.id.btnCrearAnuncio);

        establecerFechaInicio();
        //establecerFechaFin();

        btnCrearAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*Cargamos los datos*/
                String nombre = etNombre.getText().toString().trim();
                String descripcion = etDescripcion.getText().toString().trim();
                String fechaInicio = etFechaInicio.getText().toString().trim();
                //String fechaFin = etFechaFin.getText().toString().trim();
                Date fechaPublicacion = new Date();
                String fechaHoy = fechaPublicacion.toString();
                final String uid = mAuth.getCurrentUser().getUid();

                Map<String, Object> mapUser = new HashMap<>();
                mapUser.put("nombre", nombre);
                mapUser.put("descripcion", descripcion);
                mapUser.put("fechaInicio", fechaInicio);
                mapUser.put("fechaPublicacion", fechaHoy);
                //mapUser.put("fechaFin", fechaFin);
                mapUser.put("uid", uid);

                /*Creamos la coleccion Anuncios en la bbdd*/
                bbdd.collection("anuncios")
                        .document(uid)
                        .set(mapUser);


            }
        });


    }

    private void establecerFechaInicio() {

        // Creamos un DatePicker para la fecha de Nacimiento
        MaterialDatePicker.Builder builderDatePicker = MaterialDatePicker.Builder.datePicker();
        builderDatePicker.setTitleText("Selecciona una fecha");
        final MaterialDatePicker materialDatePicker = builderDatePicker.build();

        etFechaInicio.setFocusable(false);
        etFechaInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        // Cuando el usuario eliga la fecha y de al boton OK, hara lo siguiente
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                etFechaInicio.setText(materialDatePicker.getHeaderText());
            }
        });
    }

    /*private void establecerFechaFin() {

        // Creamos un DatePicker para la fecha de Nacimiento
        MaterialDatePicker.Builder builderDatePicker = MaterialDatePicker.Builder.datePicker();
        builderDatePicker.setTitleText("Selecciona una fecha");
        final MaterialDatePicker materialDatePicker = builderDatePicker.build();

        etFechaFin.setFocusable(false);
        etFechaFin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        // Cuando el usuario eliga la fecha y de al boton OK, hara lo siguiente
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                etFechaFin.setText(materialDatePicker.getHeaderText());
            }
        });
    }*/
}