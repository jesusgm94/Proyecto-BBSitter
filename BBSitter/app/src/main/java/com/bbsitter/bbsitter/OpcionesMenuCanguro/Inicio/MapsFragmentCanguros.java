package com.bbsitter.bbsitter.OpcionesMenuCanguro.Inicio;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapRegionDecoder;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bbsitter.bbsitter.Clases.Canguro;
import com.bbsitter.bbsitter.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import es.dmoral.toasty.Toasty;

import static android.content.Context.LOCATION_SERVICE;
import static com.firebase.ui.auth.AuthUI.getApplicationContext;

public class MapsFragmentCanguros extends Fragment {

    private LocationManager locationManager;

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

            final GoogleMap miGoogleMap = googleMap;

            locationManager = (LocationManager) getActivity().getSystemService(LOCATION_SERVICE);

            // Lo primero será obtener nuestra ubicacion actual y poner nuestro marcador para despues recorrer nuestra base de datos de canguro para que los situe en  el mapa

            // Obtenemos nuestra ubicacion
            // Usamo un try por si no tenemos activado el GPS
            /*
            try {
                Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            }
            catch(Exception e){
                alertDialogActivarGPS();
            }
            */

            if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(getActivity(), new String[]{

                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION

                },1000);
            }


            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    showAlert();
            }

            // Obtener nuestra ubicacion
            /*
            Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            LatLng MIUBICACION = new LatLng(loc.getLatitude(), loc.getLongitude());
            */
            try{

                Location loc = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                Toast.makeText(getContext(), loc.getLatitude() + ", " + loc.getLongitude(), Toast.LENGTH_SHORT).show();
                LatLng MIUBICACION = new LatLng(loc.getLatitude(), loc.getLongitude());


                // Ponemmos un marcador en nuestra ubicacion
                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.puntomarcadorubicacion);
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 130, 130, false);


                Marker marcador = miGoogleMap.addMarker(new MarkerOptions()
                        .position(MIUBICACION)
                        .title("Mi ubicación"));

                marcador.setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(MIUBICACION));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MIUBICACION, 15));
                googleMap.getUiSettings().setZoomControlsEnabled(true);


            }catch (Exception e)
            {

                LatLng MIUBICACION = new LatLng(40.490797, -3.341996);

                // Ponemmos un marcador en nuestra ubicacion
                BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.puntomarcadorubicacion);
                Bitmap b = bitmapdraw.getBitmap();
                Bitmap smallMarker = Bitmap.createScaledBitmap(b, 130, 130, false);


                Marker marcador = miGoogleMap.addMarker(new MarkerOptions()
                        .position(MIUBICACION)
                        .title("Mi ubicación"));

                marcador.setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));

                googleMap.moveCamera(CameraUpdateFactory.newLatLng(MIUBICACION));
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(MIUBICACION, 15));
                googleMap.getUiSettings().setZoomControlsEnabled(true);
            }

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
                                    String uidCanguro = document.getString("uid");

                                    /*
                                    CircleImageView fotoCanguro = null;
                                    Picasso.get().load(urlFotoCanguro).into(fotoCanguro);
                                    */

                                    BitmapDrawable bitmapdraw = (BitmapDrawable) getResources().getDrawable(R.drawable.marcadorbbsitter);
                                    Bitmap b = bitmapdraw.getBitmap();
                                    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 110, 110, false);


                                    LatLng ubicacionCanguro = new LatLng(Latitud, Longitud);
                                    Marker marcador = miGoogleMap.addMarker(new MarkerOptions()
                                                                            .position(ubicacionCanguro)
                                                                            .title(nombreCanguro));
                                    marcador.setIcon(BitmapDescriptorFactory.fromBitmap(smallMarker));

                                    // Metemos dentro del TAG del marcador el iud del canguro para acceder a sus datos (firestore) y mostrarlos cuando hagan click en el marcador
                                    marcador.setTag(uidCanguro);

                                }

                            } else {

                                Toasty.error(getContext(), "Error", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            miGoogleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    Toasty.error(getContext(), "idmarker" + marker.getTag(), Toast.LENGTH_SHORT).show();
                    return false;
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
    private Location getLastKnownLocation() {
        Location l=null;
        @SuppressLint("RestrictedApi") LocationManager mLocationManager = (LocationManager)getApplicationContext().getSystemService(LOCATION_SERVICE);
        List<String> providers = mLocationManager.getProviders(true);
        Location bestLocation = null;
        for (String provider : providers) {
            if(ContextCompat.checkSelfPermission(getContext(),Manifest.permission.ACCESS_FINE_LOCATION)==PackageManager.PERMISSION_GRANTED) {
                l = mLocationManager.getLastKnownLocation(provider);
            }
            if (l == null) {
                continue;
            }
            if (bestLocation == null || l.getAccuracy() < bestLocation.getAccuracy()) {
                bestLocation = l;
            }
        }
        return bestLocation;
    }



    private void showAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Enable Location")
                .setMessage("Su ubicación esta desactivada.\npor favor active su ubicación " +
                        "usa esta app")
                .setPositiveButton("Configuración de ubicación", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

}