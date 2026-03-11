package com.garfild63.novorosmaps;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import androidx.annotation.NonNull;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.MapObject;
import com.yandex.mapkit.map.MapObjectTapListener;
import com.yandex.mapkit.map.UserData;
import com.yandex.mapkit.mapview.MapView;
import java.util.Map;

public class Holder {
    public static Context context;
    public static MapView mapView;
    public static MapObjectTapListener listener;

    public static void initialize(Context ctx, MapView mv) {
        context = ctx;
        mapView = mv;
        listener = new MapObjectTapListener() {
            @Override
            public boolean onMapObjectTap(@NonNull MapObject mapObject, @NonNull Point point) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                Map<String, String> userData = ((UserData) mapObject.getUserData()).getData();
                builder.setTitle(userData.get(Placemark.TITLE));
                builder.setMessage(userData.get(Placemark.DESCRIPTION));
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.show();
                return true;
            }
        };
    }
}
