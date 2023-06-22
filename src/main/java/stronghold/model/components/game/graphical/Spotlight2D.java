package stronghold.model.components.game.graphical;

import javafx.scene.Group;
import javafx.scene.paint.RadialGradient;
import javafx.scene.shape.Circle;

public class Spotlight2D {
    public Group shape;
    public Spotlight2D(double x, double y, double radius){
        Circle baseMesh = new Circle(x, y, radius);
        shape.getChildren().add(baseMesh);
    }
}
