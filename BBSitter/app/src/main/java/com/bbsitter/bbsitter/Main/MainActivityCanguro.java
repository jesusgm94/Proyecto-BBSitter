package com.bbsitter.bbsitter.Main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bbsitter.bbsitter.Perfiles.PerfilFamiliaActivity;
import com.bbsitter.bbsitter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class MainActivityCanguro extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private ImageView imagenUsuarioMenu;
    private TextView tvNombreUsuarioMenu, tvEmailUsuarioMenu;

    /*Movidas de Firebase*/
    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_canguro);

        // ACTION BAR
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();


        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);


        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);

        NavigationUI.setupWithNavController(navigationView, navController);


        ////////////////////////////// Menú desplegable parte superior //////////////////////////////////////
        navigationView = findViewById(R.id.nav_view);
        imagenUsuarioMenu = navigationView.getHeaderView(0).findViewById(R.id.imagenUsuarioMenu);
        tvNombreUsuarioMenu = navigationView.getHeaderView(0).findViewById(R.id.tvNombreUsuarioMenu);
        tvEmailUsuarioMenu = navigationView.getHeaderView(0).findViewById(R.id.tvEmailUsuarioMenu);
        cargarDatosUsuario();

        //Cuando pulsamos la imagen vamos al perfil de la familia
        imagenUsuarioMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent perfilFamilia = new Intent(getApplicationContext(), PerfilFamiliaActivity.class);
                startActivity(perfilFamilia);

            }
        });
    }

    private void cargarDatosUsuario()
    {
        String uid = mAuth.getCurrentUser().getUid();

        bbdd.collection("canguros")
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //Recogemos los datos de la base de datos
                                String nombreFamilia =  document.get("nombre").toString();
                                String imagenFamilia = document.get("img").toString();
                                String emailFamilia = mAuth.getCurrentUser().getEmail();

                                //Agrega una nueva imagen desde una url usando Picasso.
                                Picasso.get().load(imagenFamilia).into(imagenUsuarioMenu);

                                //Agrega nuevo nombre
                                tvNombreUsuarioMenu.setText(nombreFamilia);
                                tvEmailUsuarioMenu.setText(emailFamilia);


                            }
                        } else {
                            Toast.makeText(MainActivityCanguro.this, "Error" + getApplicationContext(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}