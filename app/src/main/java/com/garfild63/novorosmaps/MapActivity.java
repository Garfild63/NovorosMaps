package com.garfild63.novorosmaps;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.widget.Toast;
import com.yandex.mapkit.Animation;
import com.yandex.mapkit.MapKitFactory;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.CameraPosition;
import com.yandex.mapkit.mapview.MapView;

public class MapActivity extends Activity {
    private MapView mapView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        setTitle(getIntent().getStringExtra(MainActivity.ROUTE_NAME));
        mapView = findViewById(R.id.mapView);
        mapView.getMap().move(
                new CameraPosition(new Point(44.67342, 37.77034), 11.0f, 0.0f, 0.0f),
                new Animation(Animation.Type.SMOOTH, 0),
                null);
        Holder.initialize(this, mapView);

        Placemark yourPlacemark = new Placemark(0, 0, R.drawable.geo,
                "Ваша геолокация", "Это ваша геолокация");
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        LocationListener locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                yourPlacemark.setCoordinates(location.getLatitude(), location.getLongitude());
            }
        };
        try {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
        } catch (SecurityException e) {
            Toast.makeText(this, R.string.access_gps, Toast.LENGTH_LONG).show();
        }

        new Polyline(getIntent().getStringExtra(MainActivity.ROUTE_FILE), 0xFF008000);
    }

    @Override
    protected void onStart() {
        super.onStart();
        MapKitFactory.getInstance().onStart();
        mapView.onStart();
    }

    @Override
    protected void onStop() {
        mapView.onStop();
        MapKitFactory.getInstance().onStop();
        super.onStop();
    }
}
