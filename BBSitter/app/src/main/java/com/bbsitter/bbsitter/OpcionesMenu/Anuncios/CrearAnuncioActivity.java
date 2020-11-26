package com.bbsitter.bbsitter.OpcionesMenu.Anuncios;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bbsitter.bbsitter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class CrearAnuncioActivity extends AppCompatActivity {

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_anuncio);

        //Firebase
        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();

        //EditText
        titulo = findViewById(R.id.tituloFamilia_edit_text);
        descripcion = findViewById(R.id.descripcionAnuncio_edit_text);
        etTitulo = findViewById(R.id.etTituloAnuncio);
        etDescripcion = findViewById(R.id.etDescripcionAnuncio);

        //CheckBox Tiempo
        radioGroupTiempo = findViewById(R.id.radioGroupTiempo);
        rbFines = findViewById(R.id.radio_button_Fines);
        rbDiario = findViewById(R.id.radio_button_Diario);
        rbDiasSueltos = findViewById(R.id.radio_button_diasSueltos);
        rbHabitualmente = findViewById(R.id.radio_button_Habitualmente);

        //CheckBox Casa
        radioGroupCasa = findViewById(R.id.radioGroupCasa);
        rbCasaFamilia = findViewById(R.id.radio_button_CasaFamilia);
        rbCasaCanguro = findViewById(R.id.radio_button_CasaCanguro);

        // ListView Checkboxs PLUSES
        listViewPluses = findViewById(R.id.listViewPluses);
        adapterPluses = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, arrayPluses);
        listViewPluses.setAdapter(adapterPluses);

        // ListView Checkboxs IDIOMAS
        listViewIdiomas = findViewById(R.id.listViewIdiomas);
        adapterIdiomas = new ArrayAdapter<>(this, android.R.layout.simple_list_item_multiple_choice, arrayIdiomas);
        listViewIdiomas.setAdapter(adapterIdiomas);

        //Boton Crear anuncio
        btnCrearAnuncio = (Button) findViewById(R.id.btnCrearAnuncio);

        btnCrearAnuncio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cargarDatos();
                finish();

            }
        });


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



                        } else {
                            Toast.makeText(CrearAnuncioActivity.this, "Error" + getApplicationContext(), Toast.LENGTH_SHORT).show();
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


}