import javafx.geometry.Dimension2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * @brief Represents the Touchdown and Liftoff (TLOF) circle of the
 *        helipad.
 * 
 *        The Circle's diameter is based on the helipad's FATO square dimension.
 */
class HelipadTLOFCircle extends Circle {
    private static final int PADDING_BETWEEN_SQUARE_AND_CIRCLE = 5;

    public HelipadTLOFCircle(Dimension2D size) {
        super(0,
                0,
                ((size.getWidth() / 2) - PADDING_BETWEEN_SQUARE_AND_CIRCLE));
        this.setFill(Color.TRANSPARENT);
        this.setStroke(Color.YELLOW);
        this.setStrokeWidth(3);
    }
}