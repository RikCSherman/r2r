package r2r;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import r2r.converter.SystemMapper;
import r2r.model.Planet;
import r2r.model.R2RSystem;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class R2r {

    private static List<R2RSystem> r2RSystems = new ArrayList<>();
    private static Map<String, R2RSystem> allSystems;
    private static List<String> visited = new ArrayList<>();
    private static Config config;

    public static void main(String[] args) throws Exception {
        config = new Config(args);
        loadVisited();
        loadR2RSystems();
        loadAllSystems();
        doR2R();
    }

    private static void loadAllSystems() throws IOException {
        allSystems = new HashMap<>();
        String fileName = "systems-short.csv";
        if (!new File(fileName).exists()) {
            fileName = "systems.csv";
        }
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String line;
        in.readLine();
        while((line = in.readLine()) != null) {
            R2RSystem system = new R2RSystem(line);
            allSystems.put(system.getName(), system);
        }
    }

    private static void loadR2RSystems() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(new BufferedReader(new FileReader("expl_1000 (1).json")));
        Iterator<Entry<String, JsonNode>> it = node.fields();
        while (it.hasNext()) {
            R2RSystem system = SystemMapper.map(it.next());
            r2RSystems.add(system);
        }
    }

    private static void loadVisited() {
        try (Stream<String> stream = Files.lines(Paths.get("visited.txt"))) {
            stream.forEach(visited::add);
        } catch (IOException e) {
            // Do nothing
        }
    }

    private static void doR2R() {
        R2RSystem startPoint = allSystems.get(config.getStartSystem());
        List<R2RSystem> localSystems = filterSystems(startPoint, config.getMaxDistance());
        List<R2RSystem> route = new ArrayList<>();
        R2RSystem first = startPoint;
        for (int i = 0; i < config.getNoSystems() && localSystems.size() > 0; i++) {
            first = findNearest(localSystems, first);
            route.add(first);
            localSystems.remove(first);
        }
        for(R2RSystem system : route) {
            System.out.println(system.getName());
            Collections.sort(system.getPlanets());
            for (Planet planet : system.getPlanets()) {
                System.out.println(" - " + planet.getName() + "  :  " + planet.getType());
            }
        }
    }

    private static R2RSystem findNearest(List<R2RSystem> localSystems, R2RSystem startPoint) {
        R2RSystem nearest = null;
        double shortest = Double.MAX_VALUE;
        for (R2RSystem system : localSystems) {
            double distance = system.distance(startPoint);
            if (distance < shortest) {
                nearest = system;
                shortest = distance;
            }
        }
        return nearest;
    }

    private static List<R2RSystem> filterSystems(final R2RSystem startPoint, final int maxDistance) {
        return r2RSystems.stream()
                .filter(a -> startPoint.distance(a) <= maxDistance && !visited.contains(a.getName()))
                .collect(Collectors.toList());
    }
}
