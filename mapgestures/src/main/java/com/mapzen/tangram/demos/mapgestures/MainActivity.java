package com.mapzen.tangram.demos.mapgestures;

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
        view.getMapAsync(this, "scene.yaml");

    }

    @Override
    public void onMapReady(MapController mapController) {
        map = mapController;

        map.setTapResponder(new TouchInput.TapResponder() {
            @Override
            public boolean onSingleTapUp(float x, float y) {
                LngLat point = map.coordinatesAtScreenPosition(x, y);
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
                return true;
            }

            @Override
            public boolean onFling(float posX, float posY, float velocityX, float velocityY) {
                return true;
            }
        });

        map.setShoveResponder(new TouchInput.ShoveResponder() {
            @Override
            public boolean onShove(float distance) {
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
