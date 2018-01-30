package r2r.model;

public class Planet implements Comparable<Planet> {

    private String name;
    private int dls;
    private PlanetType type;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getDls() {
        return dls;
    }

    public void setDls(int dls) {
        this.dls = dls;
    }

    public PlanetType getType() {
        return type;
    }

    public void setType(PlanetType type) {
        this.type = type;
    }

    @Override
    public int compareTo(Planet o) {
        return name.compareTo(o.name);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Planet
                && compareTo((Planet) obj) == 0;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }
}
