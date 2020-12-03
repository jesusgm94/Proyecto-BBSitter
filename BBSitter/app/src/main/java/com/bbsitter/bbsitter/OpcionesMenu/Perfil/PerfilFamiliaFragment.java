package com.bbsitter.bbsitter.OpcionesMenu.Perfil;

import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.Adaptadores.HijosAdapter;
import com.bbsitter.bbsitter.Clases.Hijos;
import com.bbsitter.bbsitter.ProgressBarCargando;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilFamiliaFragment extends Fragment {

    private PerfilFamiliaViewModel mViewModel;

    private CircleImageView imagenPerfilFamiliaCanguro;
    private TextView tvNombrePerfilFamiliaCanguro, tvDescripcionPerfilFamiliaCanguro;
    private MaterialButton btnDireccionPerfilFamiliaCanguro;
    private String uid;

    private RecyclerView recyclerViewHijosPerfilFamiliaCanguro;
    private HijosAdapter mAdapter;


    private FirebaseFirestore bbdd;


    public static PerfilFamiliaFragment newInstance() {
        return new PerfilFamiliaFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.perfil_familia_fragment, container, false);

        final ProgressBarCargando progressBarCargando = new ProgressBarCargando(getActivity());
        progressBarCargando.StarProgressBar();
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                progressBarCargando.finishProgressBar();


            }
        }, 1000);


        //Recogemos el uid de la familia de DetalleAnuncio
        Bundle data = this.getArguments();
        if(data != null){
            uid = data.getString("uid");
        }

        bbdd = FirebaseFirestore.getInstance();
        imagenPerfilFamiliaCanguro = view.findViewById(R.id.imagenPerfilFamiliaCanguro);
        tvNombrePerfilFamiliaCanguro = view.findViewById(R.id.tvNombrePerfilFamiliaCanguro);
        btnDireccionPerfilFamiliaCanguro = view.findViewById(R.id.btnDireccionPerfilFamiliaCanguro);
        tvDescripcionPerfilFamiliaCanguro = view.findViewById(R.id.tvDescripcionPerfilFamiliaCanguro);

        recyclerViewHijosPerfilFamiliaCanguro = view.findViewById(R.id.recyclerViewHijosPerfilFamiliaCanguro);

        cargarDatosFamilia();

        recyclerViewHijosPerfilFamiliaCanguro.setLayoutManager(new LinearLayoutManager(getContext()));
        bbdd = FirebaseFirestore.getInstance();

        Query query = bbdd.collection("hijos").whereEqualTo("uid", uid);

        FirestoreRecyclerOptions<Hijos> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Hijos>()
                .setQuery(query, Hijos.class).build();


        //mAdapter = new HijosAdapter(firestoreRecyclerOptions);
        mAdapter = new HijosAdapter(firestoreRecyclerOptions);
        mAdapter.notifyDataSetChanged();
        recyclerViewHijosPerfilFamiliaCanguro.setAdapter(mAdapter);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(PerfilFamiliaViewModel.class);
        // TODO: Use the ViewModel
    }

    private void cargarDatosFamilia()
    {
        bbdd.collection("familias")
                .whereEqualTo("uid", uid)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {

                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                //Recogemos los datos de la base de datos
                                String imagenFamilia = document.get("img").toString();
                                String nombreFamilia =  "Familia " + document.get("nombre").toString();
                                String direccionFamilia = document.get("direccion").toString();
                                String descripcionFamilia = document.get("descripcion").toString();

                                //Agrega una nueva imagen desde una url usando Picasso.
                                Picasso.get().load(imagenFamilia).into(imagenPerfilFamiliaCanguro);

                                //Agrega nuevo nombre
                                tvNombrePerfilFamiliaCanguro.setText(nombreFamilia);
                                btnDireccionPerfilFamiliaCanguro.setText(direccionFamilia);
                                tvDescripcionPerfilFamiliaCanguro.setText(descripcionFamilia);

                            }
                        } else {

                        }
                    }
                });

    }

    @Override
    public void onStart() {
        super.onStart();
        mAdapter.startListening();
    }

    public void onStop() {

        super.onStop();
        mAdapter.stopListening();
    }

}