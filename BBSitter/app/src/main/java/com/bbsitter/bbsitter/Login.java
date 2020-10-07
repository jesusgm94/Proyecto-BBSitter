package com.bbsitter.bbsitter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity {


    private String email  = "";
    private String pass = "";

    EditText etEmail, etPass;
    Button btnIniciar, btnCrearCuenta;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        etEmail = (EditText) findViewById(R.id.etEmail);
        etPass = (EditText) findViewById(R.id.etPassword);

        btnIniciar = (Button) findViewById(R.id.btnLoginSingIn);
        btnCrearCuenta = (Button) findViewById(R.id.btnCrearCuenta);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etEmail.getText().toString();
                pass = etPass.getText().toString();

                if(!email.isEmpty() && !pass.isEmpty())
                {
                    logearUsuario();

                }
                else {

                    Toast.makeText(Login.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etEmail.getText().toString();
                pass = etPass.getText().toString();

                if(!email.isEmpty() && !pass.isEmpty())
                {

                    if(pass.length()<=6)
                    {
                        registrarUsuario();
                    }
                    else
                    {
                        Toast.makeText(Login.this, "La contraseña debe tener más de 6 caracteres", Toast.LENGTH_LONG).show();
                    }

                }
                else {

                    Toast.makeText(Login.this, "Debe rellenar todos los datos", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void logearUsuario() {
        //comprobamos que el email y la contraseña estan en la base de datos
        mAuth.signInWithEmailAndPassword(email, pass)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { //Si la tarea es satisfactoria

                            //Metemos en la app al usuario
                            FirebaseUser user = mAuth.getCurrentUser();
                            //Aqui abrimos la actividad principal
                            Intent main = new Intent (getApplicationContext(), MainActivity.class);
                            startActivity(main);

                        } else {

                            //Si no existe ese usuario en la base de datos no inicia sesion
                            Toast.makeText(Login.this, "Inicio de sesión fallida.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });

    }

    private void registrarUsuario() {

        //Toast.makeText(Login.this, email + " " + pass , Toast.LENGTH_SHORT).show();
        mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //si es verdad que la tarea ha sido satisfactoria...
                if(task.isSuccessful())
                {
                    //Crear un mapa de Usuarios
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", email);
                    map.put("password", pass);

                    //Cogemos el id del usuario y lo guardamos en la variable ID
                    String id = mAuth.getCurrentUser().getUid();

                    //Metemos los valores y la ID al usuario
                    mDatabase.child("Usuarios").child(id).setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            Toast.makeText(Login.this, "Usuario registrado!", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                else{
                    Toast.makeText(Login.this, "Este usuario no se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
