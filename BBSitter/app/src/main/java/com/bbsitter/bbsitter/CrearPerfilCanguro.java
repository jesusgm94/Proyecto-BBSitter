package com.bbsitter.bbsitter;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.slider.Slider;
import com.google.android.material.textfield.TextInputLayout;

public class CrearPerfilCanguro extends AppCompatActivity {

    private TextInputLayout nombre, apellidos, fechaNacimiento, direccion;
    private TextView precioHora;
    private Slider sliderPrecio;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_perfil_canguro);

        nombre = findViewById(R.id.nombre_edit_text);
        apellidos = findViewById(R.id.apellidos_edit_text);
        fechaNacimiento = findViewById(R.id.FechaNacimiento_edit_text);
        direccion = findViewById(R.id.direccion_edit_text);

        precioHora = findViewById(R.id.textViewPrecioHora);

        sliderPrecio = findViewById(R.id.slider_precio_hora);


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

        fechaNacimiento.addOnEditTextAttachedListener(new TextInputLayout.OnEditTextAttachedListener() {
            @Override
            public void onEditTextAttached(@NonNull TextInputLayout textInputLayout) {
                Toast.makeText(getApplicationContext(), "Al pulsar en fecha ", Toast.LENGTH_LONG).show();
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });

/*
        fechaNacimiento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(getApplicationContext(), "Al pulsar en fecha ", Toast.LENGTH_LONG).show();
                materialDatePicker.show(getSupportFragmentManager(), "DATE_PICKER");
            }
        });


 */
        // Cuando el usuario eliga la fecha y de al boton OK, hara lo siguiente
        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {
                //fechaNacimiento.(materialDatePicker.getHeaderText());
            }
        });


        sliderPrecio.addOnChangeListener(new Slider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull Slider slider, float value, boolean fromUser) {
                String precio = String.valueOf(value);
                precioHora.setText(precio + " €");
            }
        });
    }
}