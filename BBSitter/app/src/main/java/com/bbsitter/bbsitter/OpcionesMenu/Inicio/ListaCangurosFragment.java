package com.bbsitter.bbsitter.OpcionesMenu.Inicio;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bbsitter.bbsitter.Adaptadores.CanguroAdapter;
import com.bbsitter.bbsitter.Clases.Canguro;
import com.bbsitter.bbsitter.OpcionesMenuCanguro.Perfil.PerfilCanguroFragment;
import com.bbsitter.bbsitter.R;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.squareup.picasso.Picasso;

import java.util.Random;

import static android.content.Context.LOCATION_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaCangurosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaCangurosFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    LatLng MIUBICACION;


    private FirebaseFirestore bbdd;
    private RecyclerView recyclerViewListaCanguros;
    private CanguroAdapter mAdapter;


    public ListaCangurosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaCangurosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaCangurosFragment newInstance(String param1, String param2) {
        ListaCangurosFragment fragment = new ListaCangurosFragment();
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
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_lista_canguros, container, false);

        // OBETENMOS LA UBICACION PARA CALCULAR LA DISTANCIA DE LOS CANGUROS *************

        /*
        LocationManager locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{

                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION

            },1000);
        }

        final Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        MIUBICACION = new LatLng(loc.getLatitude(), loc.getLongitude());
        */



        // RECYCLER VIEW ****************

        recyclerViewListaCanguros = view.findViewById(R.id.recycler_ListaCanguros);
        recyclerViewListaCanguros.setLayoutManager(new LinearLayoutManager(getContext()));
        bbdd = FirebaseFirestore.getInstance();


        Query query = bbdd.collection("canguros").orderBy("fechaCreacionPerfil", Query.Direction.ASCENDING);

        FirestoreRecyclerOptions<Canguro> firestoreRecyclerOptions = new FirestoreRecyclerOptions.Builder<Canguro>()
                .setQuery(query, Canguro.class).build();

        //mAdapter = new CanguroAdapter(firestoreRecyclerOptions);
        mAdapter = new CanguroAdapter(firestoreRecyclerOptions) {


            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Canguro canguro) {

                // Poner FOTO
                String img = canguro.getImg();
                Picasso.get().load(img).into(holder.getImg());

                holder.getNombre().setText(canguro.getNombre());
                holder.getEdad().setText(String.valueOf(canguro.getEdad()) + " años");
                holder.getPrecioHora().setText(canguro.getPrecioHora() + " €");

                holder.getRatingBar().setRating(canguro.getRating());

                final String uid = canguro.getUid();
                final String email = canguro.getEmail();
                final String telefono = canguro.getTelefono();

                // Calculamos la distancia entre los dos puntos
                Location locCanguro= new Location("locCanguro");
                locCanguro.setLatitude(canguro.getLatitud());
                locCanguro.setLongitude(canguro.getLongitud());


                Location miLocalizacion = new Location("miLocalizacion");
                miLocalizacion.setLatitude(40.490764);
                miLocalizacion.setLongitude(-3.342020);

                // Obtenemos la distancia entre los dos puntos. Nos devuelve metros
                double distanciaCalculada = locCanguro.distanceTo(miLocalizacion);

                // Pasamos los metros obtenidos a Kms
                double kms = distanciaCalculada / 1000;
                //Con 2 decimales
                holder.getDistancia().setText(String.format("%.1f", kms) + " kms");
                

                // Obtenemos el cardview de itemCanguro que hemos instanciado en el onBindViewHolder de AdapterCangruo
                holder.getCardViewCanguro().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        //Llevamos el uid con un Bundle a PerfilCanguroFragment
                        PerfilCanguroFragment perfilCanguroFragment = new PerfilCanguroFragment();
                        Bundle data = new Bundle();
                        data.putString("uid", uid);
                        data.putString("email", email);
                        data.putString("telefono", telefono);
                        perfilCanguroFragment.setArguments(data);

                        getActivity().getSupportFragmentManager().beginTransaction()
                                .setCustomAnimations(R.anim.fui_slide_in_right, R.anim.fui_slide_out_left)
                                .replace(R.id.nav_host_fragment, perfilCanguroFragment)
                                .addToBackStack(null)
                                .commit();

                    }
                });

            }
        };

        mAdapter.notifyDataSetChanged();

        recyclerViewListaCanguros.setAdapter(mAdapter);


        return view;
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