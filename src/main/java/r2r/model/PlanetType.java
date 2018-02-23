package r2r.model;

import com.fasterxml.jackson.annotation.JsonValue;

public enum PlanetType {

    EARTHLIKE("Earth world", 26),
    HIGH_METAL("High metal content world", 30),
    WATER_WORLD("Water World", 36);

    private final String name;
    private final int id;

    private PlanetType(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return id;
    }

    public static PlanetType fromId(int id) throws Exception {
        switch (id) {
            case 26:
                return EARTHLIKE;
            case 30:
                return HIGH_METAL;
            case 36:
                return WATER_WORLD;
            default:
                throw new Exception("Unkown planet Type id " + id);
        }
    }

    @Override
    @JsonValue
    public String toString() {
        return name;
    }
}
