package com.mapzen.tangramdemos;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;
import com.mapzen.tangram.SceneUpdate;
import com.mapzen.tangram.networking.HttpHandler;
import com.mapzen.tangram.viewholder.GLViewHolderFactory;
import com.mapzen.tangram.viewholder.TextureViewHolderFactory;

import java.util.ArrayList;
import java.util.List;

public class TranslucentMapActivity extends AppCompatActivity implements MapView.MapReadyCallback {

    // MapController is the main class used to interact with a Tangram map.
    MapController map;

    // MapView is the View used to display the map.
    MapView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translucency);

        // Set up back button to return to the demo list.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // Our MapView is declared in the layout file.
        view = (MapView)findViewById(R.id.map);

        // Lifecycle events from the Activity must be forwarded to the MapView.
        view.onCreate(savedInstanceState);

        // Create an HttpHandler to cache map tiles.
        HttpHandler httpHandler = new CachingHttpHandler(getExternalCacheDir());

        // Create a factory to build a TextureView with translucency enabled.
        GLViewHolderFactory factory = new TextureViewHolderFactory(true);

        // Start a background process to set up the map.
        view.getMapAsync(this, factory, httpHandler);
    }

    @Override
    public void onMapReady(MapController mapController) {
        // We receive a MapController object in this callback when the map is ready for use.
        map = mapController;

        // Set our API key as a scene update.
        List<SceneUpdate> updates = new ArrayList<>();
        updates.add(new SceneUpdate("global.sdk_api_key", BuildConfig.NEXTZEN_API_KEY));

        // Update the scene background color to be translucent.
        updates.add(new SceneUpdate("scene.background.color", "[0, 0, 0, 0]"));

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
