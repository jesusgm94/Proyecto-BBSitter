package com.bbsitter.bbsitter.OpcionesMenuCanguro.Inicio;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.bbsitter.bbsitter.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import es.dmoral.toasty.Toasty;

public class MapsFragmentCanguros extends Fragment {

    private LocationManager ubicacion;

    FirebaseFirestore bbdd = FirebaseFirestore.getInstance();


    // Toca poner los marcadores de los canguros que salgan en la lista

    private OnMapReadyCallback callback = new OnMapReadyCallback() {

        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        @Override
        public void onMapReady(GoogleMap googleMap) {

            ubicacion = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{

                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION

                },1000);
            }

            Location loc = ubicacion.getLastKnownLocation(LocationManager.GPS_PROVIDER);

            final GoogleMap miGoogleMap = googleMap;

            // Lo primero será obtener nuestra ubicacion actual y poner nuestro marcador para despues recorrer nuestra base de datos de canguro para que los situe en  el mapa
            LatLng MIUBICACION = new LatLng(loc.getLatitude(), loc.getLongitude());


            googleMap.addMarker(new MarkerOptions().position(MIUBICACION).title("Mi ubicacion"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(MIUBICACION));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MIUBICACION, 15));
            googleMap.getUiSettings().setZoomControlsEnabled(true);


            // Lo segundo será recorrrer nuetro canguros y pintar cada uno, con su marcador, en el mapa.

            bbdd.collection("canguros")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {

                                for (QueryDocumentSnapshot document : task.getResult()) {

                                    // Object localizacionCanguro = document.getData().get("Localizacion");

                                    String nombreCanguro = document.get("nombre").toString();
                                    String urlFotoCanguro = document.get("img").toString();
                                    Double Latitud = document.getDouble("latitud");
                                    Double Longitud = document.getDouble("longitud");

                                    /*
                                    CircleImageView fotoCanguro = null;
                                    Picasso.get().load(urlFotoCanguro).into(fotoCanguro);
                                    */

                                    LatLng ubicacionCanguro = new LatLng(Latitud, Longitud);
                                    miGoogleMap.addMarker(new MarkerOptions().position(ubicacionCanguro).title(nombreCanguro));
                                }

                            } else {

                                Toasty.error(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }
    };

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_maps_canguros, container, false);


        // ubicacion = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        super.onViewCreated(view, savedInstanceState);

        SupportMapFragment mapFragment =  (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);

        }
    }
}