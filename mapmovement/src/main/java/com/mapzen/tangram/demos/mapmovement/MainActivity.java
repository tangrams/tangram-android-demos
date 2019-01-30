package com.mapzen.tangram.demos.mapmovement;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;

import com.mapzen.tangram.CameraPosition;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;
import com.mapzen.tangram.SceneUpdate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MapView.MapReadyCallback {

    MapController map;
    MapView view;

    // Just for this demo, we define an enum type called 'Landmark' that helps control the map view.
    public enum Landmark {
        WORLD_TRADE_CENTER(-74.012477, 40.712454, 16f, (float)Math.toRadians(45), (float)Math.toRadians(-15)),
        EMPIRE_STATE_BUILDING(-73.986431, 40.748275, 16f, (float)Math.toRadians(65.25), (float)Math.toRadians(85)),
        CENTRAL_PARK_ZOO(-73.963918, 40.779414, 14.75f, (float)Math.toRadians(36), (float)Math.toRadians(-36)),
        ;

        Landmark(double lng, double lat, float z, float t, float r) {
            camera = new CameraPosition();
            camera.longitude = lng;
            camera.latitude = lat;
            camera.zoom = z;
            camera.tilt = t;
            camera.rotation = r;
        }

        CameraPosition camera;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (MapView)findViewById(R.id.map);
        view.onCreate(savedInstanceState);
        view.getMapAsync(this);
    }

    @Override
    public void onMapReady(MapController mapController) {
        map = mapController;

        // Set our API key as a scene update.
        List<SceneUpdate> updates = new ArrayList<>();
        updates.add(new SceneUpdate("global.sdk_api_key", BuildConfig.NEXTZEN_API_KEY));

        map.loadSceneFileAsync("bubble-wrap/bubble-wrap-style.yaml", updates);
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        if (!checked) {
            return;
        }

        switch(view.getId()) {
            case R.id.radio_wtc:
                goToLandmark(Landmark.WORLD_TRADE_CENTER);
                break;
            case R.id.radio_esb:
                goToLandmark(Landmark.EMPIRE_STATE_BUILDING);
                break;
            case R.id.radio_cpz:
                goToLandmark(Landmark.CENTRAL_PARK_ZOO);
                break;
        }
    }

    public void goToLandmark(Landmark landmark) {

        if (map == null) {
            return;
        }

        int duration = 2000; // Milliseconds

        // We use the position, zoom, tilt, and rotation of the Landmark to move the camera over time.
        // Different types of "easing" are available to make the transition smoother or sharper.
        map.flyToCameraPosition(landmark.camera, duration, null);
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
