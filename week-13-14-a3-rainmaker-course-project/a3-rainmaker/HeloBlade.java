import javafx.animation.AnimationTimer;
import javafx.geometry.Dimension2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

class HeloBlade extends Rectangle {
    private int bladeSpeed;
    private static Dimension2D BLADE_DIMENSION = new Dimension2D(90, 4);

    public HeloBlade() {
        super(BLADE_DIMENSION.getWidth(), BLADE_DIMENSION.getHeight());
        this.bladeSpeed = 0;
        this.setFill(Color.GRAY);
        this.setStroke(Color.BLACK);
        this.setStrokeWidth(1);
        // The origin of the rotation is the center of the rectangle.
        this.setX(-this.getWidth() / 2);
        this.setY(-this.getHeight() / 2);
        // Helicopter Blade is self animating with it's own animation timer.
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (bladeSpeed <= 120) {
                    bladeSpeed++;
                }
                HeloBlade.this.setRotate(
                        HeloBlade.this.getRotate() + bladeSpeed);
            }
        };
        timer.start();
    }
}