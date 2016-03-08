package com.letsgo.model.daointerfaces;

import com.letsgo.model.Location;

import java.util.ArrayList;

/**
 * Created by Petkata on 8.3.2016 г..
 */
public interface LocationDao {

    Location selectLocation(String name);

    ArrayList<String> selectAllLocationNames();

    ArrayList<Location> selectAllLocations();

}
