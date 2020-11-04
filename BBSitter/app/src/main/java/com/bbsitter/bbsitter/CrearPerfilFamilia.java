package com.bbsitter.bbsitter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
/*import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;*/

public class CrearPerfilFamilia extends AppCompatActivity {


    private TextInputLayout nombreFamilia, descripcionFamilia;

    private Button btnCrearPerfilFamilia;

    private String nombre = "";
    private String descripcion = "";

    private FirebaseAuth mAuth;
    //private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_perfil_familia);

        nombreFamilia = findViewById(R.id.nombreFamilia_edit_text);
        descripcionFamilia = findViewById(R.id.descripcionFamilia_edit_text);

        btnCrearPerfilFamilia = (Button) findViewById(R.id.btnCrearPerfilFamilia);

        mAuth = FirebaseAuth.getInstance();
        //mDatabase = FirebaseDatabase.getInstance().getReference();

        btnCrearPerfilFamilia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nombre = nombreFamilia.getEditText().getText().toString();
                descripcion = descripcionFamilia.getEditText().getText().toString();

                /*Creamos una familia y le metemos los datos que ha metido el usuario*/
                Familia familia = new Familia();
                familia.setNombre(nombre);
                familia.setDescripcion(descripcion);


                //Metemos los valores y la ID al usuario
                /*mDatabase.child("Familia").child(mAuth.getCurrentUser().getUid()).setValue(familia).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {


                        MaterialAlertDialogBuilder builder =
                                new MaterialAlertDialogBuilder(CrearPerfilFamilia.this);

                        builder.setTitle("Perfil Creado");
                        builder.setMessage("Muy bien! Ya tienes creado tu perfil. Ahora puedes ver a nuestras canguros.");
                        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Aqui abrimos la actividad perfil
                                finish();
                                Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(main);

                            }
                        });
                        builder.show();


                    }
                });*/
            }
        });
    }
}