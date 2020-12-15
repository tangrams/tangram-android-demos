package com.mapzen.tangramdemos;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;
import com.mapzen.tangram.SceneUpdate;

import java.util.ArrayList;
import java.util.List;

public class SimpleMapActivity extends AppCompatActivity implements MapView.MapReadyCallback {

    // MapController is the main class used to interact with a Tangram map.
    MapController map;

    // MapView is the View used to display the map.
    MapView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simplemap);

        // Set up back button to return to the demo list.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Our MapView is declared in the layout file.
        view = (MapView)findViewById(R.id.map);

        // Lifecycle events from the Activity must be forwarded to the MapView.
        view.onCreate(savedInstanceState);

        // This starts a background process to set up the map.
        view.getMapAsync(this);
    }

    @Override
    public void onMapReady(MapController mapController) {
        // We receive a MapController object in this callback when the map is ready for use.
        map = mapController;

        // Set our API key as a scene update.
        List<SceneUpdate> updates = new ArrayList<>();
        updates.add(new SceneUpdate("global.sdk_api_key", BuildConfig.NEXTZEN_API_KEY));

        map.loadSceneFileAsync("bubble-wrap/bubble-wrap-style.yaml", updates);
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
