package r2r.gui;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.apache.commons.lang3.StringUtils;
import r2r.converter.SystemMapper;
import r2r.model.Planet;
import r2r.model.RtoRSystem;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RtoRController {

    private List<RtoRSystem> r2RSystems = new ArrayList<>();
    private Map<String, RtoRSystem> allSystems;
    private List<String> visited = new ArrayList<>();

    @FXML
    private TextField startSystem;
    @FXML
    private TextField next;
    @FXML
    private TextArea planets;
    @FXML
    public IntField distance;

    @FXML
    public void initialize() throws Exception {
        loadAllSystems();
        loadVisited();
        loadR2RSystems();
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        doR2R();
    }
    private void loadAllSystems() throws IOException {
        allSystems = new HashMap<>();
        String fileName = "systems-short.csv";
        if (!new File(fileName).exists()) {
            fileName = "systems.csv";
        }
        BufferedReader in = new BufferedReader(new FileReader(fileName));
        String line;
        in.readLine();
        while ((line = in.readLine()) != null) {
            RtoRSystem system = new RtoRSystem(line);
            allSystems.put(system.getName(), system);
        }
    }

    private void loadR2RSystems() throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        JsonNode node = mapper.readTree(new BufferedReader(new FileReader("expl_1000 (1).json")));
        Iterator<Map.Entry<String, JsonNode>> it = node.fields();
        while (it.hasNext()) {
            RtoRSystem system = SystemMapper.map(it.next());
            r2RSystems.add(system);
        }
    }

    private void loadVisited() {
        try (Stream<String> stream = Files.lines(Paths.get("visited.txt"))) {
            stream.forEach(visited::add);
        } catch (IOException e) {
            // Do nothing
        }
    }
    private void doR2R() {
        RtoRSystem startPoint = allSystems.get(startSystem.getText());
        List<RtoRSystem> localSystems = filterSystems(startPoint, distance.getValue());
        RtoRSystem first;
        if (StringUtils.isEmpty(next.getText())) {
            first = startPoint;
        } else {
            first = allSystems.get(next.getText());
        }
        if (localSystems.size() > 0) {
            RtoRSystem system = findNearest(localSystems, first);
            next.appendText(system.getName() + "\n");
            Collections.sort(system.getPlanets());
            for (Planet planet : system.getPlanets()) {
                planets.appendText(" - " + planet.getName() + "  :  " + planet.getType() + "\n");
            }
        }
    }

    private RtoRSystem findNearest(List<RtoRSystem> localSystems, RtoRSystem startPoint) {
        RtoRSystem nearest = null;
        double shortest = Double.MAX_VALUE;
        for (RtoRSystem system : localSystems) {
            double distance = system.distance(startPoint);
            if (distance < shortest) {
                nearest = system;
                shortest = distance;
            }
        }
        return nearest;
    }

    private List<RtoRSystem> filterSystems(final RtoRSystem startPoint, final int maxDistance) {
        return r2RSystems.stream()
                .filter(a -> startPoint.distance(a) <= maxDistance && !visited.contains(a.getName()))
                .collect(Collectors.toList());
    }
}
