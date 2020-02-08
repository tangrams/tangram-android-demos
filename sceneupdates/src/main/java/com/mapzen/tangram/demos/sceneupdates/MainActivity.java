package com.mapzen.tangram.demos.sceneupdates;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;

import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;
import com.mapzen.tangram.MapView;
import com.mapzen.tangram.SceneUpdate;
import com.mapzen.tangram.TouchInput;
import com.mapzen.tangram.geometry.Geometry;
import com.mapzen.tangram.geometry.Point;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MapView.MapReadyCallback {

    MapController map;
    MapView view;

    SceneUpdate apiKeySceneUpdate = new SceneUpdate("global.sdk_api_key", BuildConfig.NEXTZEN_API_KEY);
    String sceneFilePath = "bubble-wrap/bubble-wrap-style.yaml";

    MapData tappedLocationData;
    List<Geometry> tappedLocations = new ArrayList<>();

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

        tappedLocationData = map.addDataLayer("mz_default_point");

        map.getTouchInput().setTapResponder(new TouchInput.TapResponder() {
            @Override
            public boolean onSingleTapUp(float x, float y) {
                return false;
            }

            @Override
            public boolean onSingleTapConfirmed(float x, float y) {
                LngLat location = map.screenPositionToLngLat(new PointF(x, y));

                tappedLocations.add(new Point(location, null));

                tappedLocationData.setFeatures(tappedLocations);

                return false;
            }
        });
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
