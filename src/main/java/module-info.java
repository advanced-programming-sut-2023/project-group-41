module stronghold {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    exports stronghold.view to javafx.graphics;
    exports stronghold to javafx.graphics;
    exports stronghold.view.graphics to javafx.graphics;
    exports stronghold.model.components.game.graphical to javafx.graphics;
    exports stronghold.controller to javafx.fxml;
    exports stronghold.controller.graphical to javafx.fxml;
}
