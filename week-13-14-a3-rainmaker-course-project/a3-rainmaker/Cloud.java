import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Cloud extends MoveableObject {
    private int rgb_value = 255;
    private final double CLOUD_SIZE_UPPER_BOUND = 50.0;
    private final double CLOUD_SIZE_LOWER_BOUND = 30.0;
    private double saturationPercentage;
    private Color CLOUD_COLOR = Color.rgb(rgb_value, rgb_value, rgb_value);
    private Circle cloud;

    private CloudSaturationPercentageInfoText saturationText;

    public Cloud(Point2D location) {
        super(location);
        this.saturationPercentage = 0.0;
        this.cloud = new Circle();
        this.cloud.setFill(CLOUD_COLOR);
        this.cloud.setRadius(Utility.generateRandomNumberInRange(
                CLOUD_SIZE_LOWER_BOUND, CLOUD_SIZE_UPPER_BOUND));
        this.saturationPercentage = 0.0;
        this.add(this.cloud);
        saturationText = new CloudSaturationPercentageInfoText(
                saturationPercentage);
        this.add(saturationText);
    }

    @Override
    protected void move() {
        // TODO Auto-generated method stub

    }

}
