import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class HeloTailBoom extends Rectangle {
    public HeloTailBoom(Color componentColor) {
        super(5, 40, componentColor);
        this.setX(-this.getWidth() / 2);
    }
}