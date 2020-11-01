package com.bbsitter.bbsitter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class CrearPerfilCanguro extends AppCompatActivity {

    // Atributos
    private TextInputLayout nombre, apellidos, fechaNacimiento, direccion;
    private TextInputEditText etDireccion;
    private TextView precioHora;
    private Slider sliderPrecio;

    private final String KEY_API_GOOGLE = "AIzaSyCAq5pFIif49ezgqjq4x6ZEaFMyuGXnCH0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_perfil_canguro);

        // Asignacion de variables
        nombre = findViewById(R.id.nombre_edit_text);
        apellidos = findViewById(R.id.apellidos_edit_text);
        fechaNacimiento = findViewById(R.id.FechaNacimiento_edit_text);
        direccion = findViewById(R.id.direccion_edit_text);
        etDireccion = findViewById(R.id.etDireccion);

        precioHora = findViewById(R.id.textViewPrecioHora);
        sliderPrecio = findViewById(R.id.slider_precio_hora);


        // SLIDER precio de la hora, mostramos el precio que va eligiendo el usuario
        sliderPrecio.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                String precio = String.valueOf(value);
                precioHora.setText(precio + " €");
            }
        });

        // DatePicker para la fecha de Nacimiento
        MaterialDatePicker.Builder builderDatePicker = MaterialDatePicker.Builder.datePicker();
        builderDatePicker.setTitleText("Selecciona una fecha");
        final MaterialDatePicker materialDatePicker = builderDatePicker.build();



/*
        fechaNacimiento.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                Toast.makeText(getApplicationContext(), "Al pulsar en fecha ", Toast.LENGTH_LONG).show();
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

 */
/*
        fechaNacimiento.addOnEditTextAttachedListener(new TextInputLayout.OnEditTextAttachedListener() {
            @Override
            public void onEditTextAttached(@NonNull TextInputLayout textInputLayout) {
                Toast.makeText(getApplicationContext(), "Al pulsar en fecha ", Toast.LENGTH_LONG).show();
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

 */
/*
        fechaNacimiento.setFocusable(false);
        fechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Al pulsar en fecha ", Toast.LENGTH_LONG).show();
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

 */

/*
        // Cuando el usuario eliga la fecha y de al boton OK, hara lo siguiente
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                //fechaNacimiento.(materialDatePicker.getHeaderText());
            }
        });
*/

        // Iniciamos Google.PLACES para autocomplete del la direccion
        Places.initialize(getApplicationContext(),KEY_API_GOOGLE);

        direccion.setFocusable(false);
        direccion.setOnClickListener(new View.OnClickListener() {
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

/*
        direccion.addOnEditTextAttachedListener(new TextInputLayout.OnEditTextAttachedListener() {
            @Override
            public void onEditTextAttached(@NonNull TextInputLayout textInputLayout) {
                List<Place.Field> fieldList = Arrays.asList(Place.Field.ADDRESS, Place.Field.LAT_LNG, Place.Field.NAME);

                // Creamos un intent que nos mostrará el autocompletado de direcciones
                Intent intent = new Autocomplete.IntentBuilder(AutocompleteActivityMode.OVERLAY, fieldList).build(getApplicationContext());

                // Start actvity
                startActivityForResult(intent, 100);
            }
        });

 */
    }


    // Direccion
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 100 && resultCode == RESULT_OK) {
            // Si sucede, inicializamos el lugar
            Place place = Autocomplete.getPlaceFromIntent(data);

            Toast.makeText(getApplicationContext(), place.getAddress(), Toast.LENGTH_LONG).show();

            // Escribimos la direccion en el campo direccion


        } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
            // Cuando falle
            // Inicializamos estado
            Status status = Autocomplete.getStatusFromIntent(data);

            // Ponemos un Toast
            Toast.makeText(getApplicationContext(), status.getStatusMessage(), Toast.LENGTH_LONG).show();

            direccion.setHint(status.getStatusMessage());

        }
    }
}