package r2r.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseEvent;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import r2r.model.Planet;
import r2r.model.RtoRSystem;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RtoRController {

    public static final String VISITED_TXT = "visited.txt";
    private List<RtoRSystem> r2RSystems;
    private Map<String, RtoRSystem> allSystems;
    private List<String> visited;

    @FXML
    private TextField startSystem;
    @FXML
    private TextField next;
    @FXML
    private TextArea planets;
    @FXML
    public Spinner<Integer> distance;
    @FXML
    public Button nextSystem;

    @FXML
    public void initialize() throws Exception {
    }

    @FXML
    private void handleNextSystem(final ActionEvent event) {
        doR2R();
    }

    private void writeVisited() {
        try {
            final File file = new File(VISITED_TXT);
            FileUtils.writeLines(file, StandardCharsets.UTF_8.toString(), visited);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void doR2R() {
        final RtoRSystem startPoint = allSystems.get(startSystem.getText());
        if (startPoint == null) {
            return;
        }
        RtoRSystem current;
        final String lastVisited = next.getText();
        if (StringUtils.isEmpty(lastVisited)) {
            current = startPoint;
        } else {
            current = allSystems.get(lastVisited);
            visited.add(lastVisited);
            writeVisited();
        }
        List<RtoRSystem> localSystems = filterSystems(current, distance.getValue());
        if (localSystems.size() > 0) {
            next.clear();
            planets.clear();
            RtoRSystem nearest = findNearest(localSystems, current);
            next.appendText(nearest.getName());
            copyToClipboard(nearest.getName());
            Collections.sort(nearest.getPlanets());
            for (Planet planet : nearest.getPlanets()) {
                planets.appendText(planet.getName() + "  :  (" + planet.getType() + ")\n");
            }
        }
    }

    private RtoRSystem findNearest(final List<RtoRSystem> localSystems, final RtoRSystem startPoint) {
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

    public void copyPlanets(final MouseEvent mouseEvent) {
        copyToClipboard(planets.getText());
    }

    public static void copyToClipboard(final String text) {
        final Clipboard clipboard = Clipboard.getSystemClipboard();
        final ClipboardContent content = new ClipboardContent();
        content.putString(text);
        clipboard.setContent(content);
    }

    public void setR2RSystems(List<RtoRSystem> r2RSystems) {
        this.r2RSystems = r2RSystems;
    }

    public void setAllSystems(Map<String, RtoRSystem> allSystems) {
        this.allSystems = allSystems;
    }

    public void setVisited(List<String> visited) {
        this.visited = visited;
    }
}
