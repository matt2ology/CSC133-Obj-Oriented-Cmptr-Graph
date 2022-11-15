import org.junit.Before;
import org.junit.Test;

import javafx.geometry.Point2D;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class HelicopterTest {
    private Helicopter helicopter;
    private static final int FUEL_CAPACITY = 1000;

    @Before
    public void setUp() throws Exception {
        helicopter = new Helicopter(new Point2D(0, 0), FUEL_CAPACITY);
    }

    /**
     * @brief Test that Helicopter can be instantiated.
     */
    @Test
    public void canBeInstantiated() {
        assertNotNull(this.helicopter);
    }

    /**
     * @brief Test that Helicopter ignition is off by default.
     */
    @Test
    public void ignitionIsOffByDefault() {
        assertFalse(this.helicopter.isIgnitionOn());
    }

    /**
     * @brief Test that Helicopter ignition can be toggled on.
     */
    @Test
    public void ignitionCanBeToggledOn() {
        this.helicopter.toggleIgnition();
        assertEquals(true, this.helicopter.isIgnitionOn());
    }

    /**
     * @brief Test that Helicopter ignition can be toggled off.
     */
    @Test
    public void ignitionCanBeToggledOff() {
        this.helicopter.toggleIgnition();
        this.helicopter.toggleIgnition();
        assertEquals(false, this.helicopter.isIgnitionOn());
    }

    /**
     * @brief Test that Helicopter can set translation (location/position)
     *        after instantiation.
     */
    @Test
    public void setPosition_OnInstantiation() {
        for (int i = 0; i < Globals.GAME_APP_DIMENSIONS.getHeight(); i++) {
            helicopter = new Helicopter(new Point2D(i, i), FUEL_CAPACITY);
            assertEquals(i, helicopter.translate.getX(), 0.0);
            assertEquals(i, helicopter.translate.getY(), 0.0);
        }
    }

    /**
     * @brief Test that Helicopter can set fuel capacity on instantiation.
     */
    @Test
    public void setFuelGauge_OnInstantiation() {
        for (int i = 0; i < FUEL_CAPACITY; i++) {
            helicopter = new Helicopter(new Point2D(0, 0), i);
            assertEquals(i, helicopter.getFuelGauge(), 0.0);
        }
    }

    /**
     * @brief Test that Helicopter can set fuel capacity after instantiation.
     */
    @Test
    public void setFuelGage_AfterInstantiation() {
        for (int i = 0; i <= FUEL_CAPACITY; i++) {
            helicopter.setFuelGauge(i);
            assertEquals(i, helicopter.getFuelGauge(), 0.0);
        }
    }

    /**
     * @brief Test that Helicopter can set rotation angle after instantiation.
     */
    @Test
    public void setRotationAngle_AfterInstantiation() {
        for (int i = 0; i <= 360; i++) {
            helicopter.rotate.setAngle(i);
            assertEquals(i, helicopter.rotate.getAngle(), 0.0);
        }
    }

    /**
     * @brief Test that Helicopter can increase speed
     */
    @Test
    public void increaseSpeed() {
        for (int i = 0; i <= 500; i++) {
            assertEquals(Math.min(10, (double) i / 10), helicopter.getSpeed(), 0.1);
            helicopter.increaseSpeed();
        }
    }

    /**
     * @brief Test that Helicopter can decrease speed
     */
    @Test
    public void decreaseSpeed() {
        for (int i = 0; i <= 50; i++) {
            helicopter.decreaseSpeed();
            assertEquals(Math.max(-2.0, helicopter.getSpeed()), helicopter.getSpeed(), 0.1);
        }
    }

    /**
     * @brief Test that Helicopter fuel does not burn when not moving in the update
     *        method
     */
    @Test
    public void noFuelBurn() {
        for (int i = 0; i <= FUEL_CAPACITY; i++) {
            helicopter.update();
        }
        assertEquals(FUEL_CAPACITY, helicopter.getFuelGauge(), 0.0);
    }

    /**
     * @brief Test that Helicopter fuel burns when moving in the update method
     */
    @Test
    public void burnFuel() {
        helicopter = new Helicopter(new Point2D(0, 0), 10);
        // turn on the engine
        helicopter.toggleIgnition();
        for (int i = 0; i <= FUEL_CAPACITY; i++) {
            helicopter.update();
        }
        assertEquals(0, helicopter.getFuelGauge(), 0.0);
    }

}
