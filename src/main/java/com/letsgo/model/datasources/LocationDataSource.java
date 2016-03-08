package com.letsgo.model.datasources;

import android.content.Context;
import android.database.Cursor;

import com.letsgo.model.Location;
import com.letsgo.model.daointerfaces.LocationDao;
import com.letsgo.model.utils.Constants;

import java.util.ArrayList;

/**
 * Created by Petkata on 8.3.2016 г..
 */
public class LocationDataSource extends DataSource implements LocationDao {

    public LocationDataSource(Context context) {
        super(context);
    }

    @Override
    public Location selectLocation(String name) {
        Cursor cursor = database.query(Constants.TABLE_LOCATIONS, Constants.LOCATIONS_ALL_COLUMNS, Constants.LOCATIONS_NAME + " = "
                + "'" + name + "'", null, null, null, null);
        cursor.moveToFirst();
        Location location = cursorToLocation(cursor);
        cursor.close();
        return location;
    }

    @Override
    public ArrayList<Location> selectAllLocations() {
        ArrayList<Location> allLocations = new ArrayList<>();
        Cursor cursor = database.query(Constants.TABLE_LOCATIONS,Constants.LOCATIONS_ALL_COLUMNS,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            Location location = cursorToLocation(cursor);
            allLocations.add(location);
            cursor.moveToNext();
        }
        cursor.close();
        return allLocations;
    }

    @Override
    public ArrayList<String> selectAllLocationNames() {
        ArrayList<String> allLocationNames = new ArrayList<>();
        String[] columns = {Constants.LOCATIONS_NAME};
        Cursor cursor = null;
        cursor = database.query(Constants.TABLE_LOCATIONS,columns,null,null,null,null,null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()){
            String locationName = cursor.getString(cursor.getColumnIndex(Constants.LOCATIONS_NAME));
            allLocationNames.add(locationName);
            cursor.moveToNext();
        }
        cursor.close();
        return allLocationNames;
    }

    private Location cursorToLocation(Cursor cursor) {
        Location location = new Location();
        location.setLocationId(cursor.getLong(cursor.getColumnIndex(Constants.AUTOINCREMETN_COLUMN)));
        location.setLocationName(cursor.getString(cursor.getColumnIndex(Constants.LOCATIONS_NAME)));
        location.setLocationAddress(cursor.getString(cursor.getColumnIndex(Constants.LOCATIONS_ADDRESS)));
        location.setLocationContact(cursor.getString(cursor.getColumnIndex(Constants.LOCATIONS_CONTACT)));
        return location;
    }
}
