package com.mapzen.tangram.demos.mapgestures;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;
import com.mapzen.tangram.TouchInput;

public class MainActivity extends AppCompatActivity implements MapView.OnMapReadyCallback {

    MapController map;
    MapView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (MapView)findViewById(R.id.map);
        view.onCreate(savedInstanceState);
        view.getMapAsync(this, "bubble-wrap/bubble-wrap.yaml");

    }

    @Override
    public void onMapReady(MapController mapController) {
        map = mapController;

        map.setTapResponder(new TouchInput.TapResponder() {
            @Override
            public boolean onSingleTapUp(float x, float y) {
                LngLat point = map.screenPositionToLngLat(new PointF(x, y));
                map.setPositionEased(point, 1000);
                return false;
            }

            @Override
            public boolean onSingleTapConfirmed(float x, float y) {
                return false;
            }
        });

        map.setPanResponder(new TouchInput.PanResponder() {
            @Override
            public boolean onPan(float startX, float startY, float endX, float endY) {
                float rotate = (startX - endX) / 400;
                float tilt = (startY - endY) / 400;
                map.setRotation(map.getRotation() + rotate);
                map.setTilt(map.getTilt() + tilt);

                // Returning 'true' means that this gesture event is 'consumed' and won't be passed
                // on to any other handlers. We return 'true' here so that the map doesn't do the
                // built-in 'panning' behavior.
                return true;
            }

            @Override
            public boolean onFling(float posX, float posY, float velocityX, float velocityY) {
                // We return 'true' here as well, otherwise a flinging gesture will make the map pan
                return true;
            }
        });

        map.setShoveResponder(new TouchInput.ShoveResponder() {
            @Override
            public boolean onShove(float distance) {
                // We return 'true' here so that the built-in tilting behavior doesn't occur.
                return true;
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
