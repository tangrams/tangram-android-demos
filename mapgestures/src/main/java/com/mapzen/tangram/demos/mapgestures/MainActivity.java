package com.mapzen.tangram.demos.mapgestures;

import android.graphics.PointF;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import com.mapzen.tangram.CameraUpdateFactory;
import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;
import com.mapzen.tangram.SceneUpdate;
import com.mapzen.tangram.TouchInput;

import java.util.ArrayList;
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

    @Override
    public void onMapReady(MapController mapController) {
        map = mapController;

        // Set our API key as a scene update.
        List<SceneUpdate> updates = new ArrayList<>();
        updates.add(new SceneUpdate("global.sdk_api_key", BuildConfig.NEXTZEN_API_KEY));

        map.loadSceneFileAsync("bubble-wrap/bubble-wrap-style.yaml", updates);

        TouchInput touchInput = map.getTouchInput();

        touchInput.setTapResponder(new TouchInput.TapResponder() {
            @Override
            public boolean onSingleTapUp(float x, float y) {
                LngLat point = map.screenPositionToLngLat(new PointF(x, y));
                map.updateCameraPosition(CameraUpdateFactory.setPosition(point), 1000);
                return false;
            }

            @Override
            public boolean onSingleTapConfirmed(float x, float y) {
                return false;
            }
        });

        touchInput.setPanResponder(new TouchInput.PanResponder() {
            @Override
            public boolean onPanBegin() {
                return false;
            }

            @Override
            public boolean onPan(float startX, float startY, float endX, float endY) {
                float rotate = (startX - endX) / 400;
                float tilt = (startY - endY) / 400;
                map.updateCameraPosition(CameraUpdateFactory.rotateBy(rotate));
                map.updateCameraPosition(CameraUpdateFactory.tiltBy(tilt));

                // Returning 'true' means that this gesture event is 'consumed' and won't be passed
                // on to any other handlers. We return 'true' here so that the map doesn't do the
                // built-in 'panning' behavior.
                return true;
            }

            @Override
            public boolean onPanEnd() {
                return false;
            }

            @Override
            public boolean onFling(float posX, float posY, float velocityX, float velocityY) {
                // We return 'true' here as well, otherwise a flinging gesture will make the map pan
                return true;
            }

            @Override
            public boolean onCancelFling() {
                return false;
            }
        });

        // Disable built-int "shove" gesture to tilt.
        touchInput.setGestureDisabled(TouchInput.Gestures.SHOVE);

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
