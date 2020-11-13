package com.bbsitter.bbsitter.Perfiles;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bbsitter.bbsitter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilFamiliaActivity extends AppCompatActivity {

    private CircleImageView fotoPerfilFamilia;
    private TextView tvNombrePerfilFamilia;

    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_familia);

        fotoPerfilFamilia = findViewById(R.id.imagenPerfilFamilia);
        tvNombrePerfilFamilia = findViewById(R.id.tvNombrePerfilFamilia);

        /*Firebase Auth y BBDD*/
        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();

        cargarDatosPerfilFamilia();
    }

    private void cargarDatosPerfilFamilia()
    {
        String uid = mAuth.getCurrentUser().getUid();


        bbdd.collection("familias")
                .whereEqualTo(getPackageName(), uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                    }
                });

    }
}