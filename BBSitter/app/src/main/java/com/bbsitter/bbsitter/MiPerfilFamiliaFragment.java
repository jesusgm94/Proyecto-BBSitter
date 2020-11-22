package com.bbsitter.bbsitter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
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
    private MaterialButton btnDireccionPerfilFamilia;



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

        cargarDatosPerfilFamilia();

        // Inflate the layout for this fragment
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
}