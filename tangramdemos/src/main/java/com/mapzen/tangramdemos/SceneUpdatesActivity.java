package com.mapzen.tangramdemos;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;
import com.mapzen.tangram.SceneUpdate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SceneUpdatesActivity extends AppCompatActivity implements MapView.MapReadyCallback {

    MapController map;
    MapView view;

    SceneUpdate apiKeySceneUpdate = new SceneUpdate("global.sdk_api_key", BuildConfig.NEXTZEN_API_KEY);
    String sceneFilePath = "bubble-wrap/bubble-wrap-style.yaml";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sceneupdates);

        // Set up back button to return to the demo list.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        view = (MapView)findViewById(R.id.map);
        view.onCreate(savedInstanceState);
        view.getMapAsync(this, new CachingHttpHandler(getExternalCacheDir()));

    }

    public void onCheckBoxClicked(View view) {
        CheckBox checkbox = (CheckBox)view;
        String visible = checkbox.isChecked() ? "true" : "false";
        String name = (String)checkbox.getText();

        if (map == null) {
            return;
        }

        // Scene updates are written to match the structure of the scene file.
        // Our check boxes have the same names as layers in our scene, so we'll use the names to
        // turn layers on and off individually using the 'visible' property.
        updateScene(new SceneUpdate("layers." + name + ".visible", visible));
    }

    private void updateScene(SceneUpdate... updates) {
        List<SceneUpdate> updateList = new ArrayList<>(Arrays.asList(updates));

        // Always apply the API key to the scene as an update.
        updateList.add(apiKeySceneUpdate);

        map.loadSceneFileAsync(sceneFilePath, updateList);
    }

    @Override
    public void onMapReady(MapController mapController) {
        map = mapController;

        updateScene();
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
