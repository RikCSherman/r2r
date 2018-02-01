package r2r.gui;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class RtoRController {

    @FXML
    TextArea route;

    @FXML
    private void handleButtonAction(ActionEvent event) {
        // Button was clicked, do something...
        route.appendText("Hello Steve\n");
    }
}
