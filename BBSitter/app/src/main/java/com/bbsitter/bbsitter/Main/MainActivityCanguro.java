package com.bbsitter.bbsitter.Main;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.bbsitter.bbsitter.Login.Login;
import com.bbsitter.bbsitter.OpcionesMenuCanguro.Perfil.MiPerfilCanguroFragment;
import com.bbsitter.bbsitter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

public class MainActivityCanguro extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfigurationCanguro;

    private ImageView imagenUsuarioMenu;
    private TextView tvNombreUsuarioMenu, tvEmailUsuarioMenu;

    /*Movidas de Firebase*/
    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_canguro);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // ACTION BAR
        Toolbar toolbar = findViewById(R.id.toolbarCanguro);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();

        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{

                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION

            },1000);

        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout_canguro);

        NavigationView navigationViewCanguro = findViewById(R.id.nav_view_canguro);


        mAppBarConfigurationCanguro = new AppBarConfiguration.Builder(
                R.id.inicioCanguroFragment, R.id.miPerfilCanguroFragment, R.id.chatsCanguroFragment )
                .setDrawerLayout(drawer)
                .build();


        NavController navControllerCanguro = Navigation.findNavController(this, R.id.nav_host_fragment_canguro);

        NavigationUI.setupActionBarWithNavController(this, navControllerCanguro, mAppBarConfigurationCanguro);

        NavigationUI.setupWithNavController(navigationViewCanguro, navControllerCanguro);


        ////////////////////////////// Menú desplegable parte superior //////////////////////////////////////
        navigationViewCanguro = findViewById(R.id.nav_view_canguro);
        imagenUsuarioMenu = navigationViewCanguro.getHeaderView(0).findViewById(R.id.imagenCanguroMenu);
        tvNombreUsuarioMenu = navigationViewCanguro.getHeaderView(0).findViewById(R.id.tvNombreCanguroMenu);
        tvEmailUsuarioMenu = navigationViewCanguro.getHeaderView(0).findViewById(R.id.tvEmailCanguroMenu);
        cargarDatosUsuario();

        //Cuando pulsamos la imagen vamos al perfil de la familia
        imagenUsuarioMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                cargarFragment(new MiPerfilCanguroFragment());

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

                                try {
                                    //Recogemos los datos de la base de datos
                                    String nombre =  document.get("nombre").toString();
                                    String imagen = document.get("img").toString();
                                    String email = mAuth.getCurrentUser().getEmail();

                                    //Agrega una nueva imagen desde una url usando Picasso.
                                    Picasso.get().load(imagen).into(imagenUsuarioMenu);

                                    //Agrega nuevo nombre
                                    tvNombreUsuarioMenu.setText(nombre);
                                    tvEmailUsuarioMenu.setText(email);
                                }
                                catch(Exception e)
                                {

                                }



                            }
                        } else {
                            Toast.makeText(MainActivityCanguro.this, "Error" + getApplicationContext(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_cerrarSesion:
                MaterialAlertDialogBuilder builder =new MaterialAlertDialogBuilder(MainActivityCanguro.this, R.style.MyMaterialAlertDialog);
                builder.setTitle("Cerrar sesión");
                builder.setMessage("¿Quieres cerrar sesión?");
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent login = new Intent(getApplicationContext(), Login.class);
                        startActivity(login);
                        finish();
                    }
                });
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navControllerCanguro = Navigation.findNavController(this, R.id.nav_host_fragment_canguro);
        return NavigationUI.navigateUp(navControllerCanguro, mAppBarConfigurationCanguro)
                || super.onSupportNavigateUp();
    }

    private void cargarFragment(Fragment fragment){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction=fm.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment_canguro, fragment);
        fragmentTransaction.commit();
    }
}