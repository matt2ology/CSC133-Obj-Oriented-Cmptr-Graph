import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class HeloLandingSkid extends Rectangle {

    public HeloLandingSkid(Color componentColor) {
        super(3, 50);
        this.setFill(componentColor);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(1);
    }
}