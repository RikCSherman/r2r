package uk.co.rc418.r2r.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EdsmSystem {

    private String name;
    private Coords coords;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coords getCoords() {
        return coords;
    }

    public void setCoords(Coords coords) {
        this.coords = coords;
    }

    public static class Coords {
        private double x;
        private double y;
        private double z;

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
    }

    public RtoRSystem asRtoRSystem() {
        RtoRSystem rtoRSystem = new RtoRSystem();
        rtoRSystem.setName(name);
        rtoRSystem.setX(coords.x);
        rtoRSystem.setY(coords.y);
        rtoRSystem.setZ(coords.z);
        return rtoRSystem;
    }
}
