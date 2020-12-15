package com.mapzen.tangramdemos;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;
import com.mapzen.tangram.SceneUpdate;
import com.mapzen.tangram.TouchInput;

import java.util.ArrayList;
import java.util.List;

public class MultiMapActivity extends AppCompatActivity implements TouchInput.RotateResponder {

    // MapController is the main class used to interact with a Tangram map.
    MapController mapTop;
    MapController mapBottom;

    // MapView is the View used to display the map.
    MapView viewTop;
    MapView viewBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multimap);

        // Set up back button to return to the demo list.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Our MapView is declared in the layout file.
        viewTop = (MapView)findViewById(R.id.map_top);
        viewBottom = (MapView)findViewById(R.id.map_bottom);

        // Lifecycle events from the Activity must be forwarded to the MapView.
        viewTop.onCreate(savedInstanceState);
        viewBottom.onCreate(savedInstanceState);

        // This starts a background process to set up the map.
        viewTop.getMapAsync(new MapView.MapReadyCallback() {
            @Override
            public void onMapReady(@Nullable MapController mapController) {
                mapTop = mapController;
                MultiMapActivity.this.onMapReady(mapController);
            }
        });
        viewBottom.getMapAsync(new MapView.MapReadyCallback() {
            @Override
            public void onMapReady(@Nullable MapController mapController) {
                mapBottom = mapController;
                MultiMapActivity.this.onMapReady(mapController);
            }
        });
    }

    public void onMapReady(MapController mapController) {
        // Set our API key as a scene update.
        List<SceneUpdate> updates = new ArrayList<>();
        updates.add(new SceneUpdate("global.sdk_api_key", BuildConfig.NEXTZEN_API_KEY));

        //mapController.getTouchInput().setRotateResponder(this);

        mapController.loadSceneFileAsync("bubble-wrap/bubble-wrap-style.yaml", updates);
    }

    // Below are the remaining Activity lifecycle events that must be forwarded to our MapView.

    @Override
    public void onResume() {
        super.onResume();
        viewTop.onResume();
        viewBottom.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewTop.onPause();
        viewBottom.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewTop.onDestroy();
        viewBottom.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        viewTop.onLowMemory();
        viewBottom.onLowMemory();
    }

    /**
     * Called when a Rotation Gesture begins
     *
     * @return True if the event is consumed, false if the event should continue to propagate
     */
    @Override
    public boolean onRotateBegin() {
        return false;
    }

    /**
     * Called repeatedly while two touch points are rotated around a point
     *
     * @param x        The x screen coordinate of the center of rotation
     * @param y        The y screen coordinate of the center of rotation
     * @param rotation The change in rotation of the touch points relative to the previous
     *                 rotation event, in counter-clockwise radians
     * @return True if the event is consumed, false if the event should continue to propagate
     */
    @Override
    public boolean onRotate(float x, float y, float rotation) {
        return false;
    }

    /**
     * Called when Rotation Gesture ends
     *
     * @return True if the event is consumed, false if the event should continue to propagate
     */
    @Override
    public boolean onRotateEnd() {
        return false;
    }
}
