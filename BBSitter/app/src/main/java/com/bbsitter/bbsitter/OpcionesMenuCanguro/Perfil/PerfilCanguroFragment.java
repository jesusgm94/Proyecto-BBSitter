package com.bbsitter.bbsitter.OpcionesMenuCanguro.Perfil;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.airbnb.lottie.LottieAnimationView;
import com.bbsitter.bbsitter.Main.MainActivity;
import com.bbsitter.bbsitter.OpcionesMenu.Chats.RoomChatFamiliaFragment;
import com.bbsitter.bbsitter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Set;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilCanguroFragment extends Fragment {

    private PerfilCanguroViewModel mViewModel;

    private TextView tvPrecioHoraPerfilCanguro, tvExperienciaPerfilCanguro, tvNombrePerfilCanguro, tvEdadCanguro, tvDescripcionPerfilCanguro;
    private CircleImageView imagenPerfilCanguro;

    /*Movidas de Firebase*/
    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    private MaterialButton btnDireccion;
    private String uidCanguro, emailCanguro, telefonoCanguro;

    private LottieAnimationView lottieFav;
    private ChipGroup chipGroupPluses, chipGroupIdiomas, chipGroupPreferencias;

    private ExtendedFloatingActionButton btnTelefonoPerfilCanguro, btnEmailPerfilCanguro;
    ImageButton btnCompartir;

    private RatingBar ratingBar;


    public PerfilCanguroFragment() {
    }

    public static PerfilCanguroFragment newInstance() {
        return new PerfilCanguroFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.perfil_canguro_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();

        imagenPerfilCanguro = view.findViewById(R.id.imagenPerfilCanguro);
        tvNombrePerfilCanguro = view.findViewById(R.id.tvNombrePerfilCanguro);
        tvDescripcionPerfilCanguro = view.findViewById(R.id.tvDescripcionPerfilCanguro);
        tvEdadCanguro = view.findViewById(R.id.tvEdadCanguro);
        tvPrecioHoraPerfilCanguro = view.findViewById(R.id.tvPrecioHoraPerfilCanguro);
        tvExperienciaPerfilCanguro = view.findViewById(R.id.tvExperienciaPerfilCanguro);
        btnDireccion = view.findViewById(R.id.btnDireccionPerfilCanguro);
        btnCompartir = view.findViewById(R.id.btnCompartir);

        lottieFav = view.findViewById(R.id.lottieFavorito);
        lottieFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lottieFav.playAnimation();
            }
        });

        ratingBar = view.findViewById(R.id.itemRatingBarCanguroPerfil);

        // Chips Groups
        chipGroupPluses = view.findViewById(R.id.chipgroupPluses);
        chipGroupIdiomas = view.findViewById(R.id.chipgroupIdiomas);
        chipGroupPreferencias = view.findViewById(R.id.chipgroupPrefEdades);

        //Recogemos el uid de la familia de ListaCanguroFragment
        Bundle data = this.getArguments();
        if (data != null) {
            uidCanguro = data.getString("uid");
            emailCanguro = data.getString("email");
            telefonoCanguro = data.getString("telefono");

        }


        cargarDatosCanguro();

        btnTelefonoPerfilCanguro = view.findViewById(R.id.btnTelefonoPerfilCanguro);
        btnEmailPerfilCanguro = view.findViewById(R.id.btnEmailPerfilCanguro);

        btnCompartir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ya programarás esto algún dia", Snackbar.LENGTH_LONG)
                        .setAction("Dont worry", null).show();
            }
        });


        // BOTON CHAT del anuncio
        btnTelefonoPerfilCanguro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                //Nota: en versiones anteriores a Android 6.0 bastaba con especificar en AndroidManifest.xml el permiso
                // mediante: <uses-permission android:name="android.permission.CALL_PHONE"/>
                //En las versiones posteriores hay que solicitar explíctamente al usuario si quiere conceder permiso para ello
                //Algo similar ocurre con el acceso a la geolocalización u otros servicios

                //Verificamos si tenemos los permisos necesarios para enviar SMS
                int permisoLlamada = ContextCompat.checkSelfPermission(getContext(), Manifest.permission.CALL_PHONE);

                if (permisoLlamada != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE}, 225);
                } else {
                    MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext(), R.style.MyMaterialAlertDialog);
                    builder.setTitle("Llamar");
                    builder.setMessage("Va a llamar a este usuario. Puede interferir en su factura de teléfono. ¿Quieres llamar?");
                    builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {


                            if (!TextUtils.isEmpty((telefonoCanguro))) {
                                String dial = "tel:" + telefonoCanguro; //Se tiene que poner literalmente esto
                                startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(dial)));
                                Toast.makeText(getContext(), "Llamando.", Toast.LENGTH_LONG).show();
                            } else {
                                Snackbar.make(view, "El usuario no tiene telefono. Contacte por email.", Snackbar.LENGTH_LONG)
                                        .setAction("Dont worry", null).show();
                            }


                        }
                    });
                    builder.show();

                }

            }
        });

        btnEmailPerfilCanguro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", emailCanguro, null));
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Necesito canguro");


                getContext().startActivity(Intent.createChooser(emailIntent, null));

            }
        });

        return view;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PerfilCanguroViewModel.class);
        // TODO: Use the ViewModel
    }

    private void cargarDatosCanguro() {

        bbdd.collection("canguros")
                .whereEqualTo("uid", uidCanguro)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {


                                //Recogemos los datos de la base de datos
                                String nombreCanguro = document.get("nombre").toString() + " " + document.get("apellidos").toString();
                                String descripcionCanguro = document.get("descripcion").toString();
                                String direccionCanguro = document.get("direccion").toString();
                                String edadCanguro = " ( " + document.get("edad").toString() + " años)";
                                String experienciaCanguro = document.get("experiencia").toString();
                                String precioHora = document.get("precioHora").toString() + "€";
                                String imagenCanguro = document.get("img").toString();

                                String ratingCang = document.get("rating").toString();
                                int ratingCanguroInt = Integer.parseInt(ratingCang);


                                // Añadir Chips Pluses
                                HashMap<String, Boolean> pluses = (HashMap<String, Boolean>) document.get("pluses");
                                Set<String> listaKeysPluses = pluses.keySet();

                                for (String plus : listaKeysPluses) {
                                    Chip chipPlus = new Chip(getContext());
                                    chipPlus.setText(plus.toString());
                                    chipPlus.setTextColor(Color.GRAY);
                                    chipPlus.setChipBackgroundColorResource(R.color.moradooscuro);
                                    chipPlus.setChipStrokeColorResource(R.color.colorPrimaryDark);
                                    chipPlus.setChipStrokeWidth(3);
                                    chipGroupPluses.addView(chipPlus);
                                }

                                // Añadir Chips Idiomas
                                HashMap<String, Boolean> idiomas = (HashMap<String, Boolean>) document.get("idiomas");
                                Set<String> listaKeysIdiomas = idiomas.keySet();

                                for (String idioma : listaKeysIdiomas) {
                                    Chip chipIdioma = new Chip(getContext());
                                    chipIdioma.setText(idioma.toString());
                                    chipIdioma.setTextColor(Color.GRAY);
                                    chipIdioma.setChipBackgroundColorResource(R.color.moradooscuro);
                                    chipIdioma.setChipStrokeColorResource(R.color.colorPrimaryDark);
                                    chipIdioma.setChipStrokeWidth(3);
                                    chipGroupIdiomas.addView(chipIdioma);
                                }

                                // Añadir Chips Preferencia Edades
                                HashMap<String, Boolean> prefEdades = (HashMap<String, Boolean>) document.get("preferenciaEdades");
                                Set<String> listaKeysprefEdades = prefEdades.keySet();

                                for (String pref : listaKeysprefEdades) {
                                    Chip chipPrefEdades = new Chip(getContext());
                                    chipPrefEdades.setText(pref.toString());
                                    chipPrefEdades.setTextColor(Color.GRAY);
                                    chipPrefEdades.setChipBackgroundColorResource(R.color.moradooscuro);
                                    chipPrefEdades.setChipStrokeColorResource(R.color.colorPrimaryDark);
                                    chipPrefEdades.setChipStrokeWidth(3);
                                    chipGroupPreferencias.addView(chipPrefEdades);
                                }

                                //Agrega una nueva imagen desde una url usando Picasso.
                                Picasso.get().load(imagenCanguro).into(imagenPerfilCanguro);

                                //Agrega nuevo nombre
                                tvNombrePerfilCanguro.setText(nombreCanguro);
                                tvDescripcionPerfilCanguro.setText(descripcionCanguro);
                                btnDireccion.setText(direccionCanguro);
                                tvEdadCanguro.setText(edadCanguro);
                                tvExperienciaPerfilCanguro.setText(experienciaCanguro);
                                tvPrecioHoraPerfilCanguro.setText(precioHora);

                                ratingBar.setNumStars(ratingCanguroInt);

                            }
                        } else {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void cargarFragment(Fragment fragment) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fm.beginTransaction();
        fragmentTransaction.replace(R.id.nav_host_fragment, fragment);
        fragmentTransaction.commit();
    }

}