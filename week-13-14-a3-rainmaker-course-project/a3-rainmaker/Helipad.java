import javafx.geometry.Dimension2D;
import javafx.geometry.Point2D;

/**
 * @see https://heliportlighting.com/heliport-design/
 */
class Helipad extends FixedObject {

    public Helipad(Point2D location, Dimension2D size) {
        super(location);
        this.add(new HelipadFATOSquare(size));
        this.add(new HelipadTLOFCircle(size));
        this.add(new HelipadH());
    }
}