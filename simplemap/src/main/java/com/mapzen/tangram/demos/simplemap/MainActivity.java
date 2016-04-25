package com.mapzen.tangram.demos.simplemap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;

public class MainActivity extends AppCompatActivity implements MapView.OnMapReadyCallback {

    // MapController is the main class used to interact with a Tangram map.
    MapController map;

    // MapView is the View used to display the map.
    MapView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Our MapView is declared in the layout file.
        view = (MapView)findViewById(R.id.map);

        // Lifecycle events from the Activity must be forwarded to the MapView.
        view.onCreate(savedInstanceState);

        // This starts a background process to set up the map.
        view.getMapAsync(this, "scene.yaml");
    }

    @Override
    public void onMapReady(MapController mapController) {
        // We receive a MapController object in this callback when the map is ready for use.
        map = mapController;
    }

    // Below are the remaining Activity lifecycle events that must be forwarded to our MapView.

    @Override
    public void onResume() {
        super.onResume();
        view.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        view.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        view.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        view.onLowMemory();
    }
}
