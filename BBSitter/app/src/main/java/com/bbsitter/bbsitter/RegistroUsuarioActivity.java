package com.bbsitter.bbsitter;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RegistroUsuarioActivity extends AppCompatActivity {


    private Button btnRegistrar;


    private TextInputLayout registroEmail, registroPass, registroPass2;

    private String email = "";
    private String password = "";
    private String password2 = "";


    /*Firebase*/
    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro_usuario);

        btnRegistrar = (Button) findViewById(R.id.btnRegistrar);

        registroEmail = findViewById(R.id.registroEmail_text_imput);
        registroPass = findViewById(R.id.registroPassword_text_input);
        registroPass2 = findViewById(R.id.registroPassword2_text_input);

        /*FIREBASE*/
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        /*Cuando le damos al botón Registrar queremos que la app comprueba que los campos estan llenos y que las 2 contraseñas sean las mismas. Una vez comprobadas efectuamos el registro del usuario*/
        btnRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = registroEmail.getEditText().getText().toString().trim();
                password = registroPass.getEditText().getText().toString().trim();
                password2 = registroPass2.getEditText().getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty() && !password2.isEmpty()) { /*Si no estan vacías..*/

                    if(password.equals(password2)) /*Comprobamos que las contraseñas sean iguales*/
                    {
                        registrarUsuario(); /*registramos usuarios*/
                    }
                    else
                    {
                        registroPass.setError("");
                        registroPass2.setError("");
                        Toast.makeText(RegistroUsuarioActivity.this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    validarEmail();
                    validarPassword();
                    validarPassword2();
                }
            }
        });


    }


    private boolean validarEmail() {

        email = registroEmail.getEditText().getText().toString().trim(); /*Cogemos el texto del email*/

        if (email.isEmpty()) { /*Si el campo esta vacío...*/
            registroEmail.setError("Debes rellenar el campo"); /*lanzamos el error*/
            return false;
        } else {
            registroEmail.setError(null);/*Si no esta vacío no lanzamos error*/
            return true;
        }

    }


    private boolean validarPassword() {

        password = registroPass.getEditText().getText().toString().trim();

        if (password.isEmpty()) {
            registroPass.setError("Debes rellenar el campo");
            Toast.makeText(RegistroUsuarioActivity.this, "Debes rellenar todos los ", Toast.LENGTH_LONG).show();
            return false;
        }

        else{
            registroPass.setError(null);
            return true;
        }


    }


    private boolean validarPassword2() {

        password2 = registroPass2.getEditText().getText().toString().trim();

        if (password2.isEmpty()) {
            registroPass2.setError("Debes rellenar el campo");
            Toast.makeText(RegistroUsuarioActivity.this, "Debes rellenar el campo contraseña", Toast.LENGTH_LONG).show();
            return false;
        }

        else{
            registroPass2.setError(null);
            return true;
        }


    }


    private void registrarUsuario() {


        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //si es verdad que la tarea ha sido satisfactoria...
                if(task.isSuccessful())
                {
                    //Crear un Usuario
                    Usuario usuario = new Usuario();
                    usuario.setEmail(email);
                    usuario.setPass(password);
                    usuario.setPerfil(false);
                    usuario.setUid(mAuth.getCurrentUser().getUid());

                    //Cogemos el id del usuario y lo guardamos en la variable ID
                    String id = mAuth.getCurrentUser().getUid();

                    //Metemos los valores y la ID al usuario
                    mDatabase.child("Usuarios").child(id).setValue(usuario).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {

                            Toast.makeText(RegistroUsuarioActivity.this, "Usuario registrado!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    });
                }
                else{
                    Toast.makeText(RegistroUsuarioActivity.this, "Este usuario no se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}