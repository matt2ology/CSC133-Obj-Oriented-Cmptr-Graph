import javafx.geometry.Point2D;

/**
 * @brief MoveableObject is a GameObject that can move.
 */
abstract class MoveableObject extends GameObject {
    protected double speed;

    abstract protected void move(); // all objects moves are differently

    public MoveableObject(Point2D location) {
        super(location);
        this.speed = 0.0;
        this.rotate.setAngle(0);
    }

    /**
     * Converts a direction in degrees (0...360) to x and y coordinates.
     * 
     * @return A Point2D object with x and y coordinates of the vector.
     */
    protected Point2D directionToVector() {
        double radians = Math.toRadians(this.rotate.getAngle());
        // y is negative because y axis is inverted
        return new Point2D(
                (this.speed * Math.sin(radians)),
                -(this.speed * Math.cos(radians)));
    }

    protected void setNormalizedAngle(double compassAngle) {
        if (compassAngle < 0) {
            compassAngle += 360;
        } else if (compassAngle >= 360) {
            compassAngle -= 360;
        }
        this.rotate.setAngle(compassAngle);
    }

    @Override
    public void update() {
        super.update();
    }
}