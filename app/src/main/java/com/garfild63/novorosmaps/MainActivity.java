package com.garfild63.novorosmaps;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.yandex.mapkit.MapKitFactory;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends ListActivity {
    public static final String ROUTE_NAME = "routename";
    public static final String ROUTE_FILE = "routefile";
    public static final String API_KEY = "9515cb2b-354e-41b1-8264-656fcd6adf9f";
    private List<String> routeNames, routeFiles;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MapKitFactory.setApiKey(API_KEY);
        MapKitFactory.initialize(this);
        routeNames = new ArrayList<>();
        routeFiles = new ArrayList<>();
        try {
            CSVParser.parse("routes.csv", new CSVParser.Preparator() {
                @Override
                public void endLine(String[] args, int x) {
                    if (x == 2) {
                        routeNames.add(args[0]);
                        routeFiles.add(args[1]);
                    }
                }

                @Override
                public char prepareChar(char ch, int x) {
                    return ch;
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
        }
        ArrayAdapter<String> mAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, routeNames);
        setListAdapter(mAdapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent intent = new Intent(MainActivity.this, MapActivity.class);
        intent.putExtra(ROUTE_NAME, routeNames.get(position));
        intent.putExtra(ROUTE_FILE, routeFiles.get(position));
        startActivity(intent);
    }
}
