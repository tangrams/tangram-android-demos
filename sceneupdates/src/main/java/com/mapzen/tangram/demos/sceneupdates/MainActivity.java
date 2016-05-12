package com.mapzen.tangram.demos.sceneupdates;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
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
        map.queueSceneUpdate("layers." + name + ".visible", visible);
        map.applySceneUpdates();
        map.requestRender();
    }

    public void onRadioClicked(View view) {
        RadioButton checkbox = (RadioButton)view;
        String name = (String)checkbox.getText();

        if (map == null) {
            return;
        }

        if ("isometric".equals(name)) {
            map.queueSceneUpdate("cameras", "{isometric: {type: isometric}}");
            map.applySceneUpdates();

            map.setZoomEased(15, 1000, MapController.EaseType.LINEAR);
            map.setTiltEased((float)Math.toRadians(0), 1000, MapController.EaseType.CUBIC);

        } else if ("perspective".equals(name)) {
            map.setZoomEased(17, 1000, MapController.EaseType.LINEAR);
            map.setTiltEased((float)Math.toRadians(60), 1000, MapController.EaseType.CUBIC);

            map.queueSceneUpdate("cameras", "{isometric: {type: perspective}}");
            map.applySceneUpdates();
        }

        map.requestRender();
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
