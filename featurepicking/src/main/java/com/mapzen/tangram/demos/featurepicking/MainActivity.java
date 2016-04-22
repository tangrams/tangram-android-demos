package com.mapzen.tangram.demos.featurepicking;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;
import com.mapzen.tangram.TouchInput;

import java.util.ArrayList;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MapView.OnMapReadyCallback {

    MapController map;
    MapView view;
    TextView textWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (MapView)findViewById(R.id.map);
        view.onCreate(savedInstanceState);
        view.getMapAsync(this, "scene.yaml");

        textWindow = (TextView)findViewById(R.id.textWindow);
        textWindow.setText("Tap an icon on the map.");
    }

    @Override
    public void onMapReady(MapController mapController) {
        map = mapController;

        map.setFeaturePickListener(new MapController.FeaturePickListener() {
            @Override
            public void onFeaturePick(Map<String, String> properties, float x, float y) {
                textWindow.setText("Selected feature properties:");
                for (Map.Entry entry : properties.entrySet()) {
                    textWindow.append("\n" + entry.getKey() + " : " + entry.getValue());
                }
            }
        });

        map.setTapResponder(new TouchInput.TapResponder() {
            @Override
            public boolean onSingleTapUp(float x, float y) {
                return false;
            }

            @Override
            public boolean onSingleTapConfirmed(float x, float y) {
                map.pickFeature(x, y);
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
