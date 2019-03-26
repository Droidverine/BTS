package com.droidverine.bts.bts;

import android.location.Location;

public class ModelLocation {
    public ModelLocation() {
    }

    public ModelLocation(Location location) {
        this.location = location;
    }

    Location location;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }
}
