package com.bbsitter.bbsitter.OpcionesMenuCanguro.Perfil;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bbsitter.bbsitter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public class MiPerfilCanguroFragment extends Fragment {

    private MiPerfilCanguroViewModel mViewModel;

    private CircleImageView fotoMiPerfilCanguro;
    private TextView tvNombreMiPerfilCanguro
            ,tvDescripcionMiPerfilCanguro
            ,tvPrecioHoraMiPerfilCanguro
            ,tvExperienciaMiPerfilCanguro
            ,tvEdadMiPerfilCanguro;

    private MaterialButton btnDireccionMiPerfilCanguro;
    private ExtendedFloatingActionButton btnEditarPerfil;

    private String uid;

    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    public static MiPerfilCanguroFragment newInstance() {
        return new MiPerfilCanguroFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.mi_perfil_canguro_fragment, container, false);

        /*Firebase Auth y BBDD*/
        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();

        fotoMiPerfilCanguro = view.findViewById(R.id.imagenMiPerfilCanguro);
        tvNombreMiPerfilCanguro = view.findViewById(R.id.tvNombreMiPerfilCanguro);
        tvDescripcionMiPerfilCanguro = view.findViewById(R.id.tvDescripcionMiPerfilCanguro);
        tvPrecioHoraMiPerfilCanguro = view.findViewById(R.id.tvPrecioHoraMiPerfilCanguro);
        tvExperienciaMiPerfilCanguro = view.findViewById(R.id.tvExperienciaMiPerfilCanguro);
        tvEdadMiPerfilCanguro = view.findViewById(R.id.tvEdadMiPerfilCanguro);
        btnDireccionMiPerfilCanguro = view.findViewById(R.id.btnDireccionMiPerfilCanguro);

        btnEditarPerfil = view.findViewById(R.id.btnEditarPerfilCanguro);

        cargarDatosPerfilCanguro();
        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Necesitas una intent para  editar el perfil", Snackbar.LENGTH_LONG)
                        .setAction("Necesitas una intent para  editar el perfil", null).show();
            }
        });

        return view;
    }

    private void cargarDatosPerfilCanguro()
    {
        uid = mAuth.getCurrentUser().getUid();


        bbdd.collection("canguros")
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //Recogemos los datos de la base de datos
                                String nombreCanguro =  document.get("nombre").toString() + " " + document.get("apellidos").toString();
                                String imagenCanguro = document.get("img").toString();
                                String direccionCanguro = document.get("direccion").toString();
                                String descripcionCanguro = document.get("descripcion").toString();
                                String precioHoraCanguro = document.get("precioHora").toString() + " €";
                                String edadCanguro = " (" + document.get("edad").toString() + " años)";
                                String experienciaCanguro = document.get("experiencia").toString();

                                //Agrega una nueva imagen desde una url usando Picasso.
                                Picasso.get().load(imagenCanguro).into(fotoMiPerfilCanguro);

                                //Agrega nuevo nombre
                                tvNombreMiPerfilCanguro.setText(nombreCanguro);
                                btnDireccionMiPerfilCanguro.setText(direccionCanguro);
                                tvDescripcionMiPerfilCanguro.setText(descripcionCanguro);
                                tvPrecioHoraMiPerfilCanguro.setText(precioHoraCanguro);
                                tvEdadMiPerfilCanguro.setText(edadCanguro);
                                tvExperienciaMiPerfilCanguro.setText(experienciaCanguro);

                            }
                        } else {

                        }
                    }
                });

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(MiPerfilCanguroViewModel.class);
        // TODO: Use the ViewModel
    }

}