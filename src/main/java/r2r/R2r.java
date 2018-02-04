package r2r;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.*;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import r2r.converter.SystemMapper;
import r2r.gui.RtoRController;
import r2r.model.RtoRSystem;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;


public class R2r extends Application {

    private final Map<String, RtoRSystem> r2RSystems = new HashMap<>();
    private List<String> visited = new ArrayList<>();

    @Override
    public void init() throws Exception {
        loadVisited();
        loadR2RSystems();
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/r2rgui.fxml"));
        Parent root = fxmlLoader.load();
        RtoRController controller = fxmlLoader.getController();
        controller.setR2RSystems(r2RSystems);
        controller.setVisited(visited);
        Scene scene = new Scene(root, 300, 275);
        setUpAccelerators(scene);
        stage.setTitle("R2R Helper");
        stage.setScene(scene);
        stage.show();
    }

    private void setUpAccelerators(Scene scene) {
        ObservableList<Node> children = scene.getRoot().getChildrenUnmodifiable();
        Button nextSystem = null;
        TextArea planets = null;
        for (Node child : children) {
            if (child instanceof Button) {
                nextSystem = (Button) child;
            } else if (child instanceof TextArea) {
                planets = (TextArea) child;
            }
        }
        TextArea finalPlanets = planets;
        assert planets != null;
        planets.setOnMouseClicked(event -> RtoRController.copyToClipboard(finalPlanets.getText()));
        scene.getAccelerators().put(new KeyCodeCombination(KeyCode.P, KeyCombination.SHORTCUT_DOWN),
                () -> finalPlanets.fireEvent(new MouseEvent(MouseEvent.MOUSE_CLICKED,
                        finalPlanets.getLayoutX(), finalPlanets.getLayoutY(), finalPlanets.getLayoutX(), finalPlanets.getLayoutY(), MouseButton.PRIMARY, 1,
                        true, true, true, true, true, true, true, true, true, true, null)));
        final Button finalNextSystem = nextSystem;
        assert finalNextSystem != null;
        scene.getAccelerators().put(
                new KeyCodeCombination(KeyCode.N, KeyCombination.SHORTCUT_DOWN),
                finalNextSystem::fire);
    }

    private void loadR2RSystems() throws Exception {
        final ObjectMapper mapper = new ObjectMapper();
        loadFile(mapper, "/data/expl_1000 (1).json");
        loadFile(mapper, "/data/expl_pop.json");
    }

    private void loadFile(ObjectMapper mapper, String fileName) throws Exception {
        JsonNode node;
        Iterator<Map.Entry<String, JsonNode>> it;
        node = mapper.readTree(this.getClass().getResourceAsStream(fileName));
        it = node.fields();
        while (it.hasNext()) {
            RtoRSystem system = SystemMapper.map(it.next());
            r2RSystems.put(system.getName(), system);
        }
    }

    private void loadVisited() {
        try  {
            final File file = new File(RtoRController.VISITED_TXT);
            visited = FileUtils.readLines(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            // Do nothing
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
