import javafx.geometry.Dimension2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * @brief Represents the Final Approach and Takeoff (FATO) square of the
 *        helipad.
 */
class HelipadFATOSquare extends Rectangle {

    public HelipadFATOSquare(Dimension2D size) {
        super(0, 0,
                size.getWidth(),
                size.getHeight());
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.WHITE);
        this.setStrokeWidth(5);
        // Center of the square is at the center of the
        // helipad by setTranslate because we want to
        // translate the rectangle relative to the helipad object
        this.setTranslateX(-size.getWidth() / 2);
        this.setTranslateY(-size.getHeight() / 2);
    }
}