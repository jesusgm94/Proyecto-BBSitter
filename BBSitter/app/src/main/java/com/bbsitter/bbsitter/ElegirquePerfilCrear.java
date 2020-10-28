package com.bbsitter.bbsitter;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

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
                Intent main = new Intent (getApplicationContext(), CrearPerfilCanguro.class);
                startActivity(main);
                //finish();

            }
        });

        //Boton perfil Familia
        btnPerfilFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Aqui abrimos la actividad principal
                Intent main = new Intent (getApplicationContext(), MainActivity.class);
                startActivity(main);
                finish();

            }
        });
    }
}