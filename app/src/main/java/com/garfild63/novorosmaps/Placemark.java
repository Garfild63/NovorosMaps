package com.garfild63.novorosmaps;

import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.PlacemarkMapObject;
import com.yandex.mapkit.map.UserData;
import com.yandex.runtime.image.ImageProvider;
import java.util.HashMap;
import java.util.Map;

public class Placemark {
    public static final String TITLE = "title";
    public static final String DESCRIPTION = "description";
    private final PlacemarkMapObject placemark;

    public Placemark(double lat, double lng, int icon, String title, String description) {
        placemark = Holder.mapView.getMap().getMapObjects().addPlacemark(new Point(lat, lng));
        placemark.setIcon(ImageProvider.fromResource(Holder.context, icon));
        Map<String, String> userData = new HashMap<>();
        userData.put(TITLE, title);
        userData.put(DESCRIPTION, description);
        placemark.setUserData(new UserData(userData));
        placemark.addTapListener(Holder.listener);
    }

    public void setCoordinates(double lat, double lng) {
        placemark.setGeometry(new Point(lat, lng));
    }
}
