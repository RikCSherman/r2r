package r2r.converter;

import com.fasterxml.jackson.databind.JsonNode;
import r2r.model.Planet;
import r2r.model.PlanetType;

import java.util.Map.Entry;

public class PlanetMapper {

    public static Planet map(Entry<String, JsonNode> planet) throws Exception {
        Planet body = new Planet();
        body.setName(planet.getKey());
        JsonNode node = planet.getValue();
        body.setBid(node.get("bid").asInt());
        body.setDls(node.get("dls").asInt());
        body.setType(PlanetType.fromId(node.get("tp").asInt()));
        return body;
    }
}
