package com.mapzen.tangram.demos.featurepicking;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.TextView;

import com.mapzen.tangram.FeaturePickListener;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;
import com.mapzen.tangram.SceneUpdate;
import com.mapzen.tangram.TouchInput;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements MapView.MapReadyCallback {

    MapController map;
    MapView view;
    TextView textWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (MapView)findViewById(R.id.map);
        view.onCreate(savedInstanceState);
        view.getMapAsync(this);

        textWindow = (TextView)findViewById(R.id.textWindow);
        textWindow.setText("Tap an icon on the map.");
    }

    @Override
    public void onMapReady(MapController mapController) {
        map = mapController;

        // Set our API key as a scene update.
        List<SceneUpdate> updates = new ArrayList<>();
        updates.add(new SceneUpdate("global.sdk_api_key", BuildConfig.NEXTZEN_API_KEY));

        map.loadSceneFileAsync("bubble-wrap/bubble-wrap-style.yaml", updates);

        map.setFeaturePickListener(new FeaturePickListener() {
            // A scene file can declare certain groups of features to be 'interactive', meaning that
            // they can be selected in a call to pickFeature(). If an 'interactive' feature is found
            // at the given position, its information is returned in onFeaturePick. If no
            // 'interactive' feature is found, onFeaturePick won't be called.
            @Override
            public void onFeaturePick(Map<String, String> properties, float x, float y) {
                // After a feature is picked from the map, we receive a map of 'properties' as
                // string keys and values, as well as the screen position of the feature's center.
                textWindow.setText("Selected feature properties:");
                for (Map.Entry entry : properties.entrySet()) {
                    textWindow.append("\n" + entry.getKey() + " : " + entry.getValue());
                }
            }
        });

        map.getTouchInput().setTapResponder(new TouchInput.TapResponder() {
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
