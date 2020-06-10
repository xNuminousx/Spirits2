package me.numin.spirits2.utils;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public class LocationUtils {

    private Location destination;
    private Location location;
    private Vector vector;

    public LocationUtils(Location location) {
        this.location = location.clone();
    }

    public LocationUtils(Location location, Location destination) {
        this.destination = destination.clone();
        this.location = location.clone();
        this.vector = new Vector(0, 0, 0);
    }

    public LocationUtils(Location location, Vector direction) {
        this.location = location.clone();
        this.vector = direction.clone();
    }

    public Location advanceToDirection(double speed) {
        if (location == null || vector == null)
            return null;

        return location.add(vector.multiply(speed).normalize());
    }

    public Location advanceToPoint(double speed) {
        if (destination == null || location == null)
            return null;

        vector.add(destination.toVector().subtract(location.toVector()).multiply(speed).normalize());
        return location.clone().add(vector.clone().multiply(speed));
    }
}
