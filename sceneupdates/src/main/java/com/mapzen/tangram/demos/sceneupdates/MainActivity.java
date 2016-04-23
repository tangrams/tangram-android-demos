package com.mapzen.tangram.demos.sceneupdates;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

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

    public void onCheckBoxClicked(View view) {
        CheckBox checkbox = (CheckBox)view;
        String visible = checkbox.isChecked() ? "true" : "false";
        String name = (String)checkbox.getText();

        map.queueSceneUpdate("layers." + name + ".visible", visible);
        map.applySceneUpdates();
        map.requestRender();
    }
}
