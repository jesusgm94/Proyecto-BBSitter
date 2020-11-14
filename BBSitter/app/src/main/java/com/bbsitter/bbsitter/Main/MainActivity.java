package com.bbsitter.bbsitter.Main;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bbsitter.bbsitter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private ImageView imagenUsuarioMenu;
    private TextView tvNombreUsuarioMenu, tvCorreoUsuarioMenu;

    /*Movidas de Firebase*/
    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;
    private NavigationView navigationView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = findViewById(R.id.fab);

        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();


        /*fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own actio", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        /*Menú Usuario*/
        navigationView = findViewById(R.id.nav_view);

        cargarDatosUsuario();



        /*Menu izquierda*/
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_fav, R.id.nav_mensajes)
                .setDrawerLayout(drawer)
                .build();

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }

    private void cargarDatosUsuario()
    {
        String uid = mAuth.getCurrentUser().getUid();

        bbdd.collection("familias")
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                TextView txtName = navigationView.getHeaderView(0).findViewById(R.id.tvNombreUsuarioMenu);
                                ImageView imageView = navigationView.getHeaderView(0).findViewById(R.id.imagenUsuarioMenu);

                                String nombreFamilia =  "Familia " + document.get("nombre").toString();
                                String imagenFamilia = document.get("img").toString();

                                //Agrega una nueva imagen desde una url usando Picasso.
                                Picasso.get().load(imagenFamilia).into(imageView);
                                //Agrega nuevo nombre
                                txtName.setText(nombreFamilia);


                            }
                        } else {
                            Toast.makeText(MainActivity.this, "Error" + getApplicationContext(), Toast.LENGTH_SHORT).show();
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
