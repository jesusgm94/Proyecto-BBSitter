package com.bbsitter.bbsitter.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.bbsitter.bbsitter.Perfiles.CrearPerfilCanguro;
import com.bbsitter.bbsitter.Perfiles.CrearPerfilFamilia;
import com.bbsitter.bbsitter.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

public class ElegirquePerfilCrear extends AppCompatActivity {


    Button btnPerfilFamilia, btnPerfilCanguro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_elegirque_perfil_crear);


        // Mensaje de que el usuario necesita crearse un perfil antes de poder usar la app

        MaterialAlertDialogBuilder builder =new MaterialAlertDialogBuilder(ElegirquePerfilCrear.this, R.style.MyMaterialAlertDialog);
        builder.setTitle("¿Todavia no tienes un perfil creado?");
        builder.setMessage("Antes de usar nuestra App necesitas crear tu perfil para que lo demás sepan  si eres una familia molona o un canguro responsable!");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();

        btnPerfilCanguro = (Button) findViewById(R.id.btnPerfilCanguro);
        btnPerfilFamilia = (Button) findViewById(R.id.btnPerfilFamilia);

        // Boton perfil Canguro
        btnPerfilCanguro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Aqui abrimos la actividad para rellenar los datos y crear el perfil Canguro
                Intent crearCanguro = new Intent (getApplicationContext(), CrearPerfilCanguro.class);
                startActivity(crearCanguro);

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