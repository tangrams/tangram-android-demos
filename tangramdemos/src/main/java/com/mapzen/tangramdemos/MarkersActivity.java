package com.mapzen.tangramdemos;

import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;
import com.mapzen.tangram.Marker;
import com.mapzen.tangram.SceneUpdate;
import com.mapzen.tangram.TouchInput;
import com.mapzen.tangram.geometry.Polygon;
import com.mapzen.tangram.geometry.Polyline;

import java.util.ArrayList;
import java.util.List;

public class MarkersActivity extends AppCompatActivity implements MapView.MapReadyCallback {

    MapController map;
    MapView view;

    Marker pointMarker;
    Marker lineMarker;
    Marker polygonMarker;

    String pointStyle = "{ style: 'points', color: 'white', size: [50px, 50px], order: 2000, collide: false }";
    String lineStyle = "{ style: 'lines', color: '#06a6d4', width: 5px, order: 2000 }";
    String polygonStyle = "{ style: 'polygons', color: '#06a6d4', width: 5px, order: 2000 }";

    Marker current;

    Button clearButton;

    ArrayList<LngLat> taps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_markers);

        // Set up back button to return to the demo list.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        view = (MapView)findViewById(R.id.map);
        view.onCreate(savedInstanceState);
        view.getMapAsync(this);

        clearButton = (Button)findViewById(R.id.clear_button);
    }

    @Override
    public void onMapReady(MapController mapController) {
        map = mapController;

        // Set our API key as a scene update.
        List<SceneUpdate> updates = new ArrayList<>();
        updates.add(new SceneUpdate("global.sdk_api_key", BuildConfig.NEXTZEN_API_KEY));

        map.loadSceneFileAsync("bubble-wrap/bubble-wrap-style.yaml", updates);

        resetMarkers();

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            resetMarkers();
            }
        });

        map.getTouchInput().setTapResponder(new TouchInput.TapResponder() {
            @Override
            public boolean onSingleTapUp(float x, float y) {
                LngLat tap = map.screenPositionToLngLat(new PointF(x, y));
                taps.add(tap);
                if (current == pointMarker) {
                    pointMarker.setPoint(tap);
                    taps.clear();
                } else if (current == lineMarker && taps.size() >= 2) {
                    lineMarker.setPolyline(new Polyline(taps, null));
                    taps.remove(0);
                } else if (current == polygonMarker && taps.size() >= 3) {
                    ArrayList<List<LngLat>> polygon = new ArrayList<>();
                    polygon.add(taps);
                    polygonMarker.setPolygon(new Polygon(polygon, null));
                    taps.remove(0);
                }
                map.requestRender();
                return false;
            }

            @Override
            public boolean onSingleTapConfirmed(float x, float y) {
                return false;
            }
        });
    }

    void resetMarkers() {
        map.removeAllMarkers();
        pointMarker = null;
        lineMarker = null;
        polygonMarker = null;

        pointMarker = map.addMarker();
        pointMarker.setStylingFromString(pointStyle);
        pointMarker.setDrawable(R.drawable.mapzen_logo);

        lineMarker = map.addMarker();
        lineMarker.setStylingFromString(lineStyle);

        polygonMarker = map.addMarker();
        polygonMarker.setStylingFromString(polygonStyle);
    }

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        if (!checked) {
            return;
        }

        taps.clear();

        switch(view.getId()) {
            case R.id.radio_points:
                current = pointMarker;
                break;
            case R.id.radio_lines:
                current = lineMarker;
                break;
            case R.id.radio_polygons:
                current = polygonMarker;
                break;
        }
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
