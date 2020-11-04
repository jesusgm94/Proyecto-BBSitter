package com.bbsitter.bbsitter;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Login extends AppCompatActivity {


    private String email = "";
    private String password = "";

    private TextInputLayout editTextEmail, editTextPassword;

    private Button btnLogin;

    private SignInButton btnGoogle;

    private TextView etCrearCuenta;

    FirebaseAuth mAuth;
    DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        editTextPassword = findViewById(R.id.password_text_input);
        editTextEmail = findViewById(R.id.email_edit_text);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnGoogle = (SignInButton) findViewById(R.id.sign_in_button);
        btnGoogle.setSize(SignInButton.SIZE_STANDARD);
        etCrearCuenta = (TextView) findViewById(R.id.etCrearCuenta);

        //btnCrearCuenta = (Button) findViewById(R.id.btnCrearCuenta);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = editTextEmail.getEditText().getText().toString().trim();
                password = editTextPassword.getEditText().getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty()) {

                    logearUsuario();

                } else {
                    validarEmail();
                    validarPassword();
                    //Toast.makeText(Login.this, "Error al iniciar sesion", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


            }
        });


        etCrearCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent crearUsuario = new Intent(getApplicationContext(), RegistroUsuarioActivity.class);
                startActivity(crearUsuario);

            }

        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        editTextEmail.setError(null);
        editTextPassword.setError(null);

    }

    private boolean validarEmail() {

        email = editTextEmail.getEditText().getText().toString().trim();

        if (email.isEmpty()) {
            editTextEmail.setError("Debes rellenar el campo");
            return false;
        } else {
            editTextEmail.setError(null);
            return true;
        }

    }

    private boolean validarPassword() {

        password = editTextPassword.getEditText().getText().toString().trim();

        if (password.isEmpty()) {
            editTextPassword.setError("Debes rellenar el campo");
            return false;

        } else {
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

                            mDatabase.child("Usuarios").child(user.getUid()).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (snapshot.exists()) {
                                        Boolean perfil = (Boolean) snapshot.child("perfil").getValue();

                                        if (perfil == true) {
                                            //Aqui abrimos la actividad principal
                                            Intent main = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(main);
                                        } else {

                                            MaterialAlertDialogBuilder builder =
                                                    new MaterialAlertDialogBuilder(Login.this);

                                            builder.setTitle("Crear Perfil");
                                            builder.setMessage("Antes de entrar necesitas crear tu perfil!");
                                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                                @Override
                                                public void onClick(DialogInterface dialog, int which) {
                                                    //Aqui abrimos la actividad perfil
                                                    Intent crearPerfil = new Intent(getApplicationContext(), ElegirquePerfilCrear.class);
                                                    startActivity(crearPerfil);
                                                }
                                            });
                                            builder.show();
                                        }
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });


                        } else {
                            //Si no existe ese usuario en la base de datos no inicia sesion
                            Toast.makeText(Login.this, "Inicio de sesión fallida.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }

}
