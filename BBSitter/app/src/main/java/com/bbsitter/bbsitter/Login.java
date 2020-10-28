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
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Login extends AppCompatActivity {


    private String email  = "";
    private String password = "";

    private TextInputLayout editTextEmail, editTextPassword;

    Button btnIniciar, btnCrearCuenta, btnGoogle;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editTextPassword = findViewById(R.id.password_text_input);
        editTextEmail = findViewById(R.id.email_edit_text);

        btnIniciar = (Button) findViewById(R.id.btnLogin);
        btnGoogle = (Button) findViewById(R.id.btnLoginGoogle);

        //btnCrearCuenta = (Button) findViewById(R.id.btnCrearCuenta);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        btnIniciar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = editTextEmail.getEditText().getText().toString().trim();
                password = editTextPassword.getEditText().getText().toString().trim();

                if(!email.isEmpty() && !password.isEmpty())
                {
                    logearUsuario();

                }
                else {
                    validarEmail();
                    validarPassword();
                    //Toast.makeText(Login.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Aqui abrimos la actividad principal
                Intent main = new Intent (getApplicationContext(), ElegirquePerfilCrear.class);
                startActivity(main);
                finish();

            }
        });


        /*
        btnCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = editTextEmail.getEditText().getText().toString().trim();
                password = editTextPassword.getEditText().getText().toString().trim();

                if(!email.isEmpty() && !password.isEmpty())
                {

                    if(password.length()<=6)
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

        */
    }

    private boolean validarEmail(){

        email = editTextEmail.getEditText().getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Debes rellenar el campo");
            return false;
        }
        // rellenar para validar un correo correcto
        /*
        else if(!Pattern.EMAIL){

        }
        */

        else{
            editTextEmail.setError(null);
            return true;
        }

    }

    private boolean validarPassword(){

        password = editTextPassword.getEditText().getText().toString().trim();

        if(password.isEmpty()){
            editTextPassword.setError("Debes rellenar el campo");
            return false;
        }

        else{
            editTextEmail.setError(null);
            return true;
        }

    }

    private void logearUsuario() {
        //comprobamos que el email y la contraseña estan en la base de datos
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) { //Si la tarea es satisfactoria

                            //Metemos en la app al usuario
                            FirebaseUser user = mAuth.getCurrentUser();

                            //Aqui abrimos la actividad principal
                            Intent main = new Intent (getApplicationContext(), MainActivity.class);
                            startActivity(main);
                            finish();

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
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //si es verdad que la tarea ha sido satisfactoria...
                if(task.isSuccessful())
                {
                    //Crear un mapa de Usuarios
                    Map<String, Object> map = new HashMap<>();
                    map.put("email", email);
                    map.put("password", password);

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
