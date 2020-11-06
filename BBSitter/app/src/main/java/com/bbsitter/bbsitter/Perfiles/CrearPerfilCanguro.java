package com.bbsitter.bbsitter.Perfiles;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.bbsitter.bbsitter.R;
import com.google.android.gms.common.api.Status;
import com.google.android.libraries.places.api.Places;
import com.google.android.libraries.places.api.model.Place;
import com.google.android.libraries.places.widget.Autocomplete;
import com.google.android.libraries.places.widget.AutocompleteActivity;
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Arrays;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CrearPerfilCanguro extends AppCompatActivity {

    // Atributos
    private CircleImageView foto;

    private TextInputLayout nombre, apellidos, fechaNacimiento, direccion;
    private TextInputEditText etDireccion, etFechaNacimiento;
    private TextView precioHora;
    private Slider sliderPrecio;
    private Button btnCrearCanguro;

    private final String KEY_API_GOOGLE = "AIzaSyCAq5pFIif49ezgqjq4x6ZEaFMyuGXnCH0";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_perfil_canguro);

        // Asignacion de variables
        foto = findViewById(R.id.imageCanguro);

        nombre = findViewById(R.id.nombre_edit_text);
        apellidos = findViewById(R.id.apellidos_edit_text);
        fechaNacimiento = findViewById(R.id.FechaNacimiento_edit_text);
        direccion = findViewById(R.id.direccion_edit_text);
        etDireccion = findViewById(R.id.etDireccion);
        etFechaNacimiento = findViewById(R.id.etFechaNacimiento);
        precioHora = findViewById(R.id.textViewPrecioHora);

        sliderPrecio = findViewById(R.id.slider_precio_hora);

        btnCrearCanguro = findViewById(R.id.btnCrearPerfilCanguro);


        // Campo Precio/hora. SLIDER precio de la hora, mostramos el precio que va eligiendo el usuario
        sliderPrecio.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                String precio = String.valueOf(value);
                precioHora.setText(precio + " €");
            }
        });

        // Campo Fecha Nacimiento
        establecerFechaNacimiento();

        // Campo Direccion
        establecerAutocompletadoDireccion();

        foto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cargarImagen();
            }
        });

        // Accion BOTON CREAR CANGURO
        btnCrearCanguro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                

            }
        });

    }

    // Cargar imagen del movil
    private void cargarImagen() {

        Intent intentCargarFoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentCargarFoto.setType("image/");

        startActivityForResult(intentCargarFoto.createChooser(intentCargarFoto, "Seleccione una foto"),200);


    }

    // Direccion
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
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(CrearPerfilCanguro.this);

                // Start activity
                startActivityForResult(intent, 100);
            }
        });
    }

    // Resultado de:  Autocompletado Direccion o Cargar Foto
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        // Resultado de Activity paara direccion
        if (requestCode == 100) {
            if (resultCode == RESULT_OK) {

                Place place = Autocomplete.getPlaceFromIntent(data);
                //Log.i(TAG, "Place: " + place.getName() + ", " + place.getId() + ", " + place.getAddress());
                Toast.makeText(CrearPerfilCanguro.this, "ID: " + place.getId() +
                                                                "address:" + place.getAddress() +
                                                                "Name:" + place.getName() +
                                                                "latlong: " + place.getLatLng(), Toast.LENGTH_LONG).show();
                String address = place.getAddress();

                etDireccion. setText(address);


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                // TODO: Handle the error.
                Status status = Autocomplete.getStatusFromIntent(data);
                Toast.makeText(CrearPerfilCanguro.this, "Error: " + status.getStatusMessage(), Toast.LENGTH_LONG).show();
                //Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {
                // The user canceled the operation.
            }
        }

        else if (requestCode == 200){

            if (resultCode == RESULT_OK) {

                Uri pathFoto = data.getData();
                foto.setImageURI(pathFoto);
            }
        }

        /*
        if (resultCode == 100 && resultCode == RESULT_OK) {
            // Si sucede, inicializamos el lugar
            Place place = Autocomplete.getPlaceFromIntent(data);

            Toast.makeText(getApplicationContext(), place.getAddress(), Toast.LENGTH_LONG).show();

            // Escribimos la direccion en el campo direccion
            etDireccion.setHint(place.getAddress());

        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            // Cuando falle
            // Inicializamos estado
            Status status = Autocomplete.getStatusFromIntent(data);

            // Ponemos un Toast
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_LONG).show();
        }

         */
    }


    // Fecha Nacimiento
    private void establecerFechaNacimiento() {

        // Creamos un DatePicker para la fecha de Nacimiento
        MaterialDatePicker.Builder builderDatePicker = MaterialDatePicker.Builder.datePicker();
        builderDatePicker.setTitleText("Selecciona una fecha");
        final MaterialDatePicker materialDatePicker = builderDatePicker.build();

        etFechaNacimiento.setFocusable(false);
        etFechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

        // Cuando el usuario eliga la fecha y de al boton OK, hara lo siguiente
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                etFechaNacimiento.setText(materialDatePicker.getHeaderText());
            }
        });
    }
}