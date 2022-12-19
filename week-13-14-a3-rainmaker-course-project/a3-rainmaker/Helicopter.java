import javafx.geometry.Point2D;

class Helicopter extends MoveableObject implements Steerable {
    private boolean isIgnitionOn;
    private final double HOVER_SPEED_0 = 0;
    private final double MAX_FORWARD_SPEED = 10.0;
    private final double MAX_REVERSE_SPEED = 2.0;
    private final double SPEED_STEP_VALUE = 0.5;
    private final double STEERING_ANGLE_INCREMENT = 5;
    private final int FUEL_BURN_RATE = 5;
    private int fuelGauge;

    private HelicopterGameInfoText fuelText; // fuel gauge text object

    public Helicopter(Point2D location, int fuelCapacity) {
        super(location);
        this.isIgnitionOn = false;
        this.setFuelGauge(fuelCapacity);
        this.add(new HeloBody());
        this.add(new HeloBlade());
        this.add(fuelText = new HelicopterGameInfoText(
                "Fuel:" + String.valueOf(getFuelGauge())));
    }

    /**
     * @brief increaseSpeed of the helicopter by specified step value
     *        above 0 speed (hover) and below MAX_SPEED. If the helicopter
     *        is in reverse increasing speed will put it in forward.
     */
    public void increaseSpeed() {
        this.speed = Math.min(
                Math.max(HOVER_SPEED_0, this.speed + SPEED_STEP_VALUE),
                MAX_FORWARD_SPEED);
    }

    /**
     * @brief decreaseSpeed of the helicopter by specified step value
     *        above 0 (hover). Decreasing the speed below 0 (hover) will
     *        put the helicopter in reverse by the minimum speed.
     */
    public void decreaseSpeed() {
        this.speed = (this.speed <= HOVER_SPEED_0) // Are we in reverse?
                ? -MAX_REVERSE_SPEED // Put in reverse from 0 speed (hover)
                // decrease speed by step value but not 0 (hover)
                : Math.max(HOVER_SPEED_0, (this.speed - SPEED_STEP_VALUE));
    }

    public void toggleIgnition() {
        this.isIgnitionOn = !isIgnitionOn;
    }

    public int getFuelGauge() {
        return fuelGauge;
    }

    private void setFuelGauge(int fuel) {
        this.fuelGauge = fuel;
    }

    @Override
    public void steerLeft() {
        setNormalizedAngle(this.rotate.getAngle() - STEERING_ANGLE_INCREMENT);
    }

    @Override
    public void steerRight() {
        setNormalizedAngle(this.rotate.getAngle() + STEERING_ANGLE_INCREMENT);
    }

    @Override
    public void move() {
        this.translate.setX(
                this.translate.getX() + this.directionToVector().getX());
        this.translate.setY(
                this.translate.getY() + this.directionToVector().getY());
    }

    @Override
    public void update() {
        if (!isIgnitionOn) {
            return;
        }
        this.move();
        this.setFuelGauge(Math.max(0, getFuelGauge() - FUEL_BURN_RATE));
        this.fuelText.setText("Fuel:" + String.valueOf(getFuelGauge()));
    }

    @Override
    public String toString() {
        return "Helicopter: "
                + "isIgnitionOn: " + this.isIgnitionOn
                + ", Angle: " + this.rotate.getAngle()
                + ", Speed: " + this.speed
                + ", Fuel Gauge: " + this.getFuelGauge();
    }
}