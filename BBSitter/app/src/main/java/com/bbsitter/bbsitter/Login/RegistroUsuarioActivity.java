package com.bbsitter.bbsitter.Login;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import com.bbsitter.bbsitter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistroUsuarioActivity extends AppCompatActivity {


    private Button btnRegistrar;


    private TextInputLayout registroEmail, registroPass, registroPass2;

    private String email = "";
    private String password = "";
    private String password2 = "";


    private final ProgressBarRegistro progressBarRegistro = new ProgressBarRegistro(RegistroUsuarioActivity.this);


    /*Firebase*/
    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

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
        bbdd = FirebaseFirestore.getInstance();

        /*Cuando damos al botón Registrar queremos que la app comprueba que los campos estan llenos y que las 2 contraseñas sean las mismas. Una vez comprobadas efectuamos el registro del usuario*/
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
                    Toast.makeText(RegistroUsuarioActivity.this, "Debes rellenar todos los datos", Toast.LENGTH_LONG).show();
                    validarCampos();
                }
            }
        });


    }


    private boolean validarCampos() {

        Boolean validar = true;

        email = registroEmail.getEditText().getText().toString().trim(); /*Cogemos el texto del email*/

        if (email.isEmpty()) { /*Si el campo esta vacío...*/
            registroEmail.setError("Debes rellenar el campo"); /*lanzamos el error*/
            validar = false;
        }
        if (password.isEmpty()) {
            registroPass.setError("Debes rellenar el campo");
            validar = false;
        }
        if (password2.isEmpty()) {
            registroPass2.setError("Debes rellenar el campo");
            validar = false;
        }


        if(!email.isEmpty()){
            registroEmail.setError(null);/*Si no esta vacío no lanzamos error*/
        }

        if(!password.isEmpty()){
            registroPass.setError(null);/*Si no esta vacío no lanzamos error*/
        }

        if(!password2.isEmpty()){
            registroPass2.setError(null);/*Si no esta vacío no lanzamos error*/
        }

        return validar;
    }





    private void registrarUsuario() {

        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                //si es verdad que la tarea ha sido satisfactoria...
                if(task.isSuccessful())
                {
                    String email = registroEmail.getEditText().getText().toString().trim();
                    //String password = registroPass.getEditText().getText().toString().trim();
                    Boolean perfil = false;
                    String uid = mAuth.getCurrentUser().getUid();

                    Map<String, Object> mapUser = new HashMap<>();
                    mapUser.put("email", email);
                    //mapUser.put("password", password);
                    mapUser.put("perfil", perfil);
                    mapUser.put("tipo", "");
                    mapUser.put("uid", uid);

                    bbdd.collection("usuarios")
                            .document(uid)
                            .set(mapUser);

                    // Creamos PROGRESS BAR para que el usuario sepa que su perfil se está creando)
                    progressBarRegistro.StarProgressBar();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            progressBarRegistro.finishProgressBar();

                            MaterialAlertDialogBuilder builder =new MaterialAlertDialogBuilder(RegistroUsuarioActivity.this, R.style.MyMaterialAlertDialog);
                            builder.setTitle("¡Bienvenido a BBSitter!");
                            builder.setMessage("¡Ya estás registrado! Loguéate para seguir con la configuración de tu usuario");
                            builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    finish();
                                }
                            });
                            builder.show();


                        }
                    }, 2000);


                }
                else{
                    Toast.makeText(RegistroUsuarioActivity.this, "Este usuario no se pudo registrar", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}