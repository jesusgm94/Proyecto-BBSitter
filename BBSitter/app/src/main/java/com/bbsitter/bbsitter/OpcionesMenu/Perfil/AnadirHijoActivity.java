package com.bbsitter.bbsitter.OpcionesMenu.Perfil;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bbsitter.bbsitter.Perfiles.ProgressBarCrearPerfil;
import com.bbsitter.bbsitter.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class AnadirHijoActivity extends AppCompatActivity {

    //movidas de firebase
    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;
    private StorageReference storageRef;


    private CircleImageView fotoHijo;

    //
    private TextInputLayout nombreHijo, edadHijo, otrosDatosHijo;
    private TextInputEditText etNombreHijo, etEdadHijo, etOtrosDatosHijo;

    private MaterialButton btnCrearHijo;

    /*Para coger la foto de perfil*/
    private Uri uri;
    private String urlFotoHijo = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anadir_hijo);

        fotoHijo = findViewById(R.id.imageHijo);

        nombreHijo = findViewById(R.id.nombreHijo_edit_text);
        edadHijo = findViewById(R.id.edadHijo_edit_text);
        otrosDatosHijo = findViewById(R.id.otrosDatosHijo_edit_text);


        btnCrearHijo = findViewById(R.id.btnAnadirHijo);

        final ProgressBarCrearPerfil progressBarCrearFamilia = new ProgressBarCrearPerfil(AnadirHijoActivity.this);

        fotoHijo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });


        /*btnCrearHijo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Cargamos los datos
                String nombre = etNombreHijo.getText().toString().trim();
                String edad = etEdadHijo.getText().toString().trim();
                String otrosDatos = etOtrosDatosHijo.getText().toString().trim();
                final String uid = mAuth.getCurrentUser().getUid();


                //Creamos una carpeta con el nombre img_familias para poder meter las fotos
                storageRef = storageRef.child("img_familias").child(uid);

                //////////////////////////////////////////////////////
                //////////////////////////////////////////////////////
                //////////////////////////////////////////////////////
                //////////////////////////////////////////////////////
                //////////////////////////////////////////////////////
                ////                    METER HIJOS DENTRO DE FAMILIAS CON SUBCOLECCION                   ///
                //////////////////////////////////////////////////////
                //////////////////////////////////////////////////////
                //////////////////////////////////////////////////////
                //////////////////////////////////////////////////////
                //////////////////////////////////////////////////////

            }
        });*/

    }

    private void cargarImagen() {

        Intent intentCargarFoto = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intentCargarFoto.setType("image/");
        startActivityForResult(intentCargarFoto.createChooser(intentCargarFoto, "Seleccione una foto"),200);

    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        // Resultado de obtener foto del movil
        if (requestCode == 200){
            if (resultCode == RESULT_OK) {

                uri = data.getData();


                fotoHijo.setImageURI(uri);


            } else if(resultCode == RESULT_CANCELED){
            }
        }
    }
}