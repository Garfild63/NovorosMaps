package com.garfild63.novorosmaps;

import android.widget.Toast;
import com.yandex.mapkit.geometry.Point;
import com.yandex.mapkit.map.PolylineMapObject;
import java.util.ArrayList;
import java.util.List;

public class Polyline {
    private final PolylineMapObject polyline;

    public Polyline(String filename, int color) {
        List<Point> points = new ArrayList<>();
        try {
            CSVParser.parse(filename, new CSVParser.Preparator() {
                public void endLine(String[] args, int x) {
                    switch (x) {
                        case 2:
                            points.add(new Point(Double.parseDouble(args[0]), Double.parseDouble(args[1])));
                            break;
                        case 4:
                            new Placemark(Double.parseDouble(args[0]), Double.parseDouble(args[1]),
                                    R.drawable.marker, args[2], args[3]);
                            break;
                    }
                }

                public char prepareChar(char ch, int x) {
                    return (ch == ',' && x < 2) ? '.' : ch;
                }
            });
        } catch (Exception e) {
            Toast.makeText(Holder.context, e.toString(), Toast.LENGTH_LONG).show();
        }
        PolylineMapObject polyline = Holder.mapView.getMap()
                .getMapObjects().addPolyline(new com.yandex.mapkit.geometry.Polyline(points));
        polyline.setStrokeColor(color);
        polyline.setStrokeWidth(5.0f);
        this.polyline = polyline;
    }
}
