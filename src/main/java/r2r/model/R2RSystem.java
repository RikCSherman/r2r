package r2r.model;

import java.util.Set;
import java.util.TreeSet;

public class R2RSystem {

    private String name;
    private int sid;
    private double x;
    private double y;
    private double z;
    private Set<Planet> planets = new TreeSet<>();

    public double distance(R2RSystem destination) {
        double xDiff = x - destination.x;
        double ydiff = y - destination.y;
        double zDiff = z - destination.z;
        return  Math.sqrt((xDiff * xDiff) + (ydiff * ydiff) + (zDiff * zDiff) );
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }

    public Set<Planet> getPlanets() {
        return planets;
    }

    public void addPlanet(Planet planet) {
        planets.add(planet);
    }
}
