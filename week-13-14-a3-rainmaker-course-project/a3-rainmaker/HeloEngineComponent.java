import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class HeloEngineComponent extends Rectangle {
    public HeloEngineComponent(Color componentColor) {
        super(36, 12, componentColor);
        // set the origin to the center of the rectangle for rotation
        this.setX(-this.getWidth() / 2);
        this.setY(-this.getHeight() / 2);
    }
}