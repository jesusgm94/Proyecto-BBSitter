package com.bbsitter.bbsitter.OpcionesMenuCanguro;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bbsitter.bbsitter.OpcionesMenu.Chats.RoomChatFamiliaFragment;
import com.bbsitter.bbsitter.OpcionesMenuCanguro.Chats.ChatsCanguroFragment;
import com.bbsitter.bbsitter.OpcionesMenuCanguro.Inicio.MapsFragmentCanguros;
import com.bbsitter.bbsitter.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class PerfilCanguroFragment extends Fragment {

    private PerfilCanguroViewModel mViewModel;

    private TextView tvPrecioHoraPerfilCanguro,tvExperienciaPerfilCanguro,tvNombrePerfilCanguro,tvEdadCanguro,tvDescripcionPerfilCanguro;
    private CircleImageView imagenPerfilCanguro;

    /*Movidas de Firebase*/
    private FirebaseAuth mAuth;
    private FirebaseFirestore bbdd;

    private MaterialButton btnDireccion;
    private String uid;

    private ExtendedFloatingActionButton btnChat;


    public PerfilCanguroFragment() {}

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

        //Recogemos el uid de la familia de ListaCanguroFragment
        Bundle data = this.getArguments();
        if(data != null){
            uid = data.getString("uid");
        }

        cargarDatosCanguro();

        btnChat = view.findViewById(R.id.btnChat);

        btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                getActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.nav_host_fragment, new RoomChatFamiliaFragment())
                        .addToBackStack(null)
                        .commit();

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

    private void cargarDatosCanguro()
    {

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
                                String descripcionCanguro = document.get("descripcion").toString();
                                String direccionCanguro = document.get("direccion").toString();
                                String edadCanguro = " ( " + document.get("edad").toString() + " años)";
                                String experienciaCanguro = document.get("experiencia").toString();
                                String precioHora = document.get("precioHora").toString() + "€";
                                String imagenCanguro = document.get("img").toString();

                                //Agrega una nueva imagen desde una url usando Picasso.
                                Picasso.get().load(imagenCanguro).into(imagenPerfilCanguro);

                                //Agrega nuevo nombre
                                tvNombrePerfilCanguro.setText(nombreCanguro);
                                tvDescripcionPerfilCanguro.setText(descripcionCanguro);
                                btnDireccion.setText(direccionCanguro);
                                tvEdadCanguro.setText(edadCanguro);
                                tvExperienciaPerfilCanguro.setText(experienciaCanguro);
                                tvPrecioHoraPerfilCanguro.setText(precioHora);


                            }
                        } else {
                            Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}