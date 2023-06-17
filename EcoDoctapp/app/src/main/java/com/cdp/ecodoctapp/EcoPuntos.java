package com.cdp.ecodoctapp;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.os.Bundle;

import com.cdp.ecodoctapp.entity.Ecopunto;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.cdp.ecodoctapp.databinding.ActivityEcoPuntosBinding;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EcoPuntos extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityEcoPuntosBinding binding;

    private DatabaseReference ecopuntosRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityEcoPuntosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Obtén la referencia a la lista de ecopuntos en la base de datos
        ecopuntosRef = FirebaseDatabase.getInstance().getReference("ecopuntos");

        // Obtiene el fragmento del mapa y llama a onMapReady cuando esté listo
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */


/*
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Agregar marcadores
        addMarker(-31.4051755, -64.1815744, "Blvd. Las Heras 150");
        addMarker(-31.4241266, -64.1927561, "Dr. T. Achával Rodríguez 345");
        addMarker(-31.4163436, -64.1916736, "General Simón Bolívar & Caseros");
        addMarker(-31.4375093, -64.1888715, "Comedor Universitario");
        addMarker(-31.43909380000001, -64.1882798, "Facultad de Odontología F.O. | U.N.C.");
        addMarker(-31.4506512, -64.1804162, "Elías Yofre 1050");
        addMarker(-31.4308354, -64.1733976, "Teatro Griego");
        addMarker(-31.4093422, -64.19576359999999, "Plaza Colon");
        addMarker(-31.4236477, -64.1860646, "Buen Pastor");
        addMarker(-31.4037967, -64.21803229999999, "Plaza Jerónimo del Barco");
        addMarker(-31.3916664, -64.18484, "Plaza Rivadavia");
        addMarker(-31.3630577, -64.2827885, "Valle escondido");
        addMarker(-31.3837714, -64.2276524, "Parque de Las Naciones");
        addMarker(-31.3403527, -64.2568273, "Ecopunto Ricardo rojas");
        addMarker(-31.3733197, -64.2558541, "Parque del Chateau");
        addMarker(-31.41436799999999, -64.1703667, "Plaza Alberdi");
        addMarker(-31.4095618, -64.1702906, "JOSE MARIA PAZ");
        addMarker(-31.4326208, -64.1873411, "Parque de Las Tejas");
        addMarker(-31.423388, -64.147803, "CPC N°12 Centro Cultural San Vicente");
        addMarker(-31.3419414, -64.2899999, "Recta martinoli");
        addMarker(-31.4295382, -64.2115548, "Ruta 20");
        addMarker(-31.3772087, -64.21474959999999, "Plaza Gendarmería Nacional Argentina");
        addMarker(-31.3639509, -64.1472451, "Centro de transferencias de residuos Rancagua");

        // Mover la cámara a la ubicación inicial
        LatLng initialLocation = new LatLng(-31.4051755, -64.1815744);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 12));
    }

    private void addMarker(double latitude, double longitude, String title) {
        LatLng position = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(position).title(title));
    }
}


 */

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Configura el nivel de zoom y la posición inicial del mapa
        LatLng initialLocation = new LatLng(-31.4051755, -64.1815744);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(initialLocation, 12));

        // Escucha los cambios en la lista de ecopuntos
        ecopuntosRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mMap.clear(); // Limpia los marcadores existentes en el mapa

                // Itera sobre los ecopuntos en la base de datos
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Ecopunto ecopunto = snapshot.getValue(Ecopunto.class);
                    if (ecopunto != null) {
                        double lat = parseLatitude(ecopunto.getWkt());
                        double lng = parseLongitude(ecopunto.getWkt());

                        // Agrega un marcador al mapa para cada ecopunto
                        LatLng location = new LatLng(lat, lng);
                        mMap.addMarker(new MarkerOptions().position(location).title(ecopunto.getNombre()));
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Maneja los errores de la base de datos
            }
        });
    }

    private double parseLatitude(String wkt) {
        // Implementa la lógica para extraer la latitud del formato WKT
        // Ejemplo: "-64.1815744 -31.4051755"
        String[] parts = wkt.split(" ");
        if (parts.length == 2) {
            try {
                return Double.parseDouble(parts[1]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }

    private double parseLongitude(String wkt) {
        // Implementa la lógica para extraer la longitud del formato WKT
        // Ejemplo: "-64.1815744 -31.4051755"
        String[] parts = wkt.split(" ");
        if (parts.length == 2) {
            try {
                return Double.parseDouble(parts[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
}