package com.bbsitter.bbsitter.OpcionesMenu.Perfil;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.Adaptadores.MisHijosAdapter;
import com.bbsitter.bbsitter.Clases.Hijos;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MiPerfilFamiliaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MiPerfilFamiliaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private CircleImageView fotoPerfilFamilia;
    private TextView tvNombrePerfilFamilia, tvDescripcionPerfilFamilia;
    private MaterialButton btnDireccionPerfilFamilia, btnAnadirHijo, btnEditarPerfil;

    private RecyclerView recyclerViewHijosPerfilFamilia;
    private MisHijosAdapter mAdapter;

    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    private String uid;

    public MiPerfilFamiliaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MiPerfilFamiliaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MiPerfilFamiliaFragment newInstance(String param1, String param2) {
        MiPerfilFamiliaFragment fragment = new MiPerfilFamiliaFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        /*Firebase Auth y BBDD*/
        mAuth = FirebaseAuth.getInstance();
        bbdd = FirebaseFirestore.getInstance();

        View view = inflater.inflate(R.layout.fragment_mi_perfil_familia, container, false);

        fotoPerfilFamilia = view.findViewById(R.id.imagenPerfilFamilia);
        tvNombrePerfilFamilia = view.findViewById(R.id.tvNombrePerfilFamilia);
        btnDireccionPerfilFamilia = view.findViewById(R.id.btnDireccionPerfilFamilia);
        tvDescripcionPerfilFamilia = view.findViewById(R.id.tvDescripcionPerfilFamilia);


        btnAnadirHijo = view.findViewById(R.id.btnAnadirHijo);
        btnEditarPerfil = view.findViewById(R.id.btnEditarPerfilFamilia);

        cargarDatosPerfilFamilia();


        final String uid = mAuth.getCurrentUser().getUid();

        btnAnadirHijo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CrearHijoFragment crearHijoFragment = new CrearHijoFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, crearHijoFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        btnEditarPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                EditarPerfilFragment editarPerfilFragment = new EditarPerfilFragment();
                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, editarPerfilFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });



        recyclerViewHijosPerfilFamilia = view.findViewById(R.id.recyclerViewHijosPerfilFamilia);
        recyclerViewHijosPerfilFamilia.setLayoutManager(new LinearLayoutManager(getContext()));
        bbdd = FirebaseFirestore.getInstance();

        Query query = bbdd.collection("hijos").whereEqualTo("uid", uid);

        FirestoreRecyclerOptions<Hijos> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Hijos>()
                .setQuery(query, Hijos.class).build();


        //mAdapter = new HijosAdapter(firestoreRecyclerOptions);
        mAdapter = new MisHijosAdapter(firestoreRecyclerOptions)
        {
            @Override
            protected void onBindViewHolder(@NonNull final MisHijosAdapter.ViewHolder holder, int position, @NonNull Hijos hijos) {

                holder.getNombre().setText(hijos.getNombre());
                holder.getEdad().setText(hijos.getEdad() + " años");
                holder.getOtrosDatos().setText(hijos.getOtrosDatos());

                final String idHijo = hijos.getIdHijo();

                        holder.getBtnBorrarHijo().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.getBtnBorrarHijo().playAnimation();

                        MaterialAlertDialogBuilder builder =new MaterialAlertDialogBuilder(getContext(), R.style.MyMaterialAlertDialog);
                        builder.setTitle("Eliminar mi hijo/a");
                        builder.setMessage("¿Estás seguro/a de que quieres eliminar tu hijo/a?");
                        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        builder.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //ELIMINAMOS ANUNCIO
                                bbdd.collection("hijos")
                                        .document(idHijo)
                                        .delete();
                            }
                        });
                        builder.show();

                    }
                });

            }
        };
        mAdapter.notifyDataSetChanged();
        recyclerViewHijosPerfilFamilia.setAdapter(mAdapter);

        return view;
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