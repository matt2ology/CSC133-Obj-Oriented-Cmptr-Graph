import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

class HeloCabin extends Circle {
    private static final int HELICOPTER_CABIN_SIZE = 18;

    public HeloCabin(Color componentColor) {
        super(HELICOPTER_CABIN_SIZE, componentColor);
    }
}