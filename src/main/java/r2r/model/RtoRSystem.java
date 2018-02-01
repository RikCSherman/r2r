package r2r.model;

import java.util.ArrayList;
import java.util.List;

public class RtoRSystem {

    private String name;
    private double x;
    private double y;
    private double z;
    private List<Planet> planets = new ArrayList<>();

    public RtoRSystem() {
    }

    public RtoRSystem(String line) {
        //1,12695,"1 G. Caeli",80.90625,-83.53125,-30.8125,6544826,1,144,Patronage,2,Empire,80,None,32,Medium,4,Industrial,"Arissa Lavigny-Duval",Exploited,32,0,1517019631,,31816,"1 G. Caeli Empire League",3,Common
        List<String> fields = split(line);
        name = fields.get(2).replaceAll("\"", "");
        x = Double.parseDouble(fields.get(3));
        y = Double.parseDouble(fields.get(4));
        z = Double.parseDouble(fields.get(5));

    }

    public double distance(RtoRSystem destination) {
        double xDiff = x - destination.x;
        double ydiff = y - destination.y;
        double zDiff = z - destination.z;
        return Math.sqrt((xDiff * xDiff) + (ydiff * ydiff) + (zDiff * zDiff));
    }

    private static List<String> split(String s) {
        List<String> words = new ArrayList<>();
        boolean notInsideComma = true;
        int start = 0;
        for (int i = 0; i < s.length() - 1; i++) {
            if (s.charAt(i) == ',' && notInsideComma) {
                words.add(s.substring(start, i));
                start = i + 1;
            } else if (s.charAt(i) == '"')
                notInsideComma = !notInsideComma;
        }
        words.add(s.substring(start));
        return words;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<Planet> getPlanets() {
        return planets;
    }

    public void addPlanet(Planet planet) {
        planets.add(planet);
    }
}
