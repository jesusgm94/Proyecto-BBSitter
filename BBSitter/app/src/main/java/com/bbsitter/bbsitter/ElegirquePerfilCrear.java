package com.bbsitter.bbsitter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ElegirquePerfilCrear extends AppCompatActivity {


    Button btnPerfilFamilia, btnPerfilCanguro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegirque_perfil_crear);

        btnPerfilCanguro = (Button) findViewById(R.id.btnPerfilCanguro);
        btnPerfilFamilia = (Button) findViewById(R.id.btnPerfilFamilia);

        // Boton perfil Canguro
        btnPerfilCanguro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Aqui abrimos la actividad para rellenar los datos y crear el perfil Canguro
                //Intent crearCanguro = new Intent (getApplicationContext(), CrearPerfilCanguro.class);
                //startActivity(crearCanguro);

            }
        });

        //Boton perfil Familia
        btnPerfilFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Aqui abrimos la actividad principal
                Intent crearFamilia = new Intent (getApplicationContext(), CrearPerfilFamilia.class);
                startActivity(crearFamilia);

            }
        });
    }
}