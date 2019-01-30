package com.mapzen.tangram.demos.sceneupdates;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;
import com.mapzen.tangram.SceneUpdate;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MapView.MapReadyCallback {

    MapController map;
    MapView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (MapView)findViewById(R.id.map);
        view.onCreate(savedInstanceState);
        view.getMapAsync(this);

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
        List<SceneUpdate> updates = new ArrayList<>();
        updates.add(new SceneUpdate("layers." + name + ".visible", visible));
        map.updateSceneAsync(updates);
    }

    @Override
    public void onMapReady(MapController mapController) {
        map = mapController;

        // Set our API key as a scene update.
        List<SceneUpdate> updates = new ArrayList<>();
        updates.add(new SceneUpdate("global.sdk_api_key", BuildConfig.NEXTZEN_API_KEY));

        map.loadSceneFileAsync("bubble-wrap/bubble-wrap-style.yaml", updates);
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
