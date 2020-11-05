package com.bbsitter.bbsitter.Login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bbsitter.bbsitter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class CambiarPassActivity extends AppCompatActivity {

    Button cambiarContrasena;

    private TextInputLayout cambiarpassEmail, cambiarpassPass, cambiarpassPass2;

    private String email = "";
    private String password = "";
    private String password2 = "";

    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_pass);

        cambiarContrasena = (Button) findViewById(R.id.btnCambiarPass);

        cambiarpassEmail = findViewById(R.id.cambiarpassEmail_text_imput);
        cambiarpassPass = findViewById(R.id.cambiarpassPassword_text_input);
        cambiarpassPass2 = findViewById(R.id.cambiarpassPassword2_text_input);

        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();

        cambiarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String email = cambiarpassEmail.getEditText().getText().toString();
                final String pass = cambiarpassPass.getEditText().getText().toString();
                String pass2 = cambiarpassPass2.getEditText().getText().toString();

                if(pass.equals(pass2))
                {
                    bbdd.collection("usuarios")
                            .whereEqualTo("email", email)
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            /*Creamos un mapa para actualizar el perifl del usuario*/
                                            Map<String, Object> userUpdate = new HashMap<>();
                                            userUpdate.put("password", pass);

                                            /*Actualizamos el perfil del usuario para que no vuelva a la pantalla de creacion de perfil*/
                                            bbdd.collection("usuarios").document()
                                                    .set(userUpdate, SetOptions.merge());


                                        }
                                    }
                                }
                            });
                }
                else
                {
                    Toast.makeText(CambiarPassActivity.this, "Las contraseñas no coincide", Toast.LENGTH_SHORT).show();
                }


            }
        });
    }
}