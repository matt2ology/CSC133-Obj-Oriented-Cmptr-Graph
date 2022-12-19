import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class HeloLandingSkidCross extends Rectangle {
    public HeloLandingSkidCross(Color componentColor) {
        super(45, 3);
        setFill(componentColor);
        // center the cross on the skid cross point (0,0)
        setTranslateX(-getWidth() / 2);
    }
}