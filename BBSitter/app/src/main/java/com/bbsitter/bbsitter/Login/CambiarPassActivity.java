package com.bbsitter.bbsitter.Login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

    private TextInputLayout cambiarpassEmail;
    private EditText etCambiarPassEmail;

    private String email = "";

    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cambiar_pass);

        cambiarContrasena = (Button) findViewById(R.id.btnCambiarPass);

        cambiarpassEmail = findViewById(R.id.cambiarpassEmail_text_imput);

        etCambiarPassEmail = (EditText) findViewById(R.id.etcambiarpassEmail);

        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();

        cambiarContrasena.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = etCambiarPassEmail.getText().toString();

                if(!email.isEmpty())
                {
                    resetPassword();
                }
                else
                {
                    cambiarpassEmail.setError("Debes ingresar el email");
                }

            }
        });
    }

    private void resetPassword()
    {
        mAuth.sendPasswordResetEmail(email). addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {

                if(task.isSuccessful())
                {
                    Toast.makeText(CambiarPassActivity.this, "Hemos mandado un correo a su email ", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(CambiarPassActivity.this, "No se ha podido enviar el correo", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}