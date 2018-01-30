package r2r.converter;

import com.fasterxml.jackson.databind.JsonNode;
import r2r.model.R2RSystem;

import java.util.Iterator;
import java.util.Map.Entry;

public class SystemMapper {

    public static R2RSystem map(Entry<String, JsonNode> entry) throws Exception {
        R2RSystem system = new R2RSystem();
        system.setName(entry.getKey());
        JsonNode node = entry.getValue();
        system.setSid(node.get("sid").asInt());
        system.setX(node.get("x").asDouble());
        system.setY(node.get("y").asDouble());
        system.setZ(node.get("z").asDouble());
        Iterator<Entry<String, JsonNode>> it = node.get("planets").fields();
        while(it.hasNext()) {
            Entry<String, JsonNode> planet = it.next();
            system.addPlanet(PlanetMapper.map(planet));
        }
        return system;
    }
}
