package com.mapzen.dynamicmarkers;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.mapzen.tangram.LngLat;
import com.mapzen.tangram.MapController;
import com.mapzen.tangram.MapView;
import com.mapzen.tangram.Marker;
import com.mapzen.tangram.geometry.Polygon;
import com.mapzen.tangram.geometry.Polyline;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements MapView.OnMapReadyCallback {

    MapController map;
    MapView view;
    Marker pointMarker;
    Marker lineMarker;
    Marker polygonMarker;
    LngLat point = new LngLat(-73.9918, 40.73633);

    String pointStyle = "{ style: 'points', color: 'white', size: [50px, 50px], order: 2000, collide: false }";
    String lineStyle = "{ style: 'lines', color: '#06a6d4', width: 5px, order: 2000 }";
    String polygonStyle = "{ style: 'polygons', color: '#06a6d4', width: 5px, order: 2000 }";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        view = (MapView)findViewById(R.id.map);
        view.onCreate(savedInstanceState);
        view.getMapAsync(this, "bubble-wrap/bubble-wrap.yaml");

        Button addMarkerBtn = (Button) findViewById(R.id.add_marker_btn);
        addMarkerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!mapReadyAndNoMarker()) {
                    return;
                }
                pointMarker = map.addMarker();
                lineMarker = map.addMarker();
                polygonMarker = map.addMarker();
            }
        });

        Button rmMarkerBtn = (Button) findViewById(R.id.rm_markers_btn);
        rmMarkerBtn.setOnClickListener(new MapMarkerReadyClickListener() {
            @Override
            void onReadyClick(View view) {
                map.removeAllMarkers();
                pointMarker = null;
                lineMarker = null;
                polygonMarker = null;
            }
        });

        Button setBitmapBtn = (Button) findViewById(R.id.set_bitmap_btn);
        setBitmapBtn.setOnClickListener(new MapMarkerReadyClickListener() {
            @Override
            void onReadyClick(View view) {
                pointMarker.setDrawable(R.drawable.mapzen_logo);
//                Drawable drawable = ContextCompat.getDrawable(MainActivity.this, R.drawable.mapzen_logo);
//                pointMarker.setDrawable(drawable);
            }
        });

        Button setMarkerPtBtn = (Button) findViewById(R.id.set_marker_pt_btn);
        setMarkerPtBtn.setOnClickListener(new MapMarkerReadyClickListener() {
            @Override
            void onReadyClick(View view) {
                pointMarker.setPoint(point);
            }
        });

        Button setStylingBtn = (Button) findViewById(R.id.set_styling_btn);
        setStylingBtn.setOnClickListener(new MapMarkerReadyClickListener() {
            @Override
            void onReadyClick(View view) {
                pointMarker.setStyling(pointStyle);
                lineMarker.setStyling(lineStyle);
                polygonMarker.setStyling(polygonStyle);
            }
        });

        Button setPolylineBtn = (Button) findViewById(R.id.set_polyline_btn);
        setPolylineBtn.setOnClickListener(new MapMarkerReadyClickListener() {
            @Override
            void onReadyClick(View view) {
                ArrayList<LngLat> points = new ArrayList<>();
                points.add(new LngLat(-73.9903, 40.74433));
                points.add(new LngLat(-73.984770, 40.734807));
                points.add(new LngLat(-73.998674, 40.732172));
                points.add(new LngLat(-73.996142, 40.741050));
                points.add(new LngLat(-73.9903, 40.74433));
                // dont pass style here, its not read
                Polyline polyline = new Polyline(points, null);
                lineMarker.setPolyline(polyline);
            }
        });

        Button setPolygonBtn = (Button) findViewById(R.id.set_polygon_btn);
        setPolygonBtn.setOnClickListener(new MapMarkerReadyClickListener() {
            @Override
            void onReadyClick(View view) {
                List<List<LngLat>> outer = new ArrayList<>();
                ArrayList<LngLat> points = new ArrayList<>();
                points.add(new LngLat(-73.9903, 40.74433));
                points.add(new LngLat(-73.984770, 40.734807));
                points.add(new LngLat(-73.998674, 40.732172));
                points.add(new LngLat(-73.996142, 40.741050));
                points.add(new LngLat(-73.9903, 40.74433));
                outer.add(points);
                // dont pass style here, its not read
                Polygon polygon = new Polygon(outer, null);
                polygonMarker.setPolygon(polygon);
            }
        });

        Button setVisibleBtn = (Button) findViewById(R.id.set_visible_btn);
        setVisibleBtn.setOnClickListener(new MapMarkerReadyClickListener() {
            @Override
            void onReadyClick(View view) {
                pointMarker.setVisible(!pointMarker.isVisible());
                lineMarker.setVisible(!lineMarker.isVisible());
                polygonMarker.setVisible(!polygonMarker.isVisible());
            }
        });
    }

    @Override
    public void onMapReady(MapController mapController) {
        map = mapController;
        map.setPosition(point);
        map.setZoom(15);
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

    private boolean mapReadyAndNoMarker() {
        return map != null && pointMarker == null;
    }

    private boolean mapAndMarkerReady() {
        return map != null && pointMarker != null;
    }

    abstract class MapMarkerReadyClickListener implements View.OnClickListener {

        abstract void onReadyClick(View view);

        @Override
        public void onClick(View view) {
            if (!mapAndMarkerReady()) {
                return;
            }
            onReadyClick(view);
        }
    }
}
