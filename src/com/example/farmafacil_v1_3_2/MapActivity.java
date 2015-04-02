package com.example.farmafacil_v1_3_2;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.widget.Toast;

import com.example.farmafacil_v1_3_2.model.Farmacia;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapActivity extends Activity{
	// Google Map
    private GoogleMap googleMap;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
 
        try {
        	initializeUI();
            // inicializa o mapa
            initilizeMap();
 
        } catch (Exception e) {
            e.printStackTrace();
        }
 
    }
    
    private void initializeUI() {
		if ((getIntent() != null) && (getIntent().getSerializableExtra("selectedItems") != null)) {
			//TALVEZ CHAMAR O SERVICO AQUI
		}
	}

	/**
     * function to load map. If map is not created it will create it for you
     * */
    private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
 
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(this,
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
            
			/*
			* Hardcoded just for presentation, web crawler wasn't getting the location of
			* the drugstores yet, so this would be the next step: request the location the
			* same way , medicine prices are requested and save these values
			*/
            ArrayList <String> latitudes = new ArrayList <String>();
            latitudes.add("-23.610542");
            latitudes.add("-23.583663");
            latitudes.add("-23.589115");
            latitudes.add("-23.583528");
            latitudes.add("-23.606979");
            latitudes.add("-23.568972");
            latitudes.add("-23.55966");
            latitudes.add("-23.522722");
            latitudes.add("-23.561043");
            latitudes.add("-23.566622");
            
            ArrayList <String> longitudes = new ArrayList <String>();
            longitudes.add("-46.727634");
            longitudes.add("-46.687334");
            longitudes.add("-46.67634");
            longitudes.add("-46.678129");
            longitudes.add("-46.667561");
            longitudes.add("-46.687468");
            longitudes.add("-46.695783");
            longitudes.add("-46.705609");
            longitudes.add("-46.680191");
            longitudes.add("-46.66468");
            for (int i = 0; i < longitudes.size(); i++) {

            	  double lati = Double.parseDouble(latitudes.get(i));
            	  double longLat = Double.parseDouble(longitudes.get(i));
            	  
            	  googleMap.addMarker(new MarkerOptions().position(new LatLng(lati,longLat)).title("Surprise"+i));
            }
            
            /* move camera to a specific position */
            
            // creates camera
            CameraPosition cameraPosition = new CameraPosition.Builder().target(
                    new LatLng(-23.554906, -46.729734)).zoom(12).build();
            
            // move camera
            googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
            googleMap.setMyLocationEnabled(true);
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
 
}
