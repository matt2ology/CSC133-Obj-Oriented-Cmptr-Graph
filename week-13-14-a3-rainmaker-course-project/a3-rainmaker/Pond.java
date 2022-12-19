import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.transform.Scale;

class Pond extends FixedObject {
    private Circle pond;
    private double pondPercentage;
    private final Color POND_COLOR = Color.BLUE;
    private final double POND_SIZE_LOWER_BOUND = 15.0;
    private final double POND_SIZE_UPPER_BOUND = 25.0;

    private PondPercentageInfoText pondPercentageInfoText;

    public Pond(Point2D location) {
        super(location);
        this.pondPercentage = Utility.generateRandomNumberInRange(
                POND_SIZE_LOWER_BOUND,
                POND_SIZE_UPPER_BOUND);
        this.pond = new Circle(pondPercentage);
        this.pond.setFill(POND_COLOR);
        this.pond.setStroke(Color.BLACK);
        this.pond.setStrokeWidth(1);
        this.add(pond);
        pondPercentageInfoText = new PondPercentageInfoText(pondPercentage);
        this.add(pondPercentageInfoText);
    }

    private double getPondPercentage() {
        return pondPercentage;
    }

    private void setPondPercentage(double pondPercentage) {
        this.pondPercentage += pondPercentage;
    }

    private Scale getScale() {
        return scale;
    }

    private void setScale(Scale scale) {
        this.scale = scale;
    }

    @Override
    public void update() {
        // update pond percentage text to reflect current pond percentage
        pondPercentageInfoText.setText(
                String.format("%.0f", getPondPercentage()) + "%");
        // update scale of pond
        pond.getTransforms().add(scale);
    }
}