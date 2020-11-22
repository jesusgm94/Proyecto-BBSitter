package com.bbsitter.bbsitter.Perfiles;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bbsitter.bbsitter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilFamiliaActivity extends AppCompatActivity {

    private CircleImageView fotoPerfilFamilia;
    private TextView tvNombrePerfilFamilia, tvDescripcionPerfilFamilia;
    private MaterialButton btnDireccionPerfilFamilia;

    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    private String uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil_familia);

        fotoPerfilFamilia = findViewById(R.id.imagenPerfilFamilia);
        tvNombrePerfilFamilia = findViewById(R.id.tvNombrePerfilFamilia);
        btnDireccionPerfilFamilia = findViewById(R.id.btnDireccionPerfilFamilia);
        tvDescripcionPerfilFamilia = findViewById(R.id.tvDescripcionPerfilFamilia);

        /*Firebase Auth y BBDD*/
        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();

        cargarDatosPerfilFamilia();
    }

    private void cargarDatosPerfilFamilia()
    {
        uid = mAuth.getCurrentUser().getUid();

        bbdd.collection("familias")
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //Recogemos los datos de la base de datos
                                String nombreFamilia =  "Familia " + document.get("nombre").toString();
                                String imagenFamilia = document.get("img").toString();
                                String direccionFamilia = document.get("direccion").toString();
                                String descripcionFamilia = document.get("descripcion").toString();

                                //Agrega una nueva imagen desde una url usando Picasso.
                                Picasso.get().load(imagenFamilia).into(fotoPerfilFamilia);

                                //Agrega nuevo nombre
                                tvNombrePerfilFamilia.setText(nombreFamilia);
                                btnDireccionPerfilFamilia.setText(direccionFamilia);
                                tvDescripcionPerfilFamilia.setText(descripcionFamilia);

                            }
                        } else {
                            Toast.makeText(PerfilFamiliaActivity.this, "Error" + getApplicationContext(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }
}