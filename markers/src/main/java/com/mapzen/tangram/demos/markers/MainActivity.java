package com.mapzen.tangram.demos.markers;

import android.graphics.PointF;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapData;
import com.mapzen.tangram.MapView;
import com.mapzen.tangram.TouchInput;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MapView.OnMapReadyCallback {

    MapController map;
    MapView view;

    MapData points;
    MapData lines;
    MapData polygons;

    MapData current;

    Button clearButton;

    ArrayList<LngLat> taps = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (MapView)findViewById(R.id.map);
        view.onCreate(savedInstanceState);
        view.getMapAsync(this, "bubble-wrap/bubble-wrap.yaml");

        clearButton = (Button)findViewById(R.id.clear_button);
    }

    @Override
    public void onMapReady(MapController mapController) {
        map = mapController;

        // These calls create new data sources in the scene with the names given.
        // The scene already has layers defined to provide styling for features from these sources.
        points = map.addDataLayer("mz_default_point");
        lines = map.addDataLayer("mz_default_line");
        polygons = map.addDataLayer("mz_default_polygon");

        clearButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (current != null) {
                    current.clear();
                }
            }
        });

        map.setTapResponder(new TouchInput.TapResponder() {
            @Override
            public boolean onSingleTapUp(float x, float y) {
                LngLat tap = map.screenPositionToLngLat(new PointF(x, y));
                taps.add(tap);
                if (current == points) {
                    points.addPoint(tap, null);
                    taps.clear();
                } else if (current == lines && taps.size() >= 2) {
                    lines.addPolyline(taps, null);
                    taps.remove(0);
                } else if (current == polygons && taps.size() >= 3) {
                    ArrayList<List<LngLat>> polygon = new ArrayList<>();
                    polygon.add(taps);
                    polygons.addPolygon(polygon, null);
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

    public void onRadioButtonClicked(View view) {

        boolean checked = ((RadioButton) view).isChecked();

        if (!checked) {
            return;
        }

        taps.clear();

        switch(view.getId()) {
            case R.id.radio_points:
                current = points;
                break;
            case R.id.radio_lines:
                current = lines;
                break;
            case R.id.radio_polygons:
                current = polygons;
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
