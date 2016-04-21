package com.mapzen.tangram.demos.simplemap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;

public class MainActivity extends AppCompatActivity implements MapView.OnMapReadyCallback {

    MapController map;
    MapView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (MapView)findViewById(R.id.map);
        view.onCreate(savedInstanceState);
        view.getMapAsync(this, "scene.yaml");
    }

    @Override
    public void onMapReady(MapController mapController) {
        map = mapController;
    }

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
