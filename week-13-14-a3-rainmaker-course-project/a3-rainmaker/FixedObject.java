import javafx.geometry.Point2D;
import javafx.scene.transform.Scale;

abstract class FixedObject extends GameObject {
    protected Scale scale;

    public FixedObject(Point2D location) {
        super(location);
        this.scale = new Scale();
    }
}